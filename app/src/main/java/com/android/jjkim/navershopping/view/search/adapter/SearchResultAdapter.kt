package com.android.jjkim.navershopping.view.search.adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.databinding.LayoutSearchResultItemBinding
import com.android.jjkim.navershopping.service.model.search.ResponseShopSearchItem
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

//    @BindingAdapter("image")
//    fun loadItemImage(view: ImageView, imageUrl: String?) {
//        Glide.with(view.context)
//            .load(imageUrl)
//            .into(view)
//    }

    class SearchItemHolder(val binding: LayoutSearchResultItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: ResponseShopSearchItem) {
            binding.item = currentItem
        }
    }


}