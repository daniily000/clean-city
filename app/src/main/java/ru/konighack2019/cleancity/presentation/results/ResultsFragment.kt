package ru.konighack2019.cleancity.presentation.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fr_results.*
import ru.konighack2019.cleancity.R
import ru.konighack2019.cleancity.model.HistoryEntry
import ru.konighack2019.cleancity.presentation.results.adapter.ReportsAdapter

class ResultsFragment : Fragment(){

    private val resultsViewModel = ResultsViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ru.konighack2019.cleancity.databinding.FrResultsBinding = DataBindingUtil.inflate(inflater, R.layout.fr_results, container, false)
        binding.lifecycleOwner = this
        binding.vm = resultsViewModel
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_results.adapter = ReportsAdapter(mutableListOf())
        recycler_results.layoutManager = LinearLayoutManager(this.activity)
    }
}