package com.marhabaa.seriechinnes.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.ads.*
import com.google.gson.GsonBuilder
import com.marhabaa.seriechinnes.R
import com.marhabaa.seriechinnes.base.basic
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.pagepost.view.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import okhttp3.Callback as Callback1

class postActivity : AppCompatActivity() {
    var client= OkHttpClient();
    val adapter = GroupAdapter<ViewHolder>()
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        var title = intent.extras?.get("title").toString()
        var urlvideo = intent.extras?.get("urlvideo").toString()
        var desc = intent.extras?.get("desc").toString()
        var type = intent.extras?.get("type").toString()
        var stt = intent.extras?.get("stt").toString()
        var film = intent.extras?.get("film").toString()
        var views = intent.extras?.get("views").toString()

         descMosalsal.text= desc
        Glide.with(this).load("https://i.ytimg.com/vi/"+ urlvideo +"/maxresdefault.jpg").into(imgLog);

        setSupportActionBar(toolbarpost)
        supportActionBar?.title=title
        goPlayer.setOnClickListener{
            val  intent =Intent(this,playerActivity::class.java)
            intent.putExtra("urlvideo",urlvideo)
            startActivity(intent)
        }
        if(stt== "1"){
            goPlayer.visibility=View.GONE
        }


                var req= Request
            .Builder()
            .url(basic().update()+urlvideo)
            .build()
        client.newCall(req).enqueue(responseCallback = object : Callback1 {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                var res = response.body!!.string()
                var gg = GsonBuilder().create()
                var data = gg.fromJson(res,upg::class.java)
              runOnUiThread{
//                  Toast.makeText(this@postActivity, data.message, Toast.LENGTH_LONG).show()
              }
            }

        })
        ///ads inster
        MobileAds.initialize(this) {}
        val mNativeExpressAdView: NativeExpressAdView = findViewById(R.id.adView2)
        val requests = AdRequest.Builder().build()
        mNativeExpressAdView.loadAd(requests)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = basic().inst
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                showInterstitialAd()
            }
        }
    }
    data class  upg(var message:String)
    fun showInterstitialAd() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show();
        }
    }
}