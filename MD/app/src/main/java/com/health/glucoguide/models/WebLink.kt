package com.health.glucoguide.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WebLink(
    val title: String,
    val url: String,
    val imageUrl: String,
    val description: String
) : Parcelable