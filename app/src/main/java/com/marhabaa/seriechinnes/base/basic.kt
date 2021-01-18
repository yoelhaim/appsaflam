package com.marhabaa.seriechinnes.base

import android.app.Activity
import android.content.Context
import com.bumptech.glide.Glide
import com.marhabaa.seriechinnes.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.pagepost.view.*
import kotlinx.coroutines.withContext

class basic {


    val BASE_URL = "https://fm.albatkora.online"
    // val BASE_URL = "https://telmidtice.selfhealt.com/"
    val image = BASE_URL
    val film = BASE_URL + "/film"
    val tend = BASE_URL + "/"
    val serie = BASE_URL + "/aflamall"
    val serieonce = BASE_URL + "/series/"
    val update = BASE_URL + "/update/"
    val addfavusr = BASE_URL + "api/data_api"
    val adduser = BASE_URL + "api/data_api.php?addnewusers"
    val fav = BASE_URL + "api/data_api?fav="
    val commenture = BASE_URL + "api/data_api?comment="
    val addlike = BASE_URL + "api/data_api?"
    val checkreact = BASE_URL + "api/data_api?checkreact="
    val banner =  "ca-app-pub-9225575939386535/1453883986"/// test banner  	ca-app-pub-3940256099942544/6300978111
    val inst =  "ca-app-pub-9225575939386535/5593080174"  //test inst ca-app-pub-3940256099942544/1033173712
    val adsurl = BASE_URL +  "ads/"



    fun BASE_URL() = "$BASE_URL"
    fun film() = "$film"
    fun tend() = "$tend"
    fun serie() = "$serie"
    fun serieonce() = "$serieonce"
    fun update() = "$update"
    fun adduser() = "$adduser"
    fun fav() = "$fav"
    fun commenture() = "$commenture"
    fun addlik() = "$addlike"
    fun checkreact() = "$checkreact"
    fun banner() = "$banner"
    fun inst() = "$inst"
    fun adsurl() = "$adsurl"

    data class ser(var namefilm:String,var random:String)
    data class alldata(var title :String,var type :String,var views :String,var desc :String,var film :String,var urlvideo :String,var randomfilm :String,var stt :String)
}