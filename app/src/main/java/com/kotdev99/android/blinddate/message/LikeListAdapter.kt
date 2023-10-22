package com.kotdev99.android.blinddate.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotdev99.android.blinddate.data.UserInfoModel
import com.kotdev99.android.blinddate.databinding.ItemLikeListBinding

class LikeListAdapter : ListAdapter<UserInfoModel, LikeListAdapter.ViewHolder>(object :
	DiffUtil.ItemCallback<UserInfoModel>() {
	override fun areItemsTheSame(oldItem: UserInfoModel, newItem: UserInfoModel): Boolean {
		return oldItem.uid == newItem.uid
	}

	override fun areContentsTheSame(oldItem: UserInfoModel, newItem: UserInfoModel): Boolean {
		return oldItem == newItem
	}
}) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeListAdapter.ViewHolder {
		val view = ItemLikeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: LikeListAdapter.ViewHolder, position: Int) {
		holder.onBind(getItem(position))
	}

	inner class ViewHolder(private val binding: ItemLikeListBinding) :
		RecyclerView.ViewHolder(binding.root) {

			fun onBind(item: UserInfoModel) = with(binding) {
				tvName.text= item.nickname ?: "null"
			}
	}
}