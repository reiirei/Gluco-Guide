package com.health.glucoguide.models

import com.google.gson.annotations.SerializedName

data class UserRegisterRequest(

	@field:SerializedName("name")
	var name: String,

	@field:SerializedName("email")
	var email: String,

	@field:SerializedName("password")
	var password: String,
)
