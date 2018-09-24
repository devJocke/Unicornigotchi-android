package com.example.jocke.unicornigotchi.ui.main

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.jocke.unicornigotchi.R
import com.example.jocke.unicornigotchi.dto.Care

class UnicornRecyclerViewAdapter(var care: List<Care> = emptyList(), var mainViewModel: MainViewModel) : RecyclerView.Adapter<CareHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CareHolder {
        return CareHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.needs_recyclerview, viewGroup, false))
    }

    override fun onBindViewHolder(uHolder: CareHolder, position: Int) {
        uHolder.bindPerson(care[position], mainViewModel)
    }

    override fun getItemCount(): Int {
        return care.size
    }
}

class CareHolder(view: View) : RecyclerView.ViewHolder(view), FragmentSwitch {


    private var mainFragment: MainFragment? = null

    override fun fragment(fragment: Fragment) {
        if(fragment is MainFragment) {
            mainFragment = fragment
        }
        //FragmentFactory()
    }


    fun bindPerson(care: Care, viewmodel: MainViewModel) = with(care) {
        var needButton = itemView.findViewById<ImageButton>(R.id.discipline_need_button)

        //Todo Create json file
//        if (care is Discipline) {
//            needButton.setBackgroundResource(R.drawable.ic_sentiment_dissatisfied_black_24dp)
//            needButton.setOnClickListener {
//                mainFragment?.let {
//                    it.goToFragment()
//                }
//                //TODO Traverse to next page or something
//            }
//        }
    }
}
