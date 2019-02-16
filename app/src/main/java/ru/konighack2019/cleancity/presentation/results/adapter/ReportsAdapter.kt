package com.teamtwothree.kartasvalokapp.presentation.results.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.konighack2019.cleancity.R
import ru.konighack2019.cleancity.model.Point
import ru.konighack2019.cleancity.presentation.results.adapter.PointHolder

class ReportsAdapter(var items: List<Point>) : RecyclerView.Adapter<PointHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointHolder =
        PointHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PointHolder, position: Int) = holder.bind(items[position])

    interface OnItemClickListener {
        fun onClick(position: Int)
    }
}