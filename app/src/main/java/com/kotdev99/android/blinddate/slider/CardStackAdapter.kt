package com.kotdev99.android.blinddate.slider

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotdev99.android.blinddate.databinding.ItemCardBinding

class CardStackAdapter(val context: Context, val items: List<String>) :
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

	inner class ViewHolder(binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
		fun binding(data: String) {
			// TODO:    추후 작성 예정
		}
	}
}