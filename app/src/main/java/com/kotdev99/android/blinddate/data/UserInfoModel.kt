package com.kotdev99.android.blinddate.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserInfoModel(
	val uid: String? = null,
	val nickname: String? = null,
	val gender: String? = null,
	val area: String? = null,
	val age: String? = null
)