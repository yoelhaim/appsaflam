package com.marhabaa.seriechinnes.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.GsonBuilder
import com.marhabaa.seriechinnes.R
import com.marhabaa.seriechinnes.base.basic
import com.marhabaa.seriechinnes.base.posts
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_serie.*
import kotlinx.android.synthetic.main.fragment_trend.*
import kotlinx.android.synthetic.main.fragment_trend.errorConne
import kotlinx.android.synthetic.main.fragment_trend.shimmerinc
import kotlinx.android.synthetic.main.fragment_trend.swiprefresh
import kotlinx.android.synthetic.main.pagepost.view.*
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class trendFragment : Fragment() {
    var client= OkHttpClient();
    val adapter = GroupAdapter<ViewHolder>()
    private var url:String?=null
    var shm:ShimmerFrameLayout?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trend, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        swiprefresh.setOnRefreshListener{
            shm!!.startShimmer()
            shimmerinc.visibility=View.VISIBLE
            adapter.clear()
            loaddatas()
        }
        loaddatas()

    }

    private fun loaddatas() {
        swiprefresh.isRefreshing= true
        shm= activity!!.findViewById<View>(R.id.shimmer1) as ShimmerFrameLayout

        var req = Request
            .Builder()
            .url(basic().tend())
            .build()
        client.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity!!.runOnUiThread {
                    swiprefresh.isRefreshing= false
                    shm!!.stopShimmer()
                    errorConne.visibility=View.VISIBLE
                    shimmerinc.visibility=View.GONE
                    var trythis3= activity?.findViewById<View>(R.id.tryinternet) as Button

                    trythis3.setOnClickListener {
                        shm!!.startShimmer()
                        shimmerinc.visibility=View.VISIBLE
                        errorConne.visibility=View.GONE
                        loaddatas()
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                var respo=response.body!!.string()
                var gson = GsonBuilder().create()
                var data = gson.fromJson(respo,Array<basic.alldata>::class.java) as Array<basic.alldata>
                    if (data == null){
                        Toast.makeText(activity,"no data", Toast.LENGTH_LONG).show()
                    }
                activity!!.runOnUiThread {
                    swiprefresh.isRefreshing= false
                    var ii= 1;
                    for (fetch in data){
                       adapter.clear()
                        activity!!.rvtrend.adapter=adapter
                        shm!!.startShimmer()
                        shimmerinc.visibility=View.GONE
                        adapter.add(posts.Adapter(fetch,ii,activity!!))
                        ii++
                    }
                }
                }catch (e: Exception){
                    activity!!.runOnUiThread {
                        shm!!.stopShimmer()
                        errorConne.visibility = View.VISIBLE
                        shimmerinc.visibility = View.GONE
                        var trythis =  activity!!.findViewById<View>(R.id.tryinternet) as Button
                        var tesxt =  activity!!.findViewById<View>(R.id.textView222) as TextView
                        trythis.visibility=View.GONE
                        tesxt.text="عدراً لا يوجد أي فيديوهات  الأن لهدا المسلسل"

                    } }
            }

        })
    }


}