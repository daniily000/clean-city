package ru.konighack2019.cleancity.presentation.validation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.service.AppStateService
import ru.konighack2019.cleancity.service.DataService
import ru.konighack2019.cleancity.service.ReportGenerator
import ru.konighack2019.cleancity.service.ValidationService
import ru.konighack2019.cleancity.service.common.OperationState
import java.lang.StringBuilder

class ValidationViewModel: ViewModel() {


    private val validationService: ValidationService by AppDelegate.getKodein().instance()
    private val dataService: DataService by AppDelegate.getKodein().instance()
    private val reportService: ReportGenerator by AppDelegate.getKodein().instance()
    private val appStateService: AppStateService by AppDelegate.getKodein().instance()

    val report = reportService.getReport()

    val isUnsanctioned = validationService.isUnsanctioned()
    val alreadyReported = validationService.isNotAlreadyReported()
    val containsDump = validationService.imageContainsDump(report.photo)



    val editMode = MutableLiveData<Boolean>().also { it.postValue(false) }

    private val validationErrors = StringBuilder()
    private val formErrors = MutableLiveData<MutableMap<String, String>>().also { it.postValue(mutableMapOf()) }

    fun editReport() = editMode.postValue(true)


    fun sendReport() {
        if (validateReport() && validateForm()) {
            GlobalScope.launch {
                dataService.postReport(report)
                appStateService.showHistory()
            }
        } else {
            displayErrors()
        }
    }

    private fun displayErrors() {

    }

    private fun validateReport() =
        when {
            isUnsanctioned.value != OperationState.SUCCESS -> false.also {
                validationErrors.append("Sanctioned validation failed")
            }
            alreadyReported.value != OperationState.SUCCESS -> false.also {
                validationErrors.append("Unsanctioned validation failed")
            }
            containsDump.value != OperationState.SUCCESS -> false.also {
                validationErrors.append("Image validation failed")
            }
            else -> true
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