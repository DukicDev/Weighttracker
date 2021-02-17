package com.example.weighttracker.weightTracker

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
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

        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application
        val dataSource = WeightDatabase.getInstance(application).weightDatabaseDao
        val viewModelFactory = WeightTrackerViewModelFactory(dataSource, application)
        val weightTrackerViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(WeightTrackerViewModel::class.java)

        binding.lifecycleOwner = this
        binding.weightTrackerViewModel = weightTrackerViewModel

        weightTrackerViewModel.allEntries.observe(this, Observer {
            binding.avgWeightText.text = String.format("%.1f", weightTrackerViewModel.calcAvg(it))
        })

        weightTrackerViewModel.eventOnAddButtonClick.observe(this, Observer {
            if (it){
                val newWeight = binding.addWeightText.text.toString()
                binding.currentWeightText.text = newWeight
                weightTrackerViewModel.addWeight(newWeight.toDouble())
                weightTrackerViewModel.finishedOnAdd()
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || return super.onOptionsItemSelected(item)
    }




}