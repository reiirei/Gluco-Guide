package com.health.glucoguide.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    var gender: String? = null,
    var age: Int? = null,
    var smokingHistory: String? = null,
    var heartDisease: String? = null,
    var hypertension: String? = null,
    var bodyMassIndex: Double? = null,
    var hemoglobinLevel: Double? = null,
    var glucoseLevel: Int? = null,
    var date: String? = null
) : Parcelable