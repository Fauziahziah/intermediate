package com.example.mysubmission_intermediate.Api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class StroriesResponse (

     @field:SerializedName("listStory")
     val storyResponseItems: List<StoryResponseItem>,

     @field:SerializedName("error")
     val error: Boolean,

     @field:SerializedName("message")
     val message: String
 )

@Parcelize
data class StoryResponseItem(
     @field:SerializedName("photoUrl")
     val photoUrl: String,

     @field:SerializedName("name")
     val name: String,

     @field:SerializedName("description")
     val description: String,

     @field:SerializedName("lon")
     val lon: Double?,

     @field:SerializedName("id")
     val id: String,

     @field:SerializedName("lat")
     val lat:Double?
) : Parcelable