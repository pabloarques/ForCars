package com.example.forcars.ui.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.forcars.databinding.ActivityDetailBinding
import com.example.forcars.databinding.DetailCarBinding
import com.example.forcars.ui.chat.ChatActivity
import com.example.forcars.ui.common.utils.Constants.BASE_URL
import com.example.forcars.ui.common.utils.Constants.REQUEST_CALL_PHONE_PERMISSION
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
        setupButtons()
    }

    private fun backtoHome() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun navigateToChat() {
        startActivity(ChatActivity.create(this))
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
            Log.e("TAG", "setupArgs: " + args.cars.telefono.toString())
        }

        if (args.cars.marca == "Seat") {
            Glide.with(this)
                .load(args.cars.imageURL)
                .into(binding.ivPhotoCar)

        } else if (args.cars.marca == "Mercedes") {
            Glide.with(this)
                .load(args.cars.imageURL)
                .into(binding.ivPhotoCar)

        } else if (args.cars.marca == "Toyota") {
            Glide.with(this)
                .load(args.cars.imageURL)
                .into(binding.ivPhotoCar)

        } else {
            Glide.with(this)
                .load(BASE_URL + args.cars.id + "/" + args.cars.image)
                .into(binding.ivPhotoCar)
        }
    }

    private fun setupButtons() {
        with(binding) {
            btnLlamar.setOnClickListener {
                checkPermision()
            }
            btnChat.setOnClickListener {
                navigateToChat()
            }
        }
    }

    private fun checkPermision() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            callPhoneNumber()
        } else {
            // Solicitar el permiso de realizar llamadas
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CALL_PHONE_PERMISSION
            )
        }
    }

    private fun callPhoneNumber() {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel: ${args.cars.telefono}")
        startActivity(dialIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber()
            } else {
                Toast.makeText(this, "Permiso Denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
