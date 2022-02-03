package com.scan.moviesapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem(val id:String, val owner:String, val secret:String, val server:String, val farm:Int, val title:String, val ispublic:Int, val isfriend:Int, val isfamily:Int): Parcelable