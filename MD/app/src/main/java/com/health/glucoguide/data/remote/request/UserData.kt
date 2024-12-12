package com.health.glucoguide.data.remote.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    var name : String? = null,
    var age: Int? = null,
    var hypertension: String? = null,
    var heart_disease: String? = null,
    var bmi: Float? = null,
    var HbA1c_level: Float? = null,
    var blood_glucose_level: Int? = null,
    var gender_encoded: String? = null,
    var smoking_history_encoded: String? = null
) : Parcelable