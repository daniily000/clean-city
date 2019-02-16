package ru.konighack2019.cleancity.presentation.generator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.R
import ru.konighack2019.cleancity.service.AppStateService
import ru.konighack2019.cleancity.service.common.OperationState

class GeneratorFragment: DialogFragment() {

    private val appStateService: AppStateService by AppDelegate.getKodein().instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.konighack2019.cleancity.databinding.FrGenerateReportBinding = DataBindingUtil.inflate(inflater, R.layout.fr_generate_report, container, false)
        binding.lifecycleOwner = this
        val viewModel = ViewModelProviders.of(this).get(GeneratorViewModel::class.java)
        binding.vm = viewModel
        viewModel.generatorState.observe(this, Observer {
            if (it != null && it != OperationState.PROCESSING) {
                viewModel.isReportReady.postValue(true)
            }
        })
        return binding.root
    }
}