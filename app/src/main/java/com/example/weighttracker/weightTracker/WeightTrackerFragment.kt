package com.example.weighttracker.weightTracker

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
import com.example.weighttracker.databinding.FragmentWeightTrackerBinding

class WeightTrackerFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWeightTrackerBinding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_weight_tracker, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = WeightDatabase.getInstance(application).weightDatabaseDao
        val viewModelFactory = WeightTrackerViewModelFactory(dataSource, application)
        val weightTrackerViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(WeightTrackerViewModel::class.java)

        binding.lifecycleOwner = this
        binding.weightTrackerViewModel = weightTrackerViewModel

        weightTrackerViewModel.allEntries.observe(this, Observer {
            binding.avgWeightText.text = weightTrackerViewModel.calcAvg(it).toString()
        })

        return binding.root
    }
}