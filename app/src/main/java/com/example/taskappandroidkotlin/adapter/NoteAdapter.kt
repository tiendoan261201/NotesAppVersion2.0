package com.example.taskappandroidkotlin.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taskappandroidkotlin.R
import com.example.taskappandroidkotlin.entities.Notes

class   NoteAdapter():RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    var listener: onItemClickListener? =null
    var notesList = ArrayList<Notes>()

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDesc)
        val dateTime:TextView = itemView.findViewById(R.id.tvDateTime)
        val cardViewNote: CardView = itemView.findViewById(R.id.cardViewNotes)
        val imgNotee : ImageView = itemView.findViewById(R.id.imgNotee)
        val webLink:TextView = itemView.findViewById(R.id.tvWebLink)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return notesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = notesList[position].title
        holder.tvDescription.text = notesList[position].description
        holder.dateTime.text = notesList[position].dateTime
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,R.anim.music_rcv_animation))

        if(notesList[position].color!=null){
            holder.cardViewNote.setCardBackgroundColor(Color.parseColor(notesList[position].color))
        }else{
            holder.cardViewNote.setCardBackgroundColor(Color.parseColor("#171C26"))
        }

        if(notesList[position].imgPath != null){
           holder.imgNotee.setImageURI(Uri.parse(notesList[position].imgPath))
            holder.imgNotee.visibility = View.VISIBLE
        }else{
            holder.imgNotee.visibility = View.GONE
        }
        if(notesList[position].webLink != null){
            holder.webLink.text = notesList[position].webLink
            holder.webLink.visibility = View.VISIBLE
        }else{
            holder.webLink.visibility = View.GONE
        }
        holder.cardViewNote.setOnClickListener {
            listener!!.onClicked(notesList[position].id!!)
        }
    }
    fun setData(arrNotesList: List<Notes>){
        notesList = arrNotesList as ArrayList<Notes>
    }
    fun setOnClickListener(listener1: onItemClickListener){
        listener = listener1
    }

    //create Interface
    interface onItemClickListener{
        fun onClicked(noteId:Int)
    }
}