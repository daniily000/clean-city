package ru.konighack2019.cleancity.presentation.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.model.EsooEntry
import ru.konighack2019.cleancity.model.KSEntry
import ru.konighack2019.cleancity.service.DataService

class ResultsViewModel: ViewModel() {

    private val dataService: DataService by AppDelegate.getKodein().instance()

    val ksReports: LiveData<List<KSEntry>> = dataService.getKSEntries()
    val esooReports: LiveData<List<EsooEntry>> = dataService.getEsooEntries()

}