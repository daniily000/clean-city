package ru.konighack2019.cleancity.presentation.generator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.R
import ru.konighack2019.cleancity.service.AppStateService

class ReportFragment: Fragment() {

    private val appStateService: AppStateService by AppDelegate.getKodein().instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.konighack2019.cleancity.databinding.FrReportBinding = DataBindingUtil.inflate(inflater, R.layout.fr_report, container, false)
        binding.lifecycleOwner = this
        val viewModel = ViewModelProviders.of(this).get(ReportViewModel::class.java)
        binding.vm = viewModel
        return binding.root
    }
}