package com.kotdev99.android.blinddate.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotdev99.android.blinddate.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {

	private val binding by lazy { ActivityMyPageBinding.inflate(layoutInflater) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, MyPageActivity::class.java)
		}
	}
}