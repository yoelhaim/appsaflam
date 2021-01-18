package com.marhabaa.seriechinnes.view

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.Toast
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.gson.GsonBuilder
import com.marhabaa.seriechinnes.R
import com.marhabaa.seriechinnes.base.basic
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_player.*
import okhttp3.*
import java.io.IOException

class playerActivity : AppCompatActivity() {
    var client= OkHttpClient();
    val adapter = GroupAdapter<ViewHolder>()
    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        var urlvideo = intent.extras?.get("urlvideo").toString()
        loadUrlLink(urlvideo,"play")
        prrff.setOnClickListener{
            loadUrlLink(urlvideo,"play")
        }


        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = basic().banner()

        mAdView = findViewById(R.id.adViewfile)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        Glide.with(this).load("https://i.ytimg.com/vi/"+ urlvideo +"/maxresdefault.jpg").into(prrff);
    }

    private fun loadUrlLink(urlvideo: String, s: String) {
        third_party_player_view.getPlayerUiController().showFullscreenButton(true)
        third_party_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                val videoId = urlvideo
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })

        val fullScreenButtonClickListener = third_party_player_view.getPlayerUiController()
            .setFullScreenButtonClickListener(View.OnClickListener {
                if (third_party_player_view.isFullScreen()) {
                    third_party_player_view.exitFullScreen()
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    // Show ActionBar
                    if (supportActionBar != null) {
                        supportActionBar!!.show()
                    }
                } else {
                    third_party_player_view.enterFullScreen()
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                    // Hide ActionBar
                    if (supportActionBar != null) {
                        supportActionBar!!.hide()
                    }
                }
            })
//        var req=Request
//            .Builder()
//            .url(basic().update())
//            .build()
//        client.newCall(req).enqueue(object :Callback{
//            override fun onFailure(call: Call, e: IOException) {
//
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                var res = response.body!!.string()
//                var gg = GsonBuilder().create()
//                var data = gg.fromJson(res,upg::class.java)
//                Toast.makeText(this@playerActivity, data.message, Toast.LENGTH_LONG).show()
//            }
//
//        })
    }
    data class  upg(var message:String)
    }


