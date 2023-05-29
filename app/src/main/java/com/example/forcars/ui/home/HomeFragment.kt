package com.example.forcars.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forcars.databinding.FragmentHomeBinding
import com.example.forcars.entity.Cars
import com.example.forcars.ui.common.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var adapter: CarsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.getCars()
    }

    private fun setupRecyclerView() {
        adapter = CarsAdapter()
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(car: Cars) {
                Log.d("CAR", car.toString())
            }
        })

        binding.rvHome.adapter = adapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch { viewModel.cars.collect { adapter.submitList(it) } }
        lifecycleScope.launch { viewModel.error.collect { Log.e("ERROR", it) } }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}