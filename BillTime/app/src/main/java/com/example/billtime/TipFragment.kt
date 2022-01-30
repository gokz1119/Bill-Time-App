package com.example.billtime

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.billtime.databinding.FragmentTipBinding
import java.text.NumberFormat

class TipFragment : Fragment() {

    private lateinit var binding: FragmentTipBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tip, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.calculateButton.setOnClickListener { calculateTip() }
        binding.splitTheBillButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_tipFragment_to_splitFragment)
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun calculateTip() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if(cost == null) {
            binding.tipResult.text = "Please enter a valid cost of service"
            return
        }
        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        var tip = cost * tipPercentage
        val roundUp = binding.roundUpSwitch.isChecked
        if(roundUp) {
            tip = kotlin.math.ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}