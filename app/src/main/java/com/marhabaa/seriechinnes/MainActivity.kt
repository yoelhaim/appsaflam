package com.marhabaa.seriechinnes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.GsonBuilder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pagepost.view.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var client= OkHttpClient();
    val adapter = GroupAdapter<ViewHolder>()
    private var url:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().subscribeToTopic("aflam")
        val time = object : CountDownTimer(3000, 1000) {

            override fun onFinish() {

                val intent = Intent(this@MainActivity, homeActivity::class.java)
                startActivity(intent)
                finish()


            }

            override fun onTick(p0: Long) {}
        }
        time.start()
    }
}