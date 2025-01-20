@file:Suppress("DEPRECATION")

package com.maestre.wisdompills.View

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.maestre.wisdompills.R
import com.maestre.wisdompills.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        // Establecer el tema antes de inflar las vistas
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        //tema
        val themeName = sharedPreferences?.getString("pref_themes", "WisdomPillsTheme") ?: "WisdomPillsTheme"
        Toast.makeText(this, "Main-Tema: $themeName ", Toast.LENGTH_SHORT).show()
        // Lee el nombre del tema
        when (themeName) {
            "WisdomPillsTheme" -> setTheme(R.style.WisdomPillsTheme)
            "Base.Theme.WisdomPills" -> setTheme(R.style.Base_Theme_WisdomPills)
        }
        //modo nocturno
        val isDarkModeEnabled = sharedPreferences?.getBoolean("def_nigthmode", false) ?: false
        Toast.makeText(this, "Main-Modo Nocturno: $isDarkModeEnabled ", Toast.LENGTH_SHORT).show()
        if (isDarkModeEnabled){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnSignIn.setOnClickListener{
            val intent = Intent(this, EnterActivity::class.java)
            startActivity(intent)
        }

    }

}