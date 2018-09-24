package com.example.jocke.unicornigotchi.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import com.example.jocke.unicornigotchi.MainActivity
import com.example.jocke.unicornigotchi.R
import com.example.jocke.unicornigotchi.dto.Unicorn


class MainFragment : Fragment() {

    private lateinit var unicornRecyclerView: RecyclerView
    private lateinit var fetchData: TextView
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var happyBar: ProgressBar
    private lateinit var toiletNeedButton: ImageButton
    private lateinit var playNeedButton: ImageButton
    private lateinit var disciplineNeedButton: ImageButton


    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
//    private lateinit var unicornRecyclerViewAdapter: UnicornRecyclerViewAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewModel()
        getViews(view)
        setupRecyclerView()

        val progressbarDrawable = context?.let { ContextCompat.getDrawable(it, R.drawable.custom_progressbar) }
        happyBar.progressDrawable = progressbarDrawable

        fetchData.setOnClickListener {
            viewModel.fetchData()
        }
        viewModel.fetchData()

        setupUglyHardcodedStuff()

        super.onViewCreated(view, savedInstanceState)
    }


    private fun getViews(view: View) {
//        unicornRecyclerView = view.findViewById(R.id.unicorns_recyclerview)
        fetchData = view.findViewById(R.id.fetch_data)
        firstName = view.findViewById(R.id.firstname)
        lastName = view.findViewById(R.id.lastname)
        happyBar = view.findViewById(R.id.happy_bar)
        disciplineNeedButton = view.findViewById(R.id.discipline_need_button)
        playNeedButton = view.findViewById(R.id.play_need_button)
        toiletNeedButton = view.findViewById(R.id.toilet_need_button)

    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.initializeRetrofit()
        viewModel.fetchData()
    }


    private fun setupObservers() {
        viewModel.getUnicorns().observe(this, Observer<List<Unicorn>> {
            it?.let {
                firstName.text = it[0].firstName
                lastName.text = it[0].lastName
            }
//TODO REMOVE CARE FROM API AND ONLY USE LIST?

//            it?.let {
//                unicornRecyclerViewAdapter.care = addNeeds(it[0].care)
//                unicornRecyclerViewAdapter.notifyDataSetChanged()
//            }
        })
    }

    private fun setupUglyHardcodedStuff() {
        val mainActivity = (this.activity as MainActivity)

        disciplineNeedButton.setOnClickListener {
            mainActivity.goToFragment(it, R.id.action_mainFragment_to_disciplineFragment)
            //mainActivity.goToFragment(FragmentFactory().validateFragmentAndReturnFragment(fragmentToGoTo!!)!!)
        }
        playNeedButton.setOnClickListener {
            mainActivity.goToFragment(it, R.id.action_mainFragment_to_playFragment)
        }

        toiletNeedButton.setOnClickListener {
            mainActivity.goToFragment(it, R.id.action_mainFragment_to_toiletFragment)
            //   mainActivity.goToFragment(FragmentFactory().validateFragmentAndReturnFragment(fragmentToGoTo!!)!!)
        }

    }


    private fun setupRecyclerView() {
//        //TODO CALCULATE BASED ON THE NUMBER OF NEEEDS
//        val stagg = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
//        stagg.reverseLayout = true
//        unicornRecyclerView.layoutManager = stagg
//        unicornRecyclerViewAdapter = UnicornRecyclerViewAdapter(emptyList(), viewModel)
//        unicornRecyclerView.adapter = unicornRecyclerViewAdapter
    }
}

interface FragmentSwitch {
    fun fragment(fragment: Fragment)
}