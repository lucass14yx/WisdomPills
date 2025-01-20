package com.maestre.wisdompills.View

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    val viewmodel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: MaterialToolbar = binding.materialToolbar
        setSupportActionBar(toolbar)

        // Configuramos el RecyclerView
        initRecyclerView(viewmodel)

        //si cambian los datos, actualizar rv
        viewmodel.notesLiveData.observe(this) { notes ->
            myAdapter.updateData(notes)
        }

        binding.btnAdd.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            viewmodel.addNote(title, content)
        }


    }
    private fun initRecyclerView(viewmodel: NoteViewModel) {
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