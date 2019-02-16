package ru.konighack2019.cleancity.presentation.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.model.Point
import ru.konighack2019.cleancity.service.DataService

class ResultsViewModel: ViewModel() {

    private val dataService: DataService by AppDelegate.getKodein().instance()

    val reports: LiveData<List<Point>> = dataService.getAllPointDetails()

}