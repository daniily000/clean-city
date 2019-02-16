package ru.konighack2019.cleancity.service.data

import android.net.Uri
import androidx.lifecycle.LiveData

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.MultipartBody
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.db.Dao
import ru.konighack2019.cleancity.model.KSReport
import ru.konighack2019.cleancity.model.Point
import ru.konighack2019.cleancity.model.UserInfo
import ru.konighack2019.cleancity.net.NetApi
import ru.konighack2019.cleancity.service.DataService
import java.io.File

class IntegratedDataService : DataService {

    override var imageUri: Uri? = null

    private val ksApi: NetApi by AppDelegate.getKodein().instance()
    private val ksDao: Dao by AppDelegate.getKodein().instance()

    override fun getPointDetails(id: String): LiveData<Point> {
        GlobalScope.launch {
            ksDao.insertPoint(ksApi.getPointById(id).await())
        }
        return ksDao.getPointById(id)
    }

    override suspend fun postReport(report: KSReport): String {
        val body = MultipartBody.Builder().addFormDataPart("subject", report.subject)
            .addFormDataPart("address", report.address)
            .addFormDataPart("region_name", report.regionName)
            .addFormDataPart("description", report.description)
            .addFormDataPart("name", report.name)
            .addFormDataPart("phone", report.phone)
            .addFormDataPart("email", report.email)
            .addFormDataPart("map_point", report.mapPoint)
            .addFormDataPart("agree", "1")
            .addFormDataPart("participate", report.participate)

        report.photo.forEach {
            body.addPart(
                MultipartBody.Part.createFormData(
                    "photo",
                    File(it.path).path,
                    RequestBody.create(MediaType.parse("image/*"), File(it.path))
                )
            )
        }

        val id = ksApi.postKSReport(body.setType(MediaType.get("multipart/form-data")).build()).await()
        ksDao.insertPoint(ksApi.getPointById(id).await())
        return id
    }

    override fun getAllPointDetails(): LiveData<List<Point>> = ksDao.getAllPoints()
    override fun getUserInfo(): LiveData<UserInfo> = ksDao.getUserInfo()
    override fun saveUserInfo(userInfo: UserInfo) = ksDao.insertUserInfo(userInfo)
    override fun getUserInfoBlocking(): UserInfo = ksDao.getUserInfoBlocking()
    override fun flushPoints() = ksApi.deleteAllPoints()
}
