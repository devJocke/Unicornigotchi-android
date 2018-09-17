package com.example.jocke.unicornigotchi.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.jocke.unicornigotchi.R
import com.example.jocke.unicornigotchi.dto.Care

class UnicornRecyclerViewAdapter(var care: List<Care> = emptyList(), var mainViewModel: MainViewModel) : RecyclerView.Adapter<UnicornHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): UnicornHolder {
        return UnicornHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.unicorn_recyclerview, viewGroup, false))
    }

    override fun onBindViewHolder(uHolder: UnicornHolder, position: Int) {
        uHolder.bindPerson(care[position], mainViewModel)
    }

    override fun getItemCount(): Int {
        return care.size
    }
}

class UnicornHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindPerson(care: Care, v: MainViewModel) = with(care) {
        val needButton = itemView.findViewById<ImageButton>(R.id.need_button)
        needButton.setOnClickListener {

            //TODO Traverse to next page or something
        }
    }
}
