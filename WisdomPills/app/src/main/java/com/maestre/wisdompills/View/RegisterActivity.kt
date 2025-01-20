package com.maestre.wisdompills.View

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.maestre.wisdompills.ViewModel.NoteViewModel
import com.maestre.wisdompills.ViewModel.UserViewModel
import com.maestre.wisdompills.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    val viewmodel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnReg.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString()
            val nickname = binding.editTextText.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val password2 = binding.editTextRepeatPassword.text.toString()
            if (password == password2) {
                viewmodel.addUser(email, password, nickname)
                Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }
}