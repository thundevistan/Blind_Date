package com.kotdev99.android.blinddate.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotdev99.android.blinddate.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

	private val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		initView()
	}

	private fun initView() = with(binding) {
		btnJoin.setOnClickListener {
			val intent = JoinActivity.newIntent(this@IntroActivity)
			startActivity(intent)
		}
	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, IntroActivity::class.java)
		}
	}
}