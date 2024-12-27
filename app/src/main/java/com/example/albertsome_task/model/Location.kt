package com.example.albertsome_task.model

data class UserLocation(
    val street: Street,
    val city: String,
    val state:String,
    val country:String,
    val postCode:Int,
    val coordinates: Coordinates,
    val timezone : TimeZone,
)

data class Street(
    val number: Int,
    val name:String
)

data class Coordinates(
    val latitude: Double,
    val longitude:Double
)

data class TimeZone(
    val offset:String,
    val description:String
)

