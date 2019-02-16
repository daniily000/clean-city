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
import ru.konighack2019.cleancity.databinding.FrImageValidationBinding
import ru.konighack2019.cleancity.presentation.generator.GeneratorViewModel
import ru.konighack2019.cleancity.service.AppStateService
import ru.konighack2019.cleancity.service.DataService
import ru.konighack2019.cleancity.service.ReportGenerator
import ru.konighack2019.cleancity.service.ValidationService
import ru.konighack2019.cleancity.service.common.OperationState

class ImageValidationFragment: DialogFragment() {

    private val appStateService: AppStateService by AppDelegate.getKodein().instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.konighack2019.cleancity.databinding.FrImageValidationBinding = DataBindingUtil.inflate(inflater, R.layout.fr_image_validation, container, false)
        binding.lifecycleOwner = this
        val viewModel = ViewModelProviders.of(this).get(ImageValidationViewModel::class.java)
        binding.vm = viewModel
        viewModel.validationState.observe(this, Observer {
            when (it) {
                OperationState.SUCCESS -> appStateService.validateLocation()
                OperationState.FAILED -> appStateService.showError("Image does not contain dump")
                OperationState.ERROR -> appStateService.showError("Image validation error")
            }
        })
        return binding.root
    }
}

class ImageValidationViewModel: ViewModel() {
    private val validationService: ValidationService by AppDelegate.getKodein().instance()
    private val dataService: DataService by AppDelegate.getKodein().instance()
    val validationState = validationService.imageContainsDump(listOf(dataService.imageUri))
}