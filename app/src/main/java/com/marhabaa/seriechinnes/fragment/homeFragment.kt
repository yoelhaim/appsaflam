package com.marhabaa.seriechinnes.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.GsonBuilder
import com.marhabaa.seriechinnes.R
import com.marhabaa.seriechinnes.base.basic
import com.marhabaa.seriechinnes.base.posts
import com.marhabaa.seriechinnes.view.postActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.errorConne
import kotlinx.android.synthetic.main.fragment_home.shimmerinc
import kotlinx.android.synthetic.main.fragment_home.swiprefresh
import kotlinx.android.synthetic.main.fragment_serie.*
import kotlinx.android.synthetic.main.fragment_trend.*
import kotlinx.android.synthetic.main.pagepost.view.*
import okhttp3.*
import java.io.IOException
import java.lang.Exception


class homeFragment : Fragment() {
    var client= OkHttpClient();
    val adapter = GroupAdapter<ViewHolder>()
    var shm:ShimmerFrameLayout?=null
    private var url:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.clear()
        var recyclerView = activity!!.findViewById(R.id.rvfilm) as RecyclerView
        recyclerView.adapter=adapter

        swiprefresh.setOnRefreshListener{
      shm!!.startShimmer()
      shimmerinc.visibility=View.VISIBLE
      adapter.clear()
            loaddata()
        }
       loaddata()

    }

    private fun loaddata() {
        if (adapter == null){

            Toast.makeText(activity,"hhhhhhhh", Toast.LENGTH_LONG).show()
        }

        swiprefresh.isRefreshing= true
        shm= activity!!.findViewById<View>(R.id.shimmer1) as ShimmerFrameLayout

        var req = Request
                .Builder()
                .url(basic().film())
                .build()
        client.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                swiprefresh.isRefreshing= false
              activity!!.runOnUiThread {
                  shm!!.stopShimmer()
                  errorConne.visibility=View.VISIBLE
                  shimmerinc.visibility=View.GONE
                  var trythis= activity?.findViewById<View>(R.id.tryinternet) as Button

                  trythis.setOnClickListener {
                      shm!!.stopShimmer()
                      shimmerinc.visibility=View.VISIBLE
                      errorConne.visibility=View.GONE
                      loaddata()
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
                    for (fetch in data){
                        shm!!.stopShimmer()
                        shimmerinc.visibility=View.GONE
                        adapter.add(posts.Adapter(fetch,0,activity!!))
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

    override fun onResume() {

        //loaddata()
       // Toast.makeText(activity,"hhhhhhhh", Toast.LENGTH_LONG).show()
       // shm!!.stopShimmer()
       // shimmerinc.visibility=View.GONE
        super.onResume()
    }

    override fun onStop() {
        super.onStop()


    }

    override fun onDestroy() {
        super.onDestroy()

    }
}