package com.marhabaa.seriechinnes.base

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.marhabaa.seriechinnes.R
import com.marhabaa.seriechinnes.view.postActivity
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.pagepost.view.*
import kotlinx.android.synthetic.main.series.view.*

class posts {
    class  Adapter(var post : basic.alldata ,var i:Int,var contex:Context): Item<ViewHolder>(){
        override fun getLayout(): Int {
            return R.layout.pagepost
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            var xml =viewHolder.itemView
            xml.textView.text= post.title
            if (i != 0){
                xml.trendends.text="#"+i.toString()
                xml.trendends.visibility=View.VISIBLE
            }
            xml.type.text="مسلسل"
            xml.views.text=post.views
            xml.film.text=post.film
            xml.getpost.setOnClickListener {
                val intent = Intent(contex, postActivity::class.java)
                intent.putExtra("title",post.title)
                intent.putExtra("desc",post.desc)
                intent.putExtra("views",post.views)
                intent.putExtra("urlvideo",post.urlvideo)
                intent.putExtra("randomfilm",post.randomfilm)
                intent.putExtra("type",post.type)
                intent.putExtra("film",post.film)
                intent.putExtra("stt",post.stt)
                contex.startActivity(intent)

            }
            val wagrod = AnimationUtils.loadAnimation(contex!!,R.anim.slideright)
            val snumber =xml.getpost
            snumber.startAnimation(wagrod)

            Glide.with(contex).load("https://i.ytimg.com/vi/"+ post.urlvideo +"/maxresdefault.jpg").into(xml.img);
        }

    }
}