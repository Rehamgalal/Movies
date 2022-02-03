package com.scan.moviesapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MoviesResponse(val page:Int, val pages:Long, val perpage:Int,val total:Long, val photo:ArrayList<MovieItem>): Parcelable