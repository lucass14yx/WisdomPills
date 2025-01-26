@file:Suppress("DEPRECATION")

package com.maestre.wisdompills.View

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
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
        super.onCreate(savedInstanceState)
        // Inicializar las preferencias
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // Configurar el tema antes de inflar las vistas
        setupThemeAndMode()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeUserData()
    }

    private fun setupThemeAndMode() {
        // Obtener el tema guardado en SharedPreferences
        val themeName = sharedPreferences.getString("pref_themes", "WisdomPillsTheme") ?: "WisdomPillsTheme"
        when (themeName) {
            "WisdomPillsTheme" -> setTheme(R.style.WisdomPillsTheme)
            else -> setTheme(R.style.WisdomPillsTheme) // Tema predeterminado en caso de error
        }

        // Detectar si el sistema está en modo oscuro o claro
        val isDarkModeEnabled = isSystemInDarkMode()

        // Configurar el modo de noche de la app
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        // Guardar el estado en SharedPreferences
        sharedPreferences.edit().putBoolean("def_nightmode", isDarkModeEnabled).apply()
    }

    /**
     * Método para determinar si el sistema está en modo oscuro
     */
    private fun isSystemInDarkMode(): Boolean {
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
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

