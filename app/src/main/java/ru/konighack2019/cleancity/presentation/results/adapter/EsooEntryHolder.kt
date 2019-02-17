package ru.konighack2019.cleancity.presentation.results.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_report.view.*
import kotlinx.android.synthetic.main.item_report_expanded.view.*
import ru.konighack2019.cleancity.dateToReadable
import ru.konighack2019.cleancity.model.EsooEntry

class EsooEntryHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private var expanded = false

    fun bind(esooEntry: EsooEntry) {
        view.tv_report_address.text = esooEntry.address
        view.tv_report_date.text = dateToReadable(esooEntry.createdAt.toLong())
        view.tv_report_description.text = esooEntry.description
        view.tv_report_date.text = dateToReadable(esooEntry.createdAt.toLong())
    }

}
