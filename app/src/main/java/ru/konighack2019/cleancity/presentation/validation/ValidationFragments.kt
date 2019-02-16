package ru.konighack2019.cleancity.presentation.validation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fr_progress.view.*
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.R
import ru.konighack2019.cleancity.service.AppStateService
import ru.konighack2019.cleancity.service.DataService
import ru.konighack2019.cleancity.service.ReportGenerator
import ru.konighack2019.cleancity.service.ValidationService
import ru.konighack2019.cleancity.service.common.OperationState

class ImageValidationFragment: ProgressFragment() {
    private val validationService: ValidationService by AppDelegate.getKodein().instance()
    private val dataService: DataService by AppDelegate.getKodein().instance()
    override fun getMessage() = AppDelegate.applicationContext().getString(R.string.validationg_image)
    override fun observeState() {
        validationService.imageContainsDump(listOf(dataService.imageUri!!)).observe(this, Observer {
            when (it) {
                OperationState.SUCCESS -> appStateService.validateLocation()
                OperationState.FAILED -> appStateService.showError("Image does not contain dump")
                OperationState.ERROR -> appStateService.showError("Image validation error")
            }
        })
    }
}

class LocationValidationFragment: ProgressFragment() {
    private val validationService: ValidationService by AppDelegate.getKodein().instance()
    override fun getMessage() = AppDelegate.applicationContext().getString(R.string.validating_location)
    var isSanctioned: OperationState = OperationState.PROCESSING
    var isNotAlreadyReported: OperationState = OperationState.PROCESSING
    override fun observeState() {
        validationService.isUnsanctioned().observe(this, Observer {
            if (it != null && it != OperationState.PROCESSING) {
                isSanctioned = it
                checkStatus()
            }
        })
        validationService.isNotAlreadyReported().observe(this, Observer {
            if (it != null && it != OperationState.PROCESSING) {
                isNotAlreadyReported = it
                checkStatus()
            }
        })
    }

    private fun checkStatus() {
        when {
            isSanctioned == OperationState.FAILED && isNotAlreadyReported  == OperationState.SUCCESS ->
                appStateService.generateKSReport()
            isSanctioned == OperationState.FAILED && isNotAlreadyReported  == OperationState.FAILED ->
                appStateService.showError("Already reported via kartasvalok.ru")
            isSanctioned == OperationState.SUCCESS ->
                appStateService.generateEsooReport()
        }
    }
}

class ReportGenerationFragment : ProgressFragment() {
    private val reportGenerator: ReportGenerator by AppDelegate.getKodein().instance()
    override fun getMessage() = AppDelegate.applicationContext().getString(R.string.generating_report)
    override fun observeState() {
        reportGenerator.getState().observe(this, Observer<OperationState> {
            when (it) {
                OperationState.SUCCESS -> appStateService.onReportReady()
                OperationState.PROCESSING -> {}
                else -> appStateService.showError("Report generation failed")
            }
        })
    }
}

class PostingReportFragment : ProgressFragment() {
    private val reportGenerator: ReportGenerator by AppDelegate.getKodein().instance()
    override fun getMessage() = AppDelegate.applicationContext().getString(R.string.sending_report)
    override fun observeState() {
        reportGenerator.getState().observe(this, Observer<OperationState> {
            when (it) {
                OperationState.SUCCESS -> appStateService.onReportReady()
                OperationState.PROCESSING -> {
                }
                else -> appStateService.showError("Report generation failed")
            }
        })
    }
}

abstract class ProgressFragment: DialogFragment() {
    protected val appStateService: AppStateService by AppDelegate.getKodein().instance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fr_progress, container, false)
        observeState()
        view.progress_message.text = getMessage()
        return view
    }
    abstract fun observeState()
    abstract fun getMessage(): String
}
