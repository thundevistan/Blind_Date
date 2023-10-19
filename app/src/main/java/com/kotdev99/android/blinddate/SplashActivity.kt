package com.kotdev99.android.blinddate

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.kotdev99.android.blinddate.auth.IntroActivity

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
		handler.postDelayed({
			val intent = IntroActivity.newIntent(this)
			startActivity(intent)
			finish()
		}, 3000)
	}
}