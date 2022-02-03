package com.scan.moviesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.scan.moviesapp.R
import com.scan.moviesapp.adapter.MoviesPagingAdapter
import com.scan.moviesapp.databinding.ActivityMainBinding
import com.scan.moviesapp.model.MovieItem
import com.scan.moviesapp.ui.viewmodel.MainActivityViewModel
import com.scan.moviesapp.utils.OnAdClicked
import com.scan.moviesapp.utils.OnMovieClicked
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity(), OnMovieClicked, OnAdClicked {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var recyclerAdapter: MoviesPagingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        MobileAds.initialize(this, object : OnInitializationCompleteListener {
            override fun onInitializationComplete(p0: InitializationStatus) {
                Toast.makeText (this@MainActivity, "AdMob Sdk Initialize $p0", Toast.LENGTH_LONG).show();
            }

        })
        initRecyclerView()
        getData()
    }

    private fun initRecyclerView() {
        recyclerAdapter = MoviesPagingAdapter()
        recyclerAdapter.setListener(this@MainActivity,this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerAdapter}
    }

    private fun getData() {
        lifecycleScope.launchWhenCreated {
            viewModel.listResult.collectLatest {
                recyclerAdapter.submitData(it)
            }
        }
    }

    override fun onClick(movie: MovieItem) {

    }

    override fun onClick() {

    }
}