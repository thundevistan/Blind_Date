package com.kotdev99.android.blinddate.slider

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotdev99.android.blinddate.data.UserInfoModel
import com.kotdev99.android.blinddate.databinding.ItemCardBinding
import com.kotdev99.android.blinddate.utils.FirebaseRef

class CardStackAdapter(val context: Context, private val items: List<UserInfoModel>) :
	RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {
		val view = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
		holder.binding(items[position])
	}

	override fun getItemCount(): Int {
		return items.size
	}

	inner class ViewHolder(private val binding: ItemCardBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun binding(item: UserInfoModel) = with(binding) {
			tvName.text = item.nickname
			tvAge.text = item.age
			tvCity.text = item.area

			// 사용자 UID 로 저장된 이미지를 읽어 CardStackView 에 삽입
			item.uid?.let {
				FirebaseRef.storageRef(it).downloadUrl.addOnCompleteListener { task ->
					if (task.isSuccessful) {
						Glide.with(binding.root)
							.load(task.result)
							.into(ivCardStackImage)
					}
				}
			}
		}
	}
}