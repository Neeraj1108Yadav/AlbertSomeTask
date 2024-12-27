package com.example.albertsome_task.model

data class UserResult(
    val result: List<User>
)

data class User(
    val gender:String,
    val name: UserName,
    val location: UserLocation,
    val email:String,
    val dob: UserDateOfBirth,
    val phone:String,
    val cell:String,
    val picture: UserPicture
)

data class UserName(
    val title:String,
    val first:String,
    val last:String
)

data class UserDateOfBirth(
    val date:String,
    val age:Int
)

data class UserPicture(
    val large:String,
    val medium:String,
    val thumbnail:String
)
