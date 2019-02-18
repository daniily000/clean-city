package ru.konighack2019.cleancity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.konighack2019.cleancity.db.Dao
import ru.konighack2019.cleancity.db.Database
import ru.konighack2019.cleancity.di.networkModule
import ru.konighack2019.cleancity.model.Report
import ru.konighack2019.cleancity.model.UserInfo
import ru.konighack2019.cleancity.service.DataService
import ru.konighack2019.cleancity.service.data.IntegratedDataService
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


class DataServiceTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var kodein: Kodein
    lateinit var dataService: DataService

    val report = Report(
        "тема", "описание", "адрес",
        "fgs", "f@d.s", "123", "регион", "1", "1",
        "54.72263743392473,20.502462387084964",
        emptyList()
    )

    val userInfo = UserInfo("Test", "test@test.test", "1234567890")

    @Before
    fun setup() {
        kodein = Kodein {
            import(networkModule)
            bind<Database>() with singleton {
                Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), Database::class.java)
                    .allowMainThreadQueries().build()
            }
            bind<Dao>() with singleton { instance<Database>().getKSDao() }
        }
        dataService = IntegratedDataService()
    }

    @After
    fun finish() {
        dataService.flushPoints().execute()
    }

    @Test
    fun postReport() =
        runBlocking {
            val id = dataService.postReport(report)
            Assert.assertTrue(!id.isNullOrBlank())
        }

    @Test
    fun getReport() =
        runBlocking {
            val id = dataService.postReport(report)
            val pointDetails = id?.let { getResultOrNull(dataService.getPointDetails(id)) }
            Assert.assertTrue(pointDetails?.id == id)
        }

//    @Test
//    fun getAll() =
//        runBlocking {
//            val id =dataService.postReport(report)
//            val list = getResultOrNull(dataService.getAllPointDetails())
//            Assert.assertTrue(list?.get(0)?.id == id)
//        }

    @Test
    fun saveAndGetUser() =
        runBlocking {
            dataService.saveUserInfo(userInfo)
            val savedUserInfo = getResultOrNull(dataService.getUserInfo())
            Assert.assertTrue(savedUserInfo?.name == userInfo.name)
        }

    private fun <T> getResultOrNull(data: LiveData<T>): T? {
        var result: T? = null
        val latch = CountDownLatch(1)
        data.observeForever {
            it?.apply {
                result = it
                latch.countDown()
            }
        }
        latch.await(10, TimeUnit.SECONDS)
        return result
    }
}