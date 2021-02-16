package com.example.weighttracker.weightHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weighttracker.R
import com.example.weighttracker.database.WeightDatabase
import com.example.weighttracker.databinding.FragmentWeightHistoryBinding

class WeightHistoryFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWeightHistoryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_weight_history, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = WeightDatabase.getInstance(application).weightDatabaseDao
        val viewModelFactory = WeightHistoryViewModelFactory(dataSource, application)
        val weightHistoryViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(WeightHistoryViewModel::class.java)

        binding.lifecycleOwner = this
        binding.weightHistoryViewModel = weightHistoryViewModel

        weightHistoryViewModel.eventOnClearButtonClick.observe(this, Observer {
            if (it){
                weightHistoryViewModel.clearAll()
                weightHistoryViewModel.finishedClear()
            }
        })

        return binding.root
    }
}