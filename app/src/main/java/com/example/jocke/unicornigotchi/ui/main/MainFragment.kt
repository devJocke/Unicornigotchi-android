package com.example.jocke.unicornigotchi.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.example.jocke.unicornigotchi.R
import com.example.jocke.unicornigotchi.dto.Care
import com.example.jocke.unicornigotchi.dto.Unicorn


class MainFragment : android.support.v4.app.Fragment() {

    private lateinit var unicornRecyclerView: RecyclerView
    private lateinit var fetchData: TextView
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var happyBar: ProgressBar

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var unicornRecyclerViewAdapter: UnicornRecyclerViewAdapter

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
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        //TODO CALCULATE BASED ON THE NUMBER OF NEEEDS
        val stagg = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        stagg.reverseLayout = true
        unicornRecyclerView.layoutManager = stagg
        unicornRecyclerViewAdapter = UnicornRecyclerViewAdapter(emptyList(), viewModel)
        unicornRecyclerView.adapter = unicornRecyclerViewAdapter
    }

    private fun getViews(view: View) {
        unicornRecyclerView = view.findViewById(R.id.unicorns_recyclerview)
        fetchData = view.findViewById(R.id.fetch_data)
        firstName = view.findViewById(R.id.firstname)
        lastName = view.findViewById(R.id.lastname)
        happyBar = view.findViewById(R.id.happy_bar)
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
            val listofCare = mutableListOf<Care>()
            listofCare.addAll(listOf(it!![0].care))
            listofCare.addAll(listOf(it!![0].care))
            listofCare.addAll(listOf(it!![0].care))
            listofCare.addAll(listOf(it!![0].care))
            listofCare.addAll(listOf(it!![0].care))



            it?.let {
                unicornRecyclerViewAdapter.care = listofCare
                unicornRecyclerViewAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun incrementTest() {
        viewModel.getCount().observe(this, Observer<Int> {
            //it?.let { it1 -> Toast.makeText(context, it1.toString(), Toast.LENGTH_SHORT).show() }
            fetchData.text = it.toString()
        })
    }
}
