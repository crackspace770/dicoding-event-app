package com.android.myapplication.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.myapplication.R
import com.android.myapplication.data.factory.ViewModelFactory
import com.android.myapplication.data.local.EventEntity
import com.android.myapplication.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val event = intent.getParcelableExtra<EventEntity>(EXTRA_EVENT)

        binding.apply {
            event?.let {
                val kuota = event.quota
                val registrant = event.registrants

                val kuotaSisa = kuota?.minus(registrant!!)

                Glide.with(this@DetailActivity)
                    .load(event.mediaCover)
                    .into(imgArticle)
                tvJudulEvent.text = event.name
                tvOwnerDetail?.text = event.ownerName?.let { it1 -> createBoldText("By: ", it1) }
                tvSisaKuota?.text = createBoldText("Sisa kuota: ", "${kuotaSisa.toString()} peserta")
                tvTimeBegin?.text = event.beginTime?.let { it1 -> createBoldText("Mulai: ", it1) }


                // Configure WebView settings
                webViewDesc?.settings?.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                }

                // Load HTML content into the WebView
                webViewDesc?.webViewClient = WebViewClient()
                webViewDesc?.loadData(event.description ?: "", "text/html", "UTF-8")

                btnToRegister?.setOnClickListener {
                    val url = event.link
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }

            }

            if (event != null) {
                viewModel.setEventData(event)
            }

            binding.myToolbar.btnBookmark.setOnClickListener {
                if (event != null) {
                    viewModel.changeBookmark(event)
                }
            }

            viewModel.bookmarkStatus.observe(this@DetailActivity) {status->
                setBookmarkState(status)
            }

            myToolbar.btnBackToolbar.setOnClickListener {
                finish()
            }

        }

    }

    private fun setBookmarkState(state: Boolean) {
        val bookmarkItem = binding.myToolbar.btnBookmark
        if (state) {
            bookmarkItem.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_bookmark_24))
        } else {
            bookmarkItem.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_bookmark_border))
        }
    }


    private fun createBoldText(prefix: String, boldText: String): SpannableStringBuilder {
        return SpannableStringBuilder().apply {
            append(prefix)
            val start = length
            append(boldText)
            setSpan(StyleSpan(Typeface.BOLD), start, length, 0)
        }
    }

    companion object {
        const val EXTRA_EVENT = "extra_article"
    }
}