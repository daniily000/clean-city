package ru.konighack2019.cleancity.service

import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.net.NetApi

class ApiServiceImpl {
    private val ksApi: NetApi by AppDelegate.getKodein().instance()
}