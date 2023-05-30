package com.example.forcars.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.forcars.databinding.ActivityDetailBinding
import com.example.forcars.databinding.DetailCarBinding
import com.example.forcars.ui.common.utils.Constants.BASE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var _binding: DetailCarBinding

    private val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _binding = DetailCarBinding.inflate(layoutInflater, binding.llDetailCar, true)
        backtoHome()
        setupArgs()
    }

    private fun backtoHome() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupArgs() {
        with(binding) {
            tvNameCar.text =
                buildString { append(args.cars.marca + " " + args.cars.modelo + " " + args.cars.motor + " " + args.cars.cv + "cv") }
            tvPrecioCont.text = buildString { append(args.cars.price.toString() + "€") }
        }

        with(_binding) {
            tvDate.text = args.cars.year.toString()
            tvKm.text = buildString { append(args.cars.kilometros.toString() + " km") }
            tvCombustible.text = args.cars.combustible
            tvUbicacion.text = args.cars.ubicacion
            tvCv.text = buildString { append(args.cars.cv.toString() + " cv") }
            tvCambio.text = args.cars.cambio
        }

        Glide.with(this)
            .load(BASE_URL + args.cars.id + "/" + args.cars.image)
            .into(binding.ivPhotoCar)
    }
}