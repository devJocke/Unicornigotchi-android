package com.example.jocke.unicornigotchi.ui.main

import android.support.v4.content.ContextCompat
import android.view.View
import com.example.jocke.unicornigotchi.R
import com.example.jocke.unicornigotchi.dto.Discipline
import com.example.jocke.unicornigotchi.dto.Need
import com.example.jocke.unicornigotchi.dto.Play
import com.example.jocke.unicornigotchi.dto.Toilet

class FragmentFactory {

    val listFragmentChecker = hashMapOf<String, FragmentOverhead>()

    private fun initializeFragmentChecker() {
        listFragmentChecker[Discipline::class.simpleName!!] = DisciplineOverhead()
        listFragmentChecker[Play::class.simpleName!!] = PlayOverhead()
        listFragmentChecker[Toilet::class.simpleName!!] = ToiletOverhead()
    }


    init {
        initializeFragmentChecker()
    }

    fun validateFragmentAndReturnFragment(clickedView: View, need: Need): Int {
        return listFragmentChecker[need::class.simpleName]!!.getFragment(clickedView)
    }


    class DisciplineOverhead : FragmentOverhead {
        override fun getFragment(clickedView: View): Int {
            clickedView.background = ContextCompat.getDrawable(clickedView.context, R.drawable.ic_sentiment_dissatisfied_black_24dp)
            return R.id.action_mainFragment_to_disciplineFragment
        }
    }

    class ToiletOverhead : FragmentOverhead {
        override fun getFragment(clickedView: View): Int {
            return R.id.action_mainFragment_to_toiletFragment
        }
    }

    class PlayOverhead : FragmentOverhead {
        override fun getFragment(clickedView: View): Int {
            return R.id.action_mainFragment_to_playFragment
        }
    }

    interface FragmentOverhead {
        fun getFragment(clickedView: View): Int
    }
}