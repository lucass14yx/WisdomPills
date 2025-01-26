@file:Suppress("DEPRECATION")

package com.maestre.wisdompills.View

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.maestre.wisdompills.R
import com.maestre.wisdompills.ViewModel.UserViewModel
import com.maestre.wisdompills.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: UserViewModel by viewModels()
    private var isDataLoaded = false // Variable para controlar si los datos están cargados

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializar las preferencias
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        // Configurar el tema antes de inflar las vistas
        setupThemeAndMode()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeUserData()
    }

    private fun setupThemeAndMode() {
        val themeName = sharedPreferences.getString("pref_themes", "WisdomPillsTheme") ?: "WisdomPillsTheme"
        when (themeName) {
            "WisdomPillsTheme" -> setTheme(R.style.WisdomPillsTheme)
            "Base.Theme.WisdomPills" -> setTheme(R.style.Base_Theme_WisdomPills)
            else -> setTheme(R.style.WisdomPillsTheme) // Tema predeterminado en caso de error
        }

        val isDarkModeEnabled = sharedPreferences.getBoolean("def_nigthmode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeEnabled){ AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun setupListeners() {
        // Desactivar el botón de inicio de sesión hasta que se carguen los datos
        binding.btnSignIn.isEnabled = false

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {
            if (!isDataLoaded) {
                Toast.makeText(this, "Cargando datos, por favor espera...", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val users = viewModel.usersLiveData.value

            if (users.isNullOrEmpty()) {
                Toast.makeText(this, "No se encontraron usuarios registrados", Toast.LENGTH_SHORT).show()
            } else {
                val user = users.firstOrNull { it.email == email && it.password == password }
                if (user != null) {
                    val intent = Intent(this, EnterActivity::class.java)
                    intent.putExtra("idUser", user.idUser)
                    intent.putExtra("name", user.nickname)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeUserData() {
        // Observar los datos de usersLiveData
        viewModel.usersLiveData.observe(this) { users ->
            if (users != null) {
                isDataLoaded = true
                binding.btnSignIn.isEnabled = true // Habilitar el botón una vez que los datos estén cargados
            } else {
                isDataLoaded = false
                binding.btnSignIn.isEnabled = false
            }
        }
    }
}

