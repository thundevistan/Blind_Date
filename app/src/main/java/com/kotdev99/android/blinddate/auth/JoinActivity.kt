package com.kotdev99.android.blinddate.auth

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kotdev99.android.blinddate.MainActivity
import com.kotdev99.android.blinddate.data.UserInfoModel
import com.kotdev99.android.blinddate.databinding.ActivityJoinBinding
import com.kotdev99.android.blinddate.utils.FirebaseRef
import com.kotdev99.android.blinddate.utils.showToast
import java.io.ByteArrayOutputStream

class JoinActivity : AppCompatActivity() {

	private val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }
	private lateinit var auth: FirebaseAuth

	// 이미지 Uri 획득
	private val getImage = registerForActivityResult(   // onCreate or onStart 에서 초기화 되지 않으면 에러 발생
		ActivityResultContracts.GetContent(),           // 때문에 전역 변수로 선언 해주자
		ActivityResultCallback { uri ->
			binding.ivProfile.setImageURI(uri)
		}
	)

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
			val nickname = edtNickname.text.toString()
			val gender = edtGender.text.toString()
			val area = edtArea.text.toString()
			val age = edtAge.text.toString()

			signUp(nickname, gender, area, age)
		}

		ivProfile.setOnClickListener {

			// Profile 에 이미지 삽입
			getImage.launch("image/*")
		}
	}

	private fun signUp(nickname: String, gender: String, area: String, age: String) {
		val email = binding.edtEmail.text.toString()
		val password = binding.edtPwd.text.toString()

		auth.createUserWithEmailAndPassword(email, password)
			.addOnCompleteListener(this) { task ->
				if (task.isSuccessful) {
					// Sign in success, update UI with the signed-in user's information

					val user = auth.currentUser
					val uid = user?.uid.toString()

					val userInfoModel = UserInfoModel(
						uid = uid,
						nickname = nickname,
						gender = gender,
						area = area,
						age = age
					)

					// Write a message to the database
					FirebaseRef.userInfoRef.child(uid).setValue(userInfoModel)

					uploadImage(uid)

					showToast("회원 가입 성공")

					val intent = MainActivity.newIntent(this)
					startActivity(intent)
				} else {
					// If sign in fails, display a message to the user.
					showToast("회원 가입 실패")
				}
			}
	}

	private fun uploadImage(uid: String) = with(binding) {

		// Get the data from an ImageView as bytes
		ivProfile.isDrawingCacheEnabled = true
		ivProfile.buildDrawingCache()
		val bitmap = (ivProfile.drawable as BitmapDrawable).bitmap
		val baos = ByteArrayOutputStream()
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
		val data = baos.toByteArray()

		val uploadTask = FirebaseRef.storageRef(uid).putBytes(data)
		uploadTask.addOnFailureListener {
			// Handle unsuccessful uploads
		}.addOnSuccessListener {
			// taskSnapshot.metadata contains file metadata such as size, content-type, etc.
			// ...
		}
	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, JoinActivity::class.java)
		}
	}
}