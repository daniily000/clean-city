package com.teamtwothree.kartasvalokapp.presentation.results.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.konighack2019.cleancity.R
import ru.konighack2019.cleancity.model.Point

class ReportsAdapter(var items: List<Point>) : RecyclerView.Adapter<PointDetailsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointDetailsHolder =
        PointDetailsHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PointDetailsHolder, position: Int) = holder.bind(items[position])

    interface OnItemClickListener {
        fun onClick(position: Int)
    }
}