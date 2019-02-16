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
import ru.konighack2019.cleancity.service.AppStateService
import ru.konighack2019.cleancity.service.ReportGenerator
import ru.konighack2019.cleancity.service.common.OperationState

class ReportGenerationFragment : DialogFragment() {

    private val appStateService: AppStateService by AppDelegate.getKodein().instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.konighack2019.cleancity.databinding.FrReportGenerationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fr_report_generation, container, false)
        binding.lifecycleOwner = this
        val viewModel = ViewModelProviders.of(this).get(ReportGenerationViewModel::class.java)
        binding.vm = viewModel
        viewModel.generationState.observe(this, Observer {
            when (it) {
                OperationState.SUCCESS -> appStateService.onReportReady()
                OperationState.PROCESSING -> {}
                else -> appStateService.showError("Report generation failed")
            }
        })
        return binding.root
    }
}

class ReportGenerationViewModel : ViewModel() {
    private val reportGenerator: ReportGenerator by AppDelegate.getKodein().instance()
    val generationState = reportGenerator.getState()
}