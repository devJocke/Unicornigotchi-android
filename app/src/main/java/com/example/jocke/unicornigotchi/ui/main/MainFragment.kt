package com.example.jocke.unicornigotchi.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.example.jocke.unicornigotchi.MainActivity
import com.example.jocke.unicornigotchi.R
import com.example.jocke.unicornigotchi.dto.Need
import com.example.jocke.unicornigotchi.dto.NeedsSum
import com.example.jocke.unicornigotchi.dto.Unicorn


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var unicornRecyclerView: RecyclerView
    private lateinit var unicornRecyclerViewAdapter: UnicornRecyclerViewAdapter
    private lateinit var fetchData: TextView
    private lateinit var firstNameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var happyBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.main_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Error handling
        setupViewModel()
        getViews(view)
        setupObserverForUnicornAndNotifyAdapter()
        setupObserverForNeedsSum()
        setupRecyclerView()


        happyBar.progressDrawable = context?.let { ContextCompat.getDrawable(it, R.drawable.custom_progressbar) }

        viewModel.fetchSumOfPositiveNeeds()

        fetchData.setOnClickListener {
            viewModel.fetchUnicorn(1)
        }
        viewModel.fetchUnicorn(1)

        super.onViewCreated(view, savedInstanceState)
    }


    private fun getViews(view: View) {
        unicornRecyclerView = view.findViewById(R.id.unicorns_recyclerview)
        fetchData = view.findViewById(R.id.fetch_data)
        firstNameTextView = view.findViewById(R.id.firstname)
        lastNameTextView = view.findViewById(R.id.lastname)
        happyBar = view.findViewById(R.id.happy_bar)

    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.initializeRetrofit()
    }


    private fun setupObserverForUnicornAndNotifyAdapter() {
        viewModel.getUnicornLiveData().observe(viewLifecycleOwner, Observer<Unicorn> { unicorn ->
            val listOfNeeds = mutableListOf<Need>()
            unicorn?.let {
                it.apply {
                    firstNameTextView.text = firstName
                    lastNameTextView.text = lastName
                    listOfNeeds.add(care.discipline)
                    listOfNeeds.add(care.play)
                    listOfNeeds.add(care.toilet)
                }
                //TODO REMOVE CARE FROM API AND ONLY USE LIST?

                unicornRecyclerViewAdapter.need = listOfNeeds.toList()
                unicornRecyclerViewAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun setupObserverForNeedsSum() {
        viewModel.getPositiveNeedsLiveData().observe(viewLifecycleOwner, Observer<NeedsSum> { needsSum ->

            needsSum?.let {
                happyBar.progress = needsSum.sumOfAllNeeds
            }
        })
    }


    fun traverseToFragment(clickedView: View, need: Need) {
        (this.activity as MainActivity).goToFragment(clickedView, FragmentFactory().validateFragmentAndReturnFragment(clickedView, need))
    }

    private fun setupRecyclerView() {
        //TODO CALCULATE BASED ON THE NUMBER OF NEEEDS
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.reverseLayout = true
        unicornRecyclerView.layoutManager = staggeredGridLayoutManager
        unicornRecyclerViewAdapter = UnicornRecyclerViewAdapter(emptyList(), this)
        unicornRecyclerView.adapter = unicornRecyclerViewAdapter
    }
}