package com.maestre.wisdompills.Model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maestre.wisdompills.Model.Note
import com.maestre.wisdompills.databinding.ItemNoteBinding

class NoteAdapter(private var notes: MutableList<Note>) : RecyclerView.Adapter<NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titulo.text = note.titulo
        holder.contenido.text = note.contenido
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateData(newNotes:List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }
}