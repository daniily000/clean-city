package ru.konighack2019.cleancity.service

import androidx.lifecycle.LiveData
import ru.konighack2019.cleancity.model.Report
import ru.konighack2019.cleancity.model.Point

interface ApiService {
    fun getPointDetails(id: String): LiveData<Point>
    fun postKSReport(report: Report): String
    fun postEsooReport(report: Report): String
}