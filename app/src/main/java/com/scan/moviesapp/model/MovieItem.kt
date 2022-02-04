package com.scan.moviesapp.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieItem(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int
): Parcelable
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, item: MovieItem) {
        val imagePath =  "https://farm${item.farm}.static.flickr.com/${item.server}/${item.id}_${item.secret}.jpg"
        Glide.with(view.context)
            .load(imagePath)
            .placeholder(com.scan.moviesapp.R.drawable.placeholder)
            .into(view)
    }
