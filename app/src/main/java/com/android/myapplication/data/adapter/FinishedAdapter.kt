package com.android.myapplication.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.data.response.ListEvent
import com.android.myapplication.databinding.ItemFinishedBinding
import com.android.myapplication.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class FinishedAdapter:ListAdapter<ListEvent, FinishedAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding:ItemFinishedBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data:ListEvent) {
            binding.apply {
                tvEventName.text = data.name
                tvOwner.text = data.ownerName

                Glide.with(itemView.context)
                    .load(data.imageLogo)
                    .into(imgEvent)

                itemView.setOnClickListener {
                    val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.EXTRA_EVENT, data)
                    itemView.context.startActivity(intentDetail)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFinishedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ListEvent> =
            object : DiffUtil.ItemCallback<ListEvent>() {


                override fun areItemsTheSame(oldItem: ListEvent, storyItem: ListEvent): Boolean {
                    return oldItem.id == storyItem.id
                }


                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: ListEvent, storyItem: ListEvent): Boolean {
                    return oldItem == storyItem
                }
            }
    }

}