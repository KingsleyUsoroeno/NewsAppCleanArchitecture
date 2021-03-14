package com.techkingsley.newsappcleanarchitecture.framework.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techkingsley.newsappcleanarchitecture.databinding.LayoutNewsTagBinding
import java.util.*


class NewsTagRecyclerAdapter(
    private val items: MutableList<String>,
    private val onItemClickedListener: (tag: String) -> Unit
) :
    RecyclerView.Adapter<NewsTagRecyclerAdapter.NewsTagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsTagViewHolder {
        val viewBinding = LayoutNewsTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsTagViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: NewsTagViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    inner class NewsTagViewHolder(private val viewBinding: LayoutNewsTagBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(item: String) {
            with(viewBinding) {
                tag = item
                val rnd = Random()
                val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                root.background.setTint(color)
                root.setOnClickListener { onItemClickedListener(item) }

                executePendingBindings()
            }
        }
    }
}