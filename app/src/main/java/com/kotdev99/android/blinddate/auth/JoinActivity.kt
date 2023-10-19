package com.kotdev99.android.blinddate.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotdev99.android.blinddate.R

class JoinActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_join)
	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, JoinActivity::class.java)
		}
	}
}