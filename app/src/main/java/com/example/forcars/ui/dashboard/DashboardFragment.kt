package com.example.forcars.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.forcars.databinding.FragmentDashboardBinding
import com.example.forcars.ui.common.utils.Marcas
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DashboardViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        setupNumerPicker()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSpinner() {
        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                Marcas.marca.drop(1)
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerBrands: Spinner = binding.spMarca
        spinnerBrands.adapter = adapter
        spinnerBrands.setSelection(0)
    }

    private fun setupNumerPicker() {
        val numberPicker: NumberPicker = binding.npYear
        val yearRange = 1970..2023
        val currentYear = Calendar.getInstance().get(Calendar.YEAR) // Obtén el año actual

        val years = mutableListOf<String>()
        for (year in currentYear - 1 downTo yearRange.first) {
            years.add(year.toString())
        }

        years.add(currentYear.toString())

        for (year in currentYear + 1..yearRange.last) {
            years.add(year.toString())
        }

        numberPicker.minValue = 0
        numberPicker.maxValue = years.size - 1
        numberPicker.displayedValues = years.toTypedArray()
    }
}