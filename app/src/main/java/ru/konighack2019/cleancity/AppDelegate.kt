package ru.konighack2019.cleancity

import android.app.Application
import android.content.Context

class AppDelegate: Application() {
    init { instance = this }

    companion object {
        private lateinit var instance: AppDelegate
        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }
}