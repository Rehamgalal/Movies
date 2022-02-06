package com.scan.moviesapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.ads.*
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.scan.moviesapp.R
import com.scan.moviesapp.adapter.MoviesPagingAdapter
import com.scan.moviesapp.databinding.ActivityMainBinding
import com.scan.moviesapp.model.MovieItem
import com.scan.moviesapp.ui.viewmodel.MainActivityViewModel
import com.scan.moviesapp.utils.OnAdClicked
import com.scan.moviesapp.utils.OnMovieClicked
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.coroutines.flow.collectLatest

@ExperimentalPagingApi
class MainActivity : AppCompatActivity(), OnMovieClicked, OnAdClicked {

    private var initialLayoutComplete = false
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var recyclerAdapter: MoviesPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        MobileAds.initialize(this) { }
        AppCenter.start(application, "{Your App Secret}", Analytics::class.java, Crashes::class.java)
        AppCenter.configure(application, "{Your App Secret}")
        if (AppCenter.isConfigured()) {
            AppCenter.start(Analytics::class.java)
            AppCenter.start(Crashes::class.java)
        }
        MobileAds.setRequestConfiguration(
                RequestConfiguration.Builder()
                        .setTestDeviceIds(listOf("ABCDEF012345"))
                        .build()
        )
        initRecyclerView()
        getData()
    }

    private fun initRecyclerView() {
        recyclerAdapter = MoviesPagingAdapter()
        recyclerAdapter.setListener(this@MainActivity, this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerAdapter
        }
    }

    private fun getData() {
        lifecycleScope.launchWhenCreated {
            viewModel.listResult.collectLatest {
                recyclerAdapter.submitData(it)
            }
        }
    }

    override fun onClick(movie: MovieItem) {
        val imagePath =
            "https://farm${movie.farm}.static.flickr.com/${movie.server}/${movie.id}_${movie.secret}.jpg"
        val list = mutableListOf<String>()
        list.add(imagePath)
        StfalconImageViewer.Builder(this, list) { imageView, image ->
            Glide.with(this@MainActivity)
                .load(imagePath)
                .placeholder(R.drawable.placeholder)
                .into(imageView!!)
        }.show()
    }

    override fun onClick() {

    }

}