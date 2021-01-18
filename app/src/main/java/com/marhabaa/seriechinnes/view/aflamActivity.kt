package com.marhabaa.seriechinnes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.GsonBuilder
import com.marhabaa.seriechinnes.R
import com.marhabaa.seriechinnes.base.basic
import com.marhabaa.seriechinnes.base.posts
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class aflamActivity : AppCompatActivity() {
    var client = OkHttpClient();
    val adapter = GroupAdapter<ViewHolder>()
    var shm: ShimmerFrameLayout? = null
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aflam)
        var random = intent.extras?.getString("random")
        loaddata(random.toString())

    }

    private fun loaddata(random: String?) {

        shm = findViewById<View>(R.id.shimmer1) as ShimmerFrameLayout
        var recyclerView = findViewById(R.id.aflams) as RecyclerView
        recyclerView.adapter = adapter
        var req = Request
            .Builder()
            .url(basic().serieonce()+random)
            .build()
        client.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                swiprefresh.isRefreshing = false
                runOnUiThread {
                    shm!!.stopShimmer()
                    errorConne.visibility = View.VISIBLE
                    shimmerinc.visibility = View.GONE
                    var trythis = findViewById<View>(R.id.tryinternet) as Button

                    errorConne.setOnClickListener {
                        shm!!.startShimmer()
                        shimmerinc.visibility = View.VISIBLE
                        errorConne.visibility = View.GONE
                        loaddata(random)
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                var respo = response.body!!.string()
                var gson = GsonBuilder().create()
                var data =
                    gson.fromJson(respo, Array<basic.alldata>::class.java) as Array<basic.alldata>
                    if (data == null){
                        Toast.makeText(this@aflamActivity,"no data", Toast.LENGTH_LONG).show()
                    }


                    runOnUiThread {

                        for (fetch in data) {
                            shm!!.startShimmer()
                            shimmerinc.visibility = View.GONE
                            adapter.add(posts.Adapter(fetch, 0,this@aflamActivity))
                        }
                    }
                }catch (e:Exception){
                    runOnUiThread {
                        shm!!.stopShimmer()
                        errorConne.visibility = View.VISIBLE
                        shimmerinc.visibility = View.GONE
                        var trythis = findViewById<View>(R.id.tryinternet) as Button
                        var tesxt = findViewById<View>(R.id.textView222) as TextView
                        trythis.visibility=View.GONE
                        tesxt.text="عدراً لا يوجد أي فيديوهات  الأن لهدا المسلسل"

                    } }

            }


        })
    }
}


