package com.kotdev99.android.blinddate.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
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

	private val listAdapter by lazy {
		LikeListAdapter(onItemClick = { position, item ->
			onItemClicked(position, item)
		})
	}

	// item 클릭 시 매칭 상태인지 판별
	private fun onItemClicked(position: Int, item: UserInfoModel) {
		val keyList = mutableListOf<String>()

		FirebaseRef.userLikeRef.child(likeUserList[position].uid.toString())
			.addValueEventListener(object : ValueEventListener {
				override fun onDataChange(snapshot: DataSnapshot) {

					if (!snapshot.hasChildren()) {
						showToast("매칭 상태가 아닙니다")
					} else {
						for (likeUserUid in snapshot.children) {

							keyList.add(likeUserUid.key.toString())
						}

						if (keyList.contains(uid)) {
							showToast("매칭이 되었습니다!")
						} else {
							showToast("매칭 상태가 아닙니다")
						}
					}
				}

				override fun onCancelled(error: DatabaseError) {
					showToast(error.message)
				}

			})


		Log.d(TAG, likeUserList[position].uid.toString())
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		initView()
		getMyLikeList()
		getMyLikedUserInfo()
	}

	private fun initView() = with(binding) {
		rvLikeContainer.apply {
			this.adapter = listAdapter
			addItemDecoration(DividerItemDecoration(this@MyLikeListActivity, LinearLayout.VERTICAL))
		}
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

				listAdapter.submitList(likeUserList)
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