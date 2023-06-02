package com.example.forcars.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.forcars.databinding.FragmentNotificationsBinding
import com.example.forcars.ui.MainViewModel
import com.example.forcars.ui.common.EventObserver
import com.firebase.ui.auth.AuthUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NotificationsViewModel>()
    private val mviewModel by viewModels<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUser()
        setupListeners()
    }

    private fun setupUser() {
        mviewModel.user.observe(viewLifecycleOwner) { user ->
            with(binding) {
                if (user != null) {
                    tvUserNameText.text = user.displayName
                    tvEmailText.text = user.email
                }
            }
        }

        viewModel.startMainActivityEvent.observe(viewLifecycleOwner, EventObserver {
            startMainActivity()
        })
    }

    private fun startMainActivity() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        startActivity(signInIntent)
    }

    private fun setupListeners() {
        with(binding) {
            btnLogOut.setOnClickListener {
                viewModel.logout()
                mviewModel.signIn()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}