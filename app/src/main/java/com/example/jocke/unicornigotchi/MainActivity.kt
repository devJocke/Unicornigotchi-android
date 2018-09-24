package com.example.jocke.unicornigotchi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.host_fragment, MainFragment.newInstance())
//                    .commitNow()
//        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.host_fragment).navigateUp()
    }

    fun goToFragment(view: View, id: Int) {
        findNavController(view).navigate(id)
    }
}
