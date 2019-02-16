package ru.konighack2019.cleancity.service

import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.konighack2019.cleancity.model.ESOOReport
import ru.konighack2019.cleancity.model.KSReport
import ru.konighack2019.cleancity.model.Point

interface ApiService {
    fun getPointDetails(id: String): LiveData<Point>
    fun postKSReport(report: KSReport): String
    fun postEsooReport(report: ESOOReport): String
}