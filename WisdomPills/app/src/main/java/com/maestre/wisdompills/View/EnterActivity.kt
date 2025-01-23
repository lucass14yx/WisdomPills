package com.maestre.wisdompills.View

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.maestre.wisdompills.Model.adapter.NoteAdapter
import com.maestre.wisdompills.R
import com.maestre.wisdompills.ViewModel.NoteViewModel
import com.maestre.wisdompills.databinding.ActivityEnterBinding

class EnterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnterBinding
    private lateinit var myAdapter: NoteAdapter
    private val viewmodel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("idUser")?:return
        Toast.makeText(this, "idUsuario: $userId", Toast.LENGTH_SHORT).show()
        val toolbar: MaterialToolbar = binding.materialToolbar
        setSupportActionBar(toolbar)


        // Configuramos el RecyclerView
        initRecyclerView()

        viewmodel.notesLiveData.observe(this) { notes ->
            val userId = intent.getStringExtra("idUser")
            val userNotes = notes.filter { it.idUser == userId }
            myAdapter.updateData(userNotes)
        }

        binding.btnAdd.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()

            binding.titleEditText.text.clear()
            binding.contentEditText.text.clear()
            viewmodel.addNote(title, content, userId)


        }


    }
    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        binding.notesRecyclerView.layoutManager = manager
        myAdapter = NoteAdapter(mutableListOf())
        binding.notesRecyclerView.adapter = myAdapter

        // Crear un DividerItemDecoration y agregarlo al RecyclerView
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.notesRecyclerView.addItemDecoration(decoration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                true
            }

            R.id.menu_info -> {
                true
            }

            else -> false
        }
    }
}