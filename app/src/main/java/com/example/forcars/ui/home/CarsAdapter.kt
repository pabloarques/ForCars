package com.example.forcars.ui.home

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.forcars.databinding.ItemCarsViewBinding
import com.example.forcars.entity.Cars

class CarsAdapter {

    inner class ViewHolder(private val binding: ItemCarsViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(car: Cars) {
            binding.tvPrecio.text = car.price
            binding.tvModelo.text = car.marca + " " + car.modelo + " " + car.motor + " " + car.cv
            binding.tvDetalles.text = car.ubicacion + " " + car.combustible + " " + car.kilometros
        }
    }

}