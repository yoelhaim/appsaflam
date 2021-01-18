package com.marhabaa.seriechinnes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.material.tabs.TabLayout
import com.marhabaa.seriechinnes.base.basic
import com.marhabaa.seriechinnes.fragment.homeFragment
import com.marhabaa.seriechinnes.fragment.moreFragment
import com.marhabaa.seriechinnes.fragment.serieFragment
import com.marhabaa.seriechinnes.fragment.trendFragment
import com.marhabaa.seriechinnes.view.webActivity
import kotlinx.android.synthetic.main.activity_home.*

class homeActivity : AppCompatActivity() {
    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbarfile)
        supportActionBar?.title="مسلسلات صينية"
        toolbarfile.setTitleTextAppearance(this, R.style.AppTheme_fontLayout);
        toolbarfile.setTitleTextAppearance(this, R.style.AppTheme_fontsizetoolbar);



        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = basic().banner()

        mAdView = findViewById(R.id.adViewfile)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)



  fblive.setOnClickListener{
      var int = Intent(this,webActivity::class.java)
      int.putExtra("link","https://m.facebook.com/pg/mar7abaaaa/videos/")
      startActivity(int)
  }
        privacy.setOnClickListener{
            var int = Intent(this,webActivity::class.java)
            int.putExtra("link","https://sites.google.com/view/mosalslat")
            startActivity(int)
        }
        var ViewPageAdapter = ViewPageAdapter(supportFragmentManager)
        ViewPageAdapter.addFr(homeFragment(),"الرئسية","")
        ViewPageAdapter.addFr(serieFragment(),"مسلسلات","")
        ViewPageAdapter.addFr(trendFragment(),"أكتر مشاهدة","")
        ViewPage.adapter=ViewPageAdapter
        tabLayout.setupWithViewPager(ViewPage)
        tabLayout.setTabTextColors(getResources().getColor(R.color.red), getResources().getColor(R.color.red))
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.home)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.slow_motion_video)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.local_fire_department)
    }
}