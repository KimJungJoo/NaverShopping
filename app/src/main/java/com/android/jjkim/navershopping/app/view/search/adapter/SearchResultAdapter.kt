package com.android.jjkim.navershopping.app.view.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.jjkim.navershopping.databinding.LayoutSearchResultItemBinding
import com.android.jjkim.navershopping.app.service.model.search.ResponseShopSearchItem
import com.bumptech.glide.Glide

class SearchResultAdapter(private val context: Context):
    RecyclerView.Adapter<SearchResultAdapter.SearchItemHolder>() {

    var itemList = mutableListOf<ResponseShopSearchItem>()

    fun setItems(items: List<ResponseShopSearchItem>) {
        this.itemList = items.toMutableList();
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemHolder {
        val binding = LayoutSearchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchItemHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchItemHolder, position: Int) {
        holder.bind(itemList[position])
    }

    class SearchItemHolder(val binding: LayoutSearchResultItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: ResponseShopSearchItem) {
            binding.item = currentItem
        }
    }
}

