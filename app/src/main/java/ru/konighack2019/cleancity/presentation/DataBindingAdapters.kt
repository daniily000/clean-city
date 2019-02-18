package ru.konighack2019.cleancity.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.konighack2019.cleancity.model.EsooEntry
import ru.konighack2019.cleancity.model.KSEntry
import ru.konighack2019.cleancity.presentation.results.adapter.ReportsAdapter
import ru.konighack2019.cleancity.service.common.OperationState


@BindingAdapter("operationResult")
internal fun TextView.bindValidationResult(operationResult: OperationState?) {
    this.text = operationResult.toString()
}

@BindingAdapter("ksItems")
internal fun RecyclerView.bindKSEntries(items: List<KSEntry>?) {
    if (items != null && items.isNotEmpty()) {
        val adapter = adapter as ReportsAdapter
        adapter.items.addAll(items)
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter("esooItems")
internal fun RecyclerView.bindEsooEntries(items: List<EsooEntry>?) {
    if (items != null && items.isNotEmpty()) {
        val adapter = adapter as ReportsAdapter
        adapter.items.addAll(items)
        adapter.notifyDataSetChanged()
    }
}