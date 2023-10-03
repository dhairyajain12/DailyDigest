package com.dailydigest.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val dName: String = "",
    val uName: String = "",
    val email: String = "",
    val mobile: String = "",
    val dob: String = "",
    val uImg: Bitmap? = null
) : Parcelable