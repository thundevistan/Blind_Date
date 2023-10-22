package com.kotdev99.android.blinddate.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotdev99.android.blinddate.databinding.ActivityMyLikeListBinding

class MyLikeListActivity : AppCompatActivity() {

	private val binding by lazy { ActivityMyLikeListBinding.inflate(layoutInflater) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
	}

	companion object {
		fun newIntent(context: Context) : Intent {
			return Intent(context, MyLikeListActivity::class.java)
		}
	}
}