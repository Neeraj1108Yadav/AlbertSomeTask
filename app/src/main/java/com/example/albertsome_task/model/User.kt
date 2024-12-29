package com.example.albertsome_task.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

data class UserResult(
    val results: List<User>
)

@Parcelize
data class User(
    val gender:String,
    val name: UserName,
    val location: UserLocation,
    val email:String,
    val dob: UserDateOfBirth,
    val phone:String,
    val cell:String,
    val picture: UserPicture
) : Parcelable{
    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                // Compare unique IDs or reference equality
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                // Compare content equality
                return oldItem == newItem
            }

        }
    }
}

@Parcelize
data class UserName(
    val title:String,
    val first:String,
    val last:String
) : Parcelable

@Parcelize
data class UserDateOfBirth(
    val date:String,
    val age:Int
) : Parcelable

@Parcelize
data class UserPicture(
    val large:String,
    val medium:String,
    val thumbnail:String
) : Parcelable
