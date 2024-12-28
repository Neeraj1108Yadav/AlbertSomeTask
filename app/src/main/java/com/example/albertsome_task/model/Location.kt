package com.example.albertsome_task.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserLocation(
    val street: Street,
    val city: String,
    val state:String,
    val country:String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone : TimeZone,
) : Parcelable

@Parcelize
data class Street(
    val number: Int,
    val name:String
) : Parcelable

@Parcelize
data class Coordinates(
    val latitude: Double,
    val longitude:Double
) : Parcelable

@Parcelize
data class TimeZone(
    val offset:String,
    val description:String
) : Parcelable

