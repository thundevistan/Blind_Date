package com.kotdev99.android.blinddate.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotdev99.android.blinddate.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

	private val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		initView()
	}

	private fun initView() = with(binding) {
		myPageBtn.setOnClickListener {
			val intent = MyPageActivity.newIntent(this@SettingActivity)
			startActivity(intent)
		}
	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, SettingActivity::class.java)
		}
	}
}