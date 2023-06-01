package com.example.forcars.ui.chat

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.forcars.R
import com.example.forcars.databinding.ActivityChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    private val viewModel by viewModels<ChatViewModel>()


    companion object {
        fun create(context: Context) = Intent(context, ChatActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        setupButtons()
        validateEmail()
    }

    private fun init() {
        with(binding) {
            btnChat.isEnabled = false
            btnChat.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.md_theme_dark_secondary, null))
        }
    }

    private fun setupButtons() {
        with(binding) {
            btnBack.setOnClickListener {
                finish()
            }

            btnChat.setOnClickListener {
                Toast.makeText(
                    this@ChatActivity,
                    "Se ha enviado al propietario el siguiente correo: " + itCorreo.text.toString(),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun validateEmail() {
        with(binding) {
            itCorreo.doAfterTextChanged { email ->
                val isValidEmail = viewModel.isValidEmail(email.toString())
                if (isValidEmail) {
                    itCorreo.setBackgroundResource(R.drawable.bg_edittext__valid)
                    btnChat.isEnabled = true
                    btnChat.backgroundTintList =
                        ColorStateList.valueOf(
                            resources.getColor(
                                R.color.md_theme_light_tertiaryContainer,
                                null
                            )
                        )
                } else {
                    itCorreo.setBackgroundResource(R.drawable.bg_edittext_invalid)
                    itCorreo.error = getString(R.string.invalid_email)
                    btnChat.isEnabled = false
                    btnChat.backgroundTintList =
                        ColorStateList.valueOf(
                            resources.getColor(
                                R.color.md_theme_dark_secondary,
                                null
                            )
                        )
                }
            }
        }
    }
}