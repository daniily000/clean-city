package ru.konighack2019.cleancity

import android.app.Application
import android.content.Context
import androidx.room.Room
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ru.konighack2019.cleancity.db.Dao
import ru.konighack2019.cleancity.db.Database
import ru.konighack2019.cleancity.di.networkModule
import ru.konighack2019.cleancity.service.StorageService
import ru.konighack2019.cleancity.service.StorageServiceImpl

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
        bind<StorageService>() with singleton { StorageServiceImpl() }
        bind<AppDelegate>() with provider { this@AppDelegate }
    }

    companion object {
        lateinit var instance: AppDelegate
        fun applicationContext() : Context {
            return instance.applicationContext
        }
        fun getKodein() : Kodein {
            return instance.kodein
        }
    }
}