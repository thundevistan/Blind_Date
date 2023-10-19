package com.kotdev99.android.blinddate.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kotdev99.android.blinddate.databinding.ActivityJoinBinding
import com.kotdev99.android.blinddate.utils.FirebaseRef
import com.kotdev99.android.blinddate.utils.showToast

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
			nickname = edtNickname.text.toString()
			gender = edtGender.text.toString()
			area = edtArea.text.toString()
			age = edtAge.text.toString()

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
					uid = user?.uid.toString()

					// Write a message to the database
					FirebaseRef.userInfoRef.child(uid!!).setValue("Hello, World!")

//					val intent = MainActivity.newIntent(this)
//					startActivity(intent)
				} else {
					// If sign in fails, display a message to the user.
					showToast("다시 시도해 주세요")
				}
			}
	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, JoinActivity::class.java)
		}
	}
}