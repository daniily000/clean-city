package ru.konighack2019.cleancity.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamtwothree.kartasvalokapp.presentation.results.adapter.ReportsAdapter
import ru.konighack2019.cleancity.model.Point
import ru.konighack2019.cleancity.service.common.OperationState


@BindingAdapter("operationResult")
internal fun TextView.bindValidationResult(operationResult: OperationState?) {
    this.text = operationResult.toString()
}

@BindingAdapter("items")
internal fun RecyclerView.bindFeedEntries(items: List<Point>?) {
    if (items != null && items.isNotEmpty()) {
        val adapter = adapter as ReportsAdapter
        adapter.items = items
        adapter.notifyDataSetChanged()
    }
}