package com.example.jocke.unicornigotchi.ui.main

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.jocke.unicornigotchi.R
import com.example.jocke.unicornigotchi.dto.Need

class UnicornRecyclerViewAdapter(var need: List<Need> = emptyList(), val fragment: Fragment) : RecyclerView.Adapter<NeedHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): NeedHolder {
        return NeedHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.needs_recyclerview, viewGroup, false))
    }

    override fun onBindViewHolder(needHolder: NeedHolder, position: Int) {
        needHolder.bindPerson(need[position], fragment)
    }

    override fun getItemCount(): Int {
        return need.size
    }
}

class NeedHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindPerson(need: Need, fragment: Fragment) = with(need) {
        val needButton = itemView.findViewById<ImageButton>(R.id.need_button)

        //  needButton.background = ContextCompat.getDrawable(clickedView.context, R.drawable.ic_sentiment_dissatisfied_black_24dp)

        needButton.setOnClickListener {
            (fragment as? MainFragment)?.traverseToFragment(it, need)
            //TODO Traverse to next page or something
        }
    }
}
