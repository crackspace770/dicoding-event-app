package com.android.myapplication.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.myapplication.data.response.ListEvent
import com.android.myapplication.databinding.ActivityDetailBinding

class DetailActivity:AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val event = intent.getParcelableExtra<ListEvent>(EXTRA_EVENT)

        binding.apply {
            event?.let {

            }
        }

    }


    companion object {
        const val EXTRA_EVENT = "extra_article"
    }

}