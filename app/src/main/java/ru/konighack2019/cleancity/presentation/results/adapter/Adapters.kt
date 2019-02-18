package ru.konighack2019.cleancity.presentation.results.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import ru.konighack2019.cleancity.R
import ru.konighack2019.cleancity.model.EsooEntry
import ru.konighack2019.cleancity.model.HistoryEntry
import ru.konighack2019.cleancity.model.KSEntry


class ReportsAdapter(list: MutableList<HistoryEntry>) :  ListDelegationAdapter<MutableList<HistoryEntry>>(){
    init {
        delegatesManager.addDelegate(KSReportsAdapter())
        delegatesManager.addDelegate(EsooReportAdapter())
        items = list
    }
}

class KSReportsAdapter : AdapterDelegate<MutableList<HistoryEntry>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        KSEntryHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false))

    override fun onBindViewHolder(
        items: MutableList<HistoryEntry>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) = (holder as KSEntryHolder).bind(items[position] as KSEntry)

    override fun isForViewType(items: MutableList<HistoryEntry>, position: Int): Boolean = items[position] is KSEntry

}

class EsooReportAdapter : AdapterDelegate<MutableList<HistoryEntry>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        KSEntryHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false))

    override fun onBindViewHolder(
        items: MutableList<HistoryEntry>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) = (holder as EsooEntryHolder).bind(items[position] as EsooEntry)

    override fun isForViewType(items: MutableList<HistoryEntry>, position: Int): Boolean = items[position] is EsooEntry

}

