package ru.konighack2019.cleancity.service

import android.net.Uri
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.service.common.AppState

class AppStateService {

    private val validationService: ValidationService by AppDelegate.getKodein().instance()
    private val dataService: DataService by AppDelegate.getKodein().instance()
    private val reportService: ReportGenerator by AppDelegate.getKodein().instance()
    private val appService: AppStateService by AppDelegate.getKodein().instance()

    val appState = MutableLiveData<AppState>()

    init {
        val path =  "file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/171006-443-to7bN83pAcHC3CgI.JPG"
        reportService.createKSReport(listOf(Uri.parse(path)))
        generateReport()
    }

    fun generateReport() {
        appState.postValue(AppState.GENERATION)
    }

    fun validateReport() {
        appState.postValue(AppState.VALIDATION)
    }

    fun showHistory() {
        appState.postValue(AppState.HISTORY)
    }

}