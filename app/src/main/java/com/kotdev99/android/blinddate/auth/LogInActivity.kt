package com.kotdev99.android.blinddate.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kotdev99.android.blinddate.MainActivity
import com.kotdev99.android.blinddate.databinding.ActivityLogInBinding
import com.kotdev99.android.blinddate.utils.showToast

class LogInActivity : AppCompatActivity() {

	private val binding by lazy { ActivityLogInBinding.inflate(layoutInflater) }
	private lateinit var auth: FirebaseAuth

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		auth = Firebase.auth

		initView()
	}

	private fun initView() = with(binding) {
		btnLogIn.setOnClickListener {
			logIn()
		}
	}

	private fun logIn() {
		val email = binding.edtEmail.text.toString()
		val password = binding.edtPwd.text.toString()

		auth.signInWithEmailAndPassword(email, password)
			.addOnCompleteListener(this) { task ->
				if (task.isSuccessful) {
					// Sign in success, update UI with the signed-in user's information

					val intent = MainActivity.newIntent(this)
					startActivity(intent)
//					updateUI(user)
				} else {
					// If sign in fails, display a message to the user.

					showToast("로그인 실패")
//					updateUI(null)
				}
			}
	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, LogInActivity::class.java)
		}
	}
}