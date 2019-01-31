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
import com.example.jocke.unicornigotchi.viewmodels.MainViewModel


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var unicornRecyclerView: RecyclerView
    private lateinit var unicornNeedsRecyclerview: UnicornNeedsRecyclerview
    private lateinit var fetchData: TextView
    private lateinit var firstNameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var happyBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.main_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Error handling
        setupViewModelAndRetrofit()
        getViews(view)
        setupObserverForUnicornAndNotifyAdapter()
        setupObserverForNeedsProgressbar()
        setupRecyclerView()

        happyBar.progressDrawable = context?.let { ContextCompat.getDrawable(it, R.drawable.custom_progressbar) }

        viewModel.fetchSumOfPositiveNeeds()

        fetchData.setOnClickListener {
            viewModel.fetchUnicorn(1)
        }
        viewModel.fetchUnicorn(1)

        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Initializes views without using synthetic for more clarity
     */
    private fun getViews(view: View) {
        view.apply {
            unicornRecyclerView = findViewById(R.id.unicorns_recyclerview)
            fetchData = findViewById(R.id.fetch_data)
            firstNameTextView = findViewById(R.id.firstname)
            lastNameTextView = findViewById(R.id.lastname)
            happyBar = findViewById(R.id.happy_bar)
        }
    }

    /**
     * Bind the @see[MainViewModel] with this fragment, we can now observe livedata from the viewmodel
     */
    private fun setupViewModelAndRetrofit() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.initializeRetrofit()
    }

    /**
     * Use this fragment lifecycle and listen to any changes from the unicorn livedata
     * Populates the views with unicorn information
     * Recyclerview retrieves the needs of the unicorn
     * @see Unicorn for parameters
     */
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

                unicornNeedsRecyclerview.need = listOfNeeds.toList()
                unicornNeedsRecyclerview.notifyDataSetChanged()
            }
        })
    }

    /**
     * Look for any changes done to the needs and update the happiness bar with the combining sum calculated based on how many needs there are
     */
    private fun setupObserverForNeedsProgressbar() {
        viewModel.getPositiveNeedsLiveData().observe(viewLifecycleOwner, Observer<NeedsSum> { needsSum ->
            needsSum?.let {
                happyBar.progress = needsSum.sumOfAllNeeds
            }
        })
    }

    /**
     * Responsible for delegating the fragment traversal
     */
    fun traverseToFragment(clickedView: View, need: Need) {
        (this.activity as MainActivity).goToFragment(clickedView, FragmentFactory().validateFragmentAndReturnFragment(clickedView, need))
    }

    private fun setupRecyclerView() {
        //TODO CALCULATE BASED ON THE NUMBER OF NEEEDS
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.reverseLayout = true
        unicornRecyclerView.layoutManager = staggeredGridLayoutManager
        unicornNeedsRecyclerview = UnicornNeedsRecyclerview(emptyList(), this)
        unicornRecyclerView.adapter = unicornNeedsRecyclerview
    }
}