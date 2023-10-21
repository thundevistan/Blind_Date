package com.kotdev99.android.blinddate

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kotdev99.android.blinddate.data.UserInfoModel
import com.kotdev99.android.blinddate.databinding.ActivityMainBinding
import com.kotdev99.android.blinddate.setting.SettingActivity
import com.kotdev99.android.blinddate.slider.CardStackAdapter
import com.kotdev99.android.blinddate.utils.FirebaseAuthUtils
import com.kotdev99.android.blinddate.utils.FirebaseRef
import com.kotdev99.android.blinddate.utils.showToast
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction


class MainActivity : AppCompatActivity() {

	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
	private val userInfoList = mutableListOf<UserInfoModel>()
	private val uid by lazy { FirebaseAuthUtils.getUid() }
	private lateinit var currentUserGender: String
	private var userCount = 0

	// CardStackView 변수
	private lateinit var cardStackAdapter: CardStackAdapter
	private lateinit var manager: CardStackLayoutManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		initView()
		initCardStackView()
	}

	private fun initView() = with(binding) {

		ivProfile.setOnClickListener {
			val intent = SettingActivity.newIntent(this@MainActivity)
			startActivity(intent)
		}
	}

	private fun initCardStackView() {
		getMyUserInfo()

		manager = CardStackLayoutManager(this, object : CardStackListener {
			override fun onCardDragging(direction: Direction?, ratio: Float) {

			}

			override fun onCardSwiped(direction: Direction?) {

				if (direction == Direction.Right) {
					userInfoList[userCount].uid?.let { userLikeOtherUser(uid, it) }
				}

				userCount += 1
				if (userCount == userInfoList.count()) {
					getUserInfo(currentUserGender)
				}
			}

			override fun onCardRewound() {

			}

			override fun onCardCanceled() {

			}

			override fun onCardAppeared(view: View?, position: Int) {

			}

			override fun onCardDisappeared(view: View?, position: Int) {

			}
		})

		cardStackAdapter = CardStackAdapter(this, userInfoList)
		binding.cardStackView.apply {
			layoutManager = manager
			adapter = cardStackAdapter
		}
	}

	private fun getMyUserInfo() {

		FirebaseRef.userInfoRef.child(uid).addValueEventListener(object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {

				val userInfo = snapshot.getValue(UserInfoModel::class.java)
				currentUserGender = userInfo?.gender ?: "error"

				getUserInfo(currentUserGender)
			}

			override fun onCancelled(error: DatabaseError) {
				showToast(error.message)
			}
		})
	}

	// Firebase Database 에서 값 읽기
	private fun getUserInfo(currentUserGender: String) {

		// 값 변경 시마다 onDataChange 콜백 자동 호출
		FirebaseRef.userInfoRef.addValueEventListener(object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {

				for (item in snapshot.children) {
					val userInfo = item.getValue(UserInfoModel::class.java)

					if (userInfo?.gender.equals(currentUserGender)) {

					} else {
						userInfo?.let { userInfoList.add(it) }
					}
				}
				cardStackAdapter.notifyDataSetChanged()
			}

			override fun onCancelled(databaseError: DatabaseError) {
				// Getting Post failed, log a message
			}
		})
	}

	// 좋아요 누른 유저의 UID 를 저장
	private fun userLikeOtherUser(myUid: String, otherUid: String) {
		FirebaseRef.userLikeRef.child(myUid).child(otherUid).setValue("true")

		getOtherUserLikeList(otherUid)
	}

	// 내가 종아요 누른 유저의 좋아요 리스트를 통해 서로 좋아요를 눌렀는지 비교
	private fun getOtherUserLikeList(otherUid: String) {

		FirebaseRef.userLikeRef.child(otherUid).addValueEventListener(object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {

				for (item in snapshot.children) {

					val likeUserKey = item.key
					if (likeUserKey.equals(uid)) {

						showToast("매칭 완료")
						createNotificationChannel()
						notificationBuilder()
//						requestPermission { notificationBuilder() }
					}
				}
			}

			override fun onCancelled(error: DatabaseError) {
				showToast(error.message)
			}
		})
	}

	// Notification
	private fun createNotificationChannel() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val name = "One Channel"
			val descriptionText = "one-channel"
			val importance = NotificationManager.IMPORTANCE_HIGH
			val channel = NotificationChannel("test", name, importance).apply {
				description = descriptionText
			}

			val notificationManager: NotificationManager =
				getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			notificationManager.createNotificationChannel(channel)
		}
	}

	private fun notificationBuilder() {

		val builder = NotificationCompat.Builder(this, "test")
			.setSmallIcon(R.drawable.ic_launcher_background)
			.setContentTitle("textTitle")
			.setContentText("매칭이 되었습니다!")
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)

		with(NotificationManagerCompat.from(this)) {
			notify(1, builder.build())
			Log.d(TAG, "notification")
		}
	}

//	private fun requestPermission(logic: () -> Unit) {
//		TedPermission.create()
//			.setPermissionListener(object : PermissionListener {
//				override fun onPermissionGranted() {
//					logic()
//				}
//
//				override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
//					showToast("권한 승인 필요")
//				}
//			})
//			.setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//			.setPermissions(Manifest.permission.POST_NOTIFICATIONS)
//			.check()
//	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, MainActivity::class.java)
		}

		private val TAG = MainActivity::class.simpleName
	}
}