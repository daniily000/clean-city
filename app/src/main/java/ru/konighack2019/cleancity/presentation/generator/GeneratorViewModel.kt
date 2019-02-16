package ru.konighack2019.cleancity.presentation.generator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.service.AppStateService
import ru.konighack2019.cleancity.service.ReportGenerator

class GeneratorViewModel : ViewModel() {
    private val generatorService: ReportGenerator by AppDelegate.getKodein().instance()
    private val reportService: ReportGenerator by AppDelegate.getKodein().instance()
    private val appStateService: AppStateService by AppDelegate.getKodein().instance()

    val generatorState = generatorService.getState()
    var isReportReady = MutableLiveData<Boolean>().also { it.postValue(false) }
    val report = reportService.getReport()

    val editMode = MutableLiveData<Boolean>().also { it.postValue(false) }

    private val formErrors = MutableLiveData<MutableMap<String, String>>().also { it.postValue(mutableMapOf()) }

    fun editReport() = editMode.postValue(true)

    fun sendReport() {
        if (validateForm()) {
            appStateService.postReport()
        } else {
            displayErrors()
        }
    }

    private fun displayErrors() {

    }

    private fun validateForm() =
        when {
            report.subject.isEmpty() -> false.also {
                formErrors.postValue(formErrors.value.also {
                    formErrors.value!!["subject"] = "Subject is empty"
                })
            }
            report.description.isEmpty() -> false.also {
                formErrors.postValue(formErrors.value.also {
                    formErrors.value!!["description"] = "Description is empty"
                })
            }
            report.address.isEmpty() -> false.also {
                formErrors.postValue(formErrors.value.also {
                    formErrors.value!!["address"] = "Address is empty"
                })
            }
            else -> true
        }

}