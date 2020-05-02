package com.brocodes.catspics.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brocodes.catspics.R
import com.brocodes.catspics.databinding.ActivityMainBinding
import com.brocodes.catspics.di.AppContainer
import com.brocodes.catspics.view.utils.EndlessScrollListener
import com.brocodes.catspics.view.utils.KittenRecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var kittenRecyclerViewAdapter: KittenRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainActivityDataBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val kittenRecyclerView = mainActivityDataBinding.kittenRecyclerview
        val kittenLayoutManager = LinearLayoutManager(this)
        kittenRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = kittenLayoutManager
        }

        val appContainer = AppContainer(this)

        val kittenViewModel = appContainer.kittenViewModel
        kittenViewModel.getKittens().observe(this, Observer {
            kittenRecyclerViewAdapter =
                KittenRecyclerViewAdapter(it)
            kittenRecyclerView.adapter = kittenRecyclerViewAdapter
        })

        kittenRecyclerView.addOnScrollListener(object : EndlessScrollListener(kittenLayoutManager){
            override fun loadMore(pageNumber: Int, recyclerView: RecyclerView) {
                kittenViewModel.loadMoreKittens(pageNumber)
                kittenRecyclerViewAdapter.notifyDataSetChanged()
            }
        })

    }
}


