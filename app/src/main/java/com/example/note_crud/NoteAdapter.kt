package com.example.note_crud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(val notes: ArrayList<Note>, val listener: onAdapterListener, val delete: onDeleteListener): RecyclerView.Adapter<NoteAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent, false))
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        val position = notes[position]
        holder.textNote.text = position.note
        holder.textNote.setOnClickListener {
            listener.onClick( position )
        }
        holder.imageDelete.setOnClickListener {
            delete.onClick( position )
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textNote = view.findViewById<TextView>(R.id.tvNote)
        val imageDelete = view.findViewById<ImageView>(R.id.ivDelete)
    }

    fun setData(data: List<Note>){
        notes.clear()
        notes.addAll(data)
        notifyDataSetChanged()
    }

    interface onAdapterListener {
        fun onClick(note: Note)
    }

    interface onDeleteListener {
        fun onClick(note: Note)
    }


}