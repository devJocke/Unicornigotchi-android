package com.example.jocke.unicornigotchi.ui.main

import android.support.v4.app.Fragment

public class FragmentFactory {

    val listFragmentChecker = hashMapOf<String, FragmentOverhead>()

    private fun initializeFragmentChecker() {
        listFragmentChecker[DisciplineFragment::class.simpleName!!] = Discipline()
    }


    init {
        initializeFragmentChecker()
    }

    public fun validateFragmentAndReturnFragment(fragment: Fragment): Fragment? {
        return listFragmentChecker[fragment::class.simpleName]?.getFragment()
    }


    class Discipline : FragmentOverhead {
        override fun getFragment(): DisciplineFragment {
            return DisciplineFragment()
        }
    }

    interface FragmentOverhead {
        fun getFragment(): Fragment
    }
}