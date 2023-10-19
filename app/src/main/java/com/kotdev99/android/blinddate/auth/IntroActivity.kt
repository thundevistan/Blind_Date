package com.kotdev99.android.blinddate.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotdev99.android.blinddate.R

class IntroActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_intro)
	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, IntroActivity::class.java)
		}
	}
}