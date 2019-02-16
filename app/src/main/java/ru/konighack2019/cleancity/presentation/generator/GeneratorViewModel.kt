package ru.konighack2019.cleancity.presentation.generator

import androidx.lifecycle.ViewModel
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.service.ReportGenerator


class GeneratorViewModel: ViewModel() {
    private val generatorService: ReportGenerator by AppDelegate.getKodein().instance()
    val generatorState = generatorService.getState()
}