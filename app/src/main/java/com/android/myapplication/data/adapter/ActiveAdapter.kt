package com.android.myapplication.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.data.local.EventEntity
import com.android.myapplication.databinding.ItemActiveBinding
import com.android.myapplication.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class ActiveAdapter:ListAdapter<EventEntity, ActiveAdapter.ViewHolder>(DIFF_CALLBACK){

    class ViewHolder(private val binding: ItemActiveBinding):
        RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: EventEntity) {
            binding.apply {

                Glide.with(itemView.context)
                    .load(data.imageLogo)
                    .centerCrop()
                    .into(imgEvent)
                tvEventName.text = data.name

                itemView.setOnClickListener {
                    val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.EXTRA_EVENT, data)
                    itemView.context.startActivity(intentDetail)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemActiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<EventEntity> =
            object : DiffUtil.ItemCallback<EventEntity>() {


                override fun areItemsTheSame(oldItem: EventEntity, storyItem: EventEntity): Boolean {
                    return oldItem.name == storyItem.name
                }


                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: EventEntity, storyItem: EventEntity): Boolean {
                    return oldItem == storyItem
                }
            }
    }

}