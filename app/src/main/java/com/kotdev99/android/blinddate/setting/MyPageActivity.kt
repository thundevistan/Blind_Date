package com.kotdev99.android.blinddate.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kotdev99.android.blinddate.data.UserInfoModel
import com.kotdev99.android.blinddate.databinding.ActivityMyPageBinding
import com.kotdev99.android.blinddate.utils.FirebaseAuthUtils
import com.kotdev99.android.blinddate.utils.FirebaseRef

class MyPageActivity : AppCompatActivity() {

	private val binding by lazy { ActivityMyPageBinding.inflate(layoutInflater) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		initView()

	}

	private fun initView() = with(binding) {
		getMyData()
	}

	private fun getMyData() {

		FirebaseRef.userInfoRef.addValueEventListener(object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {

				val uid = FirebaseAuthUtils.getUid()
				val userInfo = snapshot.child(uid).getValue(UserInfoModel::class.java)

				if (userInfo != null) {
					binding.tvUid.text = userInfo.uid
					binding.tvNickname.text = userInfo.nickname
					binding.tvAge.text = userInfo.age
					binding.tvArea.text = userInfo.area
					binding.tvGender.text = userInfo.gender
				}

				FirebaseRef.storageRef(uid).downloadUrl.addOnCompleteListener { task ->
					if (task.isSuccessful) {
						Glide.with(this@MyPageActivity)
							.load(task.result)
							.into(binding.ivProfile)
					}
				}
			}

			override fun onCancelled(error: DatabaseError) {
				Log.d(TAG, error.message)
			}
		})
	}


	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, MyPageActivity::class.java)
		}

		private val TAG = MyPageActivity::class.java.simpleName
	}
}