package ru.konighack2019.cleancity.service

import android.net.Uri
import androidx.lifecycle.LiveData
import ru.konighack2019.cleancity.model.KSReport
import ru.konighack2019.cleancity.service.common.OperationState

interface ReportGenerator {
    fun createKSReport(imageUris: List<Uri>): LiveData<OperationState>
    fun createEsooReport(imageUris: List<Uri>): LiveData<OperationState>
    fun getReport(): KSReport
    fun getState(): LiveData<OperationState>
}