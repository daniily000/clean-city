package ru.konighack2019.cleancity.presentation.validation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.R
import ru.konighack2019.cleancity.databinding.FrLocationValidationBinding
import ru.konighack2019.cleancity.service.AppStateService
import ru.konighack2019.cleancity.service.ValidationService
import ru.konighack2019.cleancity.service.common.OperationState

class LocationValidationFragment: DialogFragment() {

    private val appStateService: AppStateService by AppDelegate.getKodein().instance()
    var isSanctioned: OperationState = OperationState.PROCESSING
    var isNotAlreadyReported: OperationState = OperationState.PROCESSING


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.konighack2019.cleancity.databinding.FrLocationValidationBinding = DataBindingUtil.inflate(inflater, R.layout.fr_location_validation, container, false)
        binding.lifecycleOwner = this
        val viewModel = ViewModelProviders.of(this).get(LocationValidationViewModel::class.java)
        binding.vm = viewModel
        viewModel.sanctionedValidationState.observe(this, Observer {
            if (it != null && it != OperationState.PROCESSING) {
                checkStatus()
            }
        })
        viewModel.unsanctionedValidationState.observe(this, Observer {
            if (it != null && it != OperationState.PROCESSING) {
                checkStatus()
            }
        })
        return binding.root
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

class LocationValidationViewModel: ViewModel() {
    private val validationService: ValidationService by AppDelegate.getKodein().instance()
    val sanctionedValidationState = validationService.isUnsanctioned()
    val unsanctionedValidationState = validationService.isNotAlreadyReported()
}