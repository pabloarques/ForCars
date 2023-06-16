package com.example.forcars

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.forcars.databinding.ActivityMainBinding
import com.example.forcars.ui.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            viewModel.handleSignInResult(resultCode, data)
        }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupBottomNav()
        setupObservers()
    }

    private fun showLoginScreen() {
        viewModel.signIn()
    }

    private fun setupObservers() {
        viewModel.startActivityEvent.observe(this) { event ->
            event.getContentIfNotHandled()?.let { intent ->
                signInLauncher.launch(intent)
            }
        }

        viewModel.user.observe(this) { user ->
            if (user != null) {
                Snackbar.make(binding.root, "Bienvenido " + user.displayName, Snackbar.LENGTH_SHORT)
                    .show()

            } else {
                Snackbar.make(binding.root, "No se ha podido iniciar sesión", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        if (viewModel.user.value == null && !viewModel.isLoggedIn()) {
            showLoginScreen()
        }
    }

    private fun setupBottomNav() {
        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    true
                }

                R.id.navigation_dashboard -> {
                    navController.navigate(R.id.navigation_dashboard)
                    true
                }

                R.id.navigation_notifications -> {
                    navController.navigate(R.id.navigation_notifications)
                    true
                }

                else -> false
            }
        }
    }
}