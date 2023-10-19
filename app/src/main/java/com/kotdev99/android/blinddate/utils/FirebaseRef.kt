package com.kotdev99.android.blinddate.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kotdev99.android.blinddate.BuildConfig

class FirebaseRef {

	companion object {
		private val database = Firebase.database(BuildConfig.FIREBASE_DATABASE_URL)

		val userInfoRef = database.getReference("userInfo")
	}
}