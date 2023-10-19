package com.kotdev99.android.blinddate

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kotdev99.android.blinddate.auth.IntroActivity
import com.kotdev99.android.blinddate.utils.FirebaseAuthUtils

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

	private lateinit var handler: Handler

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)

		initSplashScreen()
	}

	private fun initSplashScreen() {

		handler = Handler(mainLooper)

		val uid = FirebaseAuthUtils.getUid()
		if (uid != "null") {
			handler.postDelayed({
				val intent = MainActivity.newIntent(this)
				startActivity(intent)
				finish()
			}, 2000)
		} else {
			handler.postDelayed({
				val intent = IntroActivity.newIntent(this)
				startActivity(intent)
				finish()
			}, 2000)
		}

		Log.d(TAG, uid.toString())
	}

	companion object {
		private val TAG = SplashActivity::class.java.simpleName
	}
}