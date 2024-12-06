package com.health.glucoguide.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserHistoriesResponse(

	@field:SerializedName("histories")
	val histories: List<HistoriesItem>,

	@field:SerializedName("error")
	val error: Boolean? = null
)

data class HistoriesItem(

	@field:SerializedName("diagnosa")
	val diagnosa: String? = null,

	@field:SerializedName("tanggal_cek")
	val tanggalCek: String? = null,

	@field:SerializedName("keluhan")
	val keluhan: String? = null
)
