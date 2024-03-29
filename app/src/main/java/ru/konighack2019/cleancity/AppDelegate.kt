package ru.konighack2019.cleancity

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ru.konighack2019.cleancity.db.Dao
import ru.konighack2019.cleancity.db.Database
import ru.konighack2019.cleancity.di.networkModule
import ru.konighack2019.cleancity.service.*
import ru.konighack2019.cleancity.service.data.IntegratedDataService
import ru.konighack2019.cleancity.service.generator.TestReportGenerator
import ru.konighack2019.cleancity.service.validation.FirebaseValidationService

/**
 * Basic Application Delegate. Contains methods for fetching services and applicationContext.
 * get Context with AppDelegate.applicationContext()
 * get Service with: val service: ServiceInterface by AppDelegate.getKodein().instance()
 */
class AppDelegate: Application() {
    init { instance = this }

    val kodein: Kodein = Kodein {
        import(networkModule)
        bind<Dao>() with singleton {
            Room.databaseBuilder(this@AppDelegate, Database::class.java, "cleandb")
                .fallbackToDestructiveMigration()
                .build().getKSDao()
        }
        bind<AppStateService>() with singleton { AppStateService() }
        bind<ValidationService>() with singleton { FirebaseValidationService() }
        bind<DataService>() with singleton { IntegratedDataService() }
        bind<ReportGenerator>() with singleton { TestReportGenerator() }
        bind<AppDelegate>() with provider { this@AppDelegate }
        bind<FusedLocationProviderClient>() with singleton { LocationServices.getFusedLocationProviderClient(applicationContext) }
    }

    companion object {
        private lateinit var instance: AppDelegate

        fun applicationContext() : Context {
            return instance.applicationContext
        }
        fun getKodein() : Kodein {
            return instance.kodein
        }
    }
}