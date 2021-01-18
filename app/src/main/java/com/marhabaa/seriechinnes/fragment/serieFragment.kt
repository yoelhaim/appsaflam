package com.marhabaa.seriechinnes.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.GsonBuilder
import com.marhabaa.seriechinnes.R
import com.marhabaa.seriechinnes.base.basic
import com.marhabaa.seriechinnes.base.posts
import com.marhabaa.seriechinnes.view.aflamActivity
import com.marhabaa.seriechinnes.view.postActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_serie.*
import kotlinx.android.synthetic.main.fragment_serie.errorConne
import kotlinx.android.synthetic.main.fragment_serie.shimmerinc
import kotlinx.android.synthetic.main.fragment_serie.swiprefresh
import kotlinx.android.synthetic.main.fragment_trend.*
import kotlinx.android.synthetic.main.pagepost.view.*
import kotlinx.android.synthetic.main.series.view.*
import okhttp3.*
import java.io.IOException
import java.lang.Exception


class serieFragment : Fragment() {
    var client= OkHttpClient();
    val adapter = GroupAdapter<ViewHolder>()
    private var url:String?=null
    var shm:ShimmerFrameLayout?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_serie, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swiprefresh.setOnRefreshListener{
            shm!!.startShimmer()
            shimmerinc.visibility=View.VISIBLE
            adapter.clear()
            loaddata()
            errorConne.visibility = View.GONE
        }
        loaddata()



    }

    private fun loaddata() {
        swiprefresh.isRefreshing= true
        shm= activity!!.findViewById<View>(R.id.shimmer1) as ShimmerFrameLayout
        var recyclerView = activity!!.findViewById(R.id.rvserie) as RecyclerView
        recyclerView.adapter=adapter
        var req = Request
            .Builder()
            .url(basic().serie())
            .build()
        client.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity!!.runOnUiThread {
                    swiprefresh.isRefreshing= false
                    shm!!.stopShimmer()
                    errorConne.visibility = View.VISIBLE
                    shimmerinc.visibility = View.GONE
                    var trythis2 = activity!!.findViewById<View>(R.id.tryinternet) as Button

                    trythis2.setOnClickListener {
                        shm!!.startShimmer()
                        shimmerinc.visibility = View.VISIBLE
                        errorConne.visibility = View.GONE
                        loaddata()
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                var respo = response.body!!.string()
                var gson = GsonBuilder().create()
                var data = gson.fromJson(respo, Array<basic.ser>::class.java) as Array<basic.ser>
                    if (data == null){
                        Toast.makeText(activity,"no data", Toast.LENGTH_LONG).show()
                    }
                activity!!.runOnUiThread {
                    swiprefresh.isRefreshing= false
                    for (fetch in data) {
                        shm!!.startShimmer()
                        shimmerinc.visibility = View.GONE
                        adapter.add(Adapter(fetch))
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

            inner class  Adapter(var post : basic.ser): Item<ViewHolder>(){
        override fun getLayout(): Int {
            return R.layout.series
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            var xml =viewHolder.itemView
            xml.namefilm.text= post.namefilm

            val wagrod = AnimationUtils.loadAnimation(activity!!,R.anim.slidedown)
            val snumber =xml.tofilm
            snumber.startAnimation(wagrod)

            xml.tofilm.setOnClickListener{
                val intent = Intent(activity!!, aflamActivity::class.java)
                intent.putExtra("random",post.random)
                activity!!.startActivity(intent)
//                val bundle = Bundle()
//                bundle.putString("random",post.random)
//
//                val transaction = activity!!.supportFragmentManager.beginTransaction()
//                val frag2 = homeFragment()
//                frag2.arguments = bundle
//
//                transaction.replace(R.id.ViewPage, frag2)
//                transaction.addToBackStack(null)
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                transaction.commit()
            }
          }

    }

            }

