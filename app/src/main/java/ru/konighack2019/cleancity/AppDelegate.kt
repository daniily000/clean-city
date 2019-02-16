package ru.konighack2019.cleancity

import android.app.Application
import android.content.Context
import androidx.room.Room
import ru.konighack2019.cleancity.db.Database

class AppDelegate: Application() {
    init { instance = this }

    val dao = Room.databaseBuilder(this@AppDelegate, Database::class.java, "cleandb")
    .fallbackToDestructiveMigration()
    .build().getKSDao()

    companion object {
        lateinit var instance: AppDelegate
        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }
}