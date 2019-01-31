package com.example.jocke.unicornigotchi.ui.main

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.jocke.unicornigotchi.R

class DisciplineFragment : Fragment() {

    private lateinit var disciplineBar: ProgressBar
    private lateinit var fabCherry: FloatingActionButton
    private lateinit var fabMenu: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.discipline_main, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disciplineBar = view.findViewById(R.id.discipline_bar)

        fabMenu = view.findViewById(R.id.fab_menu)
        fabCherry = view.findViewById(R.id.fab_cherry)
        disciplineBar.progressDrawable = context?.let { ContextCompat.getDrawable(it, R.drawable.custom_progressbar) }

        fabCherry.background = context?.let { ContextCompat.getDrawable(it, R.drawable.cherry) }
    }
}
