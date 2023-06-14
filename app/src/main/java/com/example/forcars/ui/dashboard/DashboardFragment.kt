package com.example.forcars.ui.dashboard

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.forcars.databinding.FragmentDashboardBinding
import com.example.forcars.ui.common.utils.Marcas
import com.example.forcars.ui.common.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.InputStream
import java.util.Calendar

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DashboardViewModel>()
    private val years = mutableListOf<Int>()
    private var selectedImageUri: Uri? = null

    private val pickMedia = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            binding.imgCar.setImageURI(it)
            selectedImageUri = it
        } else {
            Toast.makeText(
                requireContext(),
                "No se ha seleccionado ninguna imagen",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

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
        validateFields()
        clearFields()
        setupObserves()
        setupListener()
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

    private fun setupListener() {
        binding.btnSubir.setOnClickListener {
            pickMedia.launch("image/*")
        }
    }

    private fun getRealPathFromUri(uri: Uri): String {
        val contentResolver: ContentResolver = requireContext().contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        inputStream?.close() // Cierre el InputStream después de usarlo
        return uri.toString() // Devuelve la Uri como String
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

    private fun clearFields() {
        with(binding) {
            btnReset.setOnClickListener {
                spMarca.setSelection(0)
                etModelo.text?.clear()
                etCombustible.text?.clear()
                etKilometros.text?.clear()
                etCv.text?.clear()
                etPrecio.text?.clear()
                etCambio.text?.clear()
                etMotor.text?.clear()
                etUbicacion.text?.clear()
                etTelefono.text?.clear()
            }
        }
    }

    private fun sendAnuncio() {
        with(binding) {
            btnSubirAnuncio.setOnClickListener {

                val marca = spMarca.selectedItem.toString()
                val modelo = etModelo.text.toString()
                val combustible = etCombustible.text.toString()
                val kilometros = etKilometros.text.toString()
                val cv = etCv.text.toString()
                val price = etPrecio.text.toString()
                val cambio = etCambio.text.toString()
                val year = years[npYear.value]
                val motor = etMotor.text.toString()
                val image = selectedImageUri?.let { getRealPathFromUri(it) }
                val ubicacion = etUbicacion.text.toString()
                val telefono = etTelefono.text.toString()

                val km = viewModel.validateInt(kilometros)
                val pr = viewModel.validateInt(price)
                val cvv = viewModel.validateInt(cv)
                val tlf = viewModel.validateInt(telefono)


                viewModel.postCar(
                    marca,
                    modelo,
                    combustible,
                    cambio,
                    cvv,
                    year,
                    image.toString(),
                    motor,
                    ubicacion,
                    pr,
                    km,
                    tlf,
                    ""
                )
            }
        }
    }

    private fun validateFields() {
        with(binding) {
            btnSubirAnuncio.setOnClickListener {
                if (etModelo.text.isNullOrBlank() || etCombustible.text.isNullOrBlank() ||
                    etKilometros.text.isNullOrBlank() || etCv.text.isNullOrBlank() ||
                    etPrecio.text.isNullOrBlank() || etCambio.text.isNullOrBlank() ||
                    etMotor.text.isNullOrBlank() || etUbicacion.text.isNullOrBlank() ||
                    etTelefono.text.isNullOrBlank()
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Por favor, rellene todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    sendAnuncio()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}