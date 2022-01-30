package com.example.billtime

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.billtime.databinding.FragmentSplitBinding
import java.text.NumberFormat

class SplitFragment : Fragment() {

    private lateinit var binding: FragmentSplitBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_split, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.calculateButton.setOnClickListener { calculateSplit() }
        binding.tipCalculatorButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_splitFragment_to_tipFragment)
        }
        
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun calculateSplit() {
        val costInTextField = binding.billAmountEditText.text.toString()
        val cost = costInTextField.toDoubleOrNull()
        if(cost == null) {
            binding.splitResult.text = "Please enter a valid cost of service"
            return
        }
        val numOfPeopleTextField = binding.numPeopleEditText.text.toString()
        val numOfPeople = numOfPeopleTextField.toDoubleOrNull()
        if(numOfPeople == null) {
            binding.splitResult.text = "Please enter valid number of people(More than 1)"
            return
        }
        if(numOfPeople < 1) {
            binding.splitResult.text = "Please enter valid number of people(More than 1)"
            return
        }
        var split = cost / numOfPeople
        val roundUp = binding.roundUpSwitch.isChecked
        if(roundUp) {
            split = kotlin.math.ceil(split)
        }
        val formattedSplit = NumberFormat.getCurrencyInstance().format(split)
        binding.splitResult.text = getString(R.string.split_amount, formattedSplit)
    }
}