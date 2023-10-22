package com.kotdev99.android.blinddate.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kotdev99.android.blinddate.data.UserInfoModel
import com.kotdev99.android.blinddate.databinding.ActivityMyLikeListBinding
import com.kotdev99.android.blinddate.utils.FirebaseAuthUtils
import com.kotdev99.android.blinddate.utils.FirebaseRef
import com.kotdev99.android.blinddate.utils.showToast

class MyLikeListActivity : AppCompatActivity() {

	private val binding by lazy { ActivityMyLikeListBinding.inflate(layoutInflater) }

	private var likeUserListUid = mutableListOf<String>()
	private var likeUserList = mutableListOf<UserInfoModel>()
	private val uid = FirebaseAuthUtils.getUid()

	private val adapter by lazy { LikeListAdapter() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		initView()
		getMyLikeList()
		getMyLikedUserInfo()
	}

	private fun initView() = with(binding) {
		rvLikeContainer.adapter = adapter
	}

	private fun getMyLikeList() {

		FirebaseRef.userLikeRef.child(uid).addValueEventListener(object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {

				for (item in snapshot.children) {
					likeUserListUid.add(item.key.toString())    // 내가 좋아요 누른 유저의 UID 리스트
				}
			}

			override fun onCancelled(error: DatabaseError) {
				showToast(error.message)
			}
		})
	}

	private fun getMyLikedUserInfo() {

		FirebaseRef.userInfoRef.addValueEventListener(object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {

				for (item in snapshot.children) {

					val userInfo = item.getValue(UserInfoModel::class.java)
					if (likeUserListUid.contains(userInfo?.uid)) {
						userInfo?.let { likeUserList.add(it) }
					}
				}

				adapter.submitList(likeUserList)
			}

			override fun onCancelled(error: DatabaseError) {
				showToast(error.message)
			}
		})


	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, MyLikeListActivity::class.java)
		}

		private val TAG = MyLikeListActivity::class.simpleName
	}
}