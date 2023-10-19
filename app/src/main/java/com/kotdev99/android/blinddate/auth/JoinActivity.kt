package com.kotdev99.android.blinddate.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kotdev99.android.blinddate.MainActivity
import com.kotdev99.android.blinddate.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

	private val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }
	private lateinit var auth: FirebaseAuth

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		// Initialize Firebase Auth
		auth = Firebase.auth

		initView()
	}

	public override fun onStart() {
		super.onStart()
		// Check if user is signed in (non-null) and update UI accordingly.
		val currentUser = auth.currentUser
		if (currentUser != null) {
//			reload()
		}
	}

	private fun initView() = with(binding) {
		btnSignUp.setOnClickListener {
			signUp()
		}
	}

	private fun signUp() {
		val email = binding.edtEmail.text.toString()
		val password = binding.edtPwd.text.toString()

		auth.createUserWithEmailAndPassword(email, password)
			.addOnCompleteListener(this) { task ->
				if (task.isSuccessful) {
					// Sign in success, update UI with the signed-in user's information

					val user = auth.currentUser
//					updateUI(user)

					val intent = MainActivity.newIntent(this)
					startActivity(intent)
				} else {
					// If sign in fails, display a message to the user.

//					updateUI(null)
				}
			}
	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, JoinActivity::class.java)
		}
	}
}