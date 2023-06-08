package com.example.forcars.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.forcars.databinding.FragmentDashboardBinding
import com.example.forcars.ui.common.utils.Marcas
import com.example.forcars.ui.common.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DashboardViewModel>()
    private val years = mutableListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        setupNumerPicker()
        sendAnuncio()
        setupObserves()
    }

    private fun setupObserves() {
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        // Mostrar mensaje de éxito al usuario
                        Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
                    }

                    is UiState.Error -> {
                        // Mostrar mensaje de error al usuario
                        Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
                    }

                    is UiState.Loading -> {
                        // Mostrar progreso o realizar acciones relacionadas con la carga
                    }
                }
            }
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, Marcas.marca.drop(0)
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerBrands: Spinner = binding.spMarca
        spinnerBrands.adapter = adapter
        spinnerBrands.setSelection(0)
    }

    private fun setupNumerPicker() {
        val numberPicker: NumberPicker = binding.npYear
        val yearRange = 1970..2023
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)


        for (year in currentYear - 1 downTo yearRange.first) {
            years.add(year)
        }

        years.add(currentYear)

        for (year in currentYear + 1..yearRange.last) {
            years.add(year)
        }

        numberPicker.minValue = 0
        numberPicker.maxValue = years.size - 1
        numberPicker.displayedValues = years.map { it.toString() }.toTypedArray()
    }

    private fun sendAnuncio() {
        with(binding) {
            btnSubirAnuncio.setOnClickListener {
                val marca = spMarca.selectedItem.toString()
                val modelo = etModelo.text.toString()
                val combustible = etCombustible.text.toString()
                val kilometros = etKilometros.text.toString().toInt()
                val cv = etCv.text.toString().toInt()
                val price = etPrecio.text.toString().toInt()
                val cambio = etCambio.text.toString()
                val year = years[npYear.value]
                val motor = etMotor.text.toString()
                val image = ""
                val ubicacion = etUbicacion.text.toString()
                val telefono = etTelefono.text.toString().toInt()
                //correo

                viewModel.postCar(
                    marca,
                    modelo,
                    combustible,
                    cambio,
                    cv,
                    year,
                    image,
                    motor,
                    ubicacion,
                    price,
                    kilometros,
                    telefono,
                    ""
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}