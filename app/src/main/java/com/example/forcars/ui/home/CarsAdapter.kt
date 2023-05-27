package com.example.forcars.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.forcars.databinding.ItemCarsViewBinding
import com.example.forcars.entity.Cars

class CarsAdapter : RecyclerView.Adapter<CarsAdapter.CarsViewHolder>() {

    private val cars: MutableList<Cars> = mutableListOf()

    inner class CarsViewHolder(private val binding: ItemCarsViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(car: Cars) {
            binding.tvPrecio.text = car.price.toString() + " €"
            binding.tvModelo.text = car.marca + " " + car.modelo + " " + car.motor + " " + car.cv + "CV"
            binding.tvDetalles.text = car.ubicacion + " | " + car.combustible + " | " + car.kilometros + " km"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCarsViewBinding.inflate(inflater, parent, false)
        return CarsViewHolder(binding)
    }

    override fun getItemCount(): Int = cars.size

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val car = cars[position]
        holder.bind(car)
    }

    fun submitList(newCars: List<Cars>) {
        val diffResult = DiffUtil.calculateDiff(CarsDiffCallback(cars, newCars))
        cars.clear()
        cars.addAll(newCars)
        diffResult.dispatchUpdatesTo(this)

    }

    class CarsDiffCallback(
        private val oldList: List<Cars>, private val newList: List<Cars>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

}