package com.kotdev99.android.blinddate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kotdev99.android.blinddate.databinding.ActivityMainBinding
import com.kotdev99.android.blinddate.slider.CardStackAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

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

		// 더미 데이터
		val testList = mutableListOf<String>()
		testList.apply {
			add("a")
			add("b")
			add("c")
		}

		cardStackAdapter = CardStackAdapter(this, testList)
		binding.cardStackView.apply {
			layoutManager = manager
			adapter = cardStackAdapter
		}
	}

	companion object {
		fun newIntent(context: Context): Intent {
			return Intent(context, MainActivity::class.java)
		}
	}
}