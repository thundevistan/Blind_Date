package com.kotdev99.android.blinddate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.kotdev99.android.blinddate.data.UserInfoModel
import com.kotdev99.android.blinddate.databinding.ActivityMainBinding
import com.kotdev99.android.blinddate.setting.SettingActivity
import com.kotdev99.android.blinddate.slider.CardStackAdapter
import com.kotdev99.android.blinddate.utils.FirebaseRef
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

const val TAG = "Main"

class MainActivity : AppCompatActivity() {

	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
	private val userInfoList = mutableListOf<UserInfoModel>()

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
			val auth = Firebase.auth
			auth.signOut()

			val intent = SettingActivity.newIntent(this@MainActivity)
			startActivity(intent)
		}
	}

	private fun initCardStackView() {

		manager = CardStackLayoutManager(this, object : CardStackListener {
			override fun onCardDragging(direction: Direction?, ratio: Float) {

			}

			override fun onCardSwiped(direction: Direction?) {

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

		getUserInfo()
	}

	// Firebase Database 에서 값 읽기
	private fun getUserInfo() {

		// 값 변경 시마다 onDataChange 콜백 자동 호출
		FirebaseRef.userInfoRef.addValueEventListener(object : ValueEventListener {
			override fun onDataChange(dataSnapshot: DataSnapshot) {
				// Get Post object and use the values to update the UI

				for (item in dataSnapshot.children) {
					val userInfo = item.getValue(UserInfoModel::class.java)
					userInfo?.let { userInfoList.add(it) }
				}
				cardStackAdapter.notifyDataSetChanged()
			}

			override fun onCancelled(databaseError: DatabaseError) {
				// Getting Post failed, log a message

			}
		})
	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, MainActivity::class.java)
		}
	}
}