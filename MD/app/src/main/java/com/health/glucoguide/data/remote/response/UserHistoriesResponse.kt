package com.health.glucoguide.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserHistoriesResponse(

	@field:SerializedName("histories")
	val histories: List<HistoriesItem>,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class HistoriesItem(

	@field:SerializedName("check_result")
	val checkResult: String? = null,

	@field:SerializedName("check_date")
	val checkDate: String? = null,

	@field:SerializedName("complaint_disease")
	val complaintDisease: String? = null
)
