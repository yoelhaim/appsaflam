package com.marhabaa.seriechinnes.view

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.gms.ads.*
import com.marhabaa.seriechinnes.R
import com.marhabaa.seriechinnes.base.basic
import kotlinx.android.synthetic.main.activity_web.*

class webActivity : AppCompatActivity() {
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val url = intent.extras?.get("link").toString()
        loadUrlLink(url)
    }

    private fun loadUrlLink(link: String) {
//Toast.makeText(this,link,Toast.LENGTH_LONG).show()

        WebViews.loadUrl(link )

        WebViews.getSettings().setJavaScriptEnabled(true);



        WebViews!!.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                refresh.isRefreshing = false
                //Toast.makeText(this@webViewActivity,url,Toast.LENGTH_LONG).show()
                refresh.setOnRefreshListener {
                    loadUrlLink(url.toString())
                    refresh.isRefreshing = true
                }
                if (url == "success") {
                    val time = object : CountDownTimer(3000, 1000) {

                        override fun onFinish() {
//                            val intent= Intent(this@webViewActivity,MainActivity::class.java)
//                            startActivity(intent)
//                            finish()
                        }

                        override fun onTick(p0: Long) {}
                    }
                    time.start()
                }
                progressBarweb.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBarweb.visibility = View.GONE
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
            ) {
                // WebViews.loadUrl("file:///android_asset/error.html")
                // Toast.makeText(this@webViewActivity,"error internet",Toast.LENGTH_LONG).show()
            }
        }
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
    fun showInterstitialAd() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show();
        }
    }

    override fun onBackPressed() {
        if (WebViews!!.canGoBack()) {
            WebViews.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
