package com.marhabaa.seriechinnes

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ViewPageAdapter(frgm: FragmentManager): FragmentPagerAdapter(frgm){

    var frag = ArrayList<Fragment>()
    var titles = ArrayList<String>()
    var up = ArrayList<String>()
    fun addFr(frgm: Fragment, titles: String, up: String){


        this.frag.add(frgm)
        this.titles.add(titles + up)


    }

    override fun getItem(position: Int): Fragment {
        return this.frag[position]

    }

    override fun getCount(): Int {
        return this.frag.size

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return this.titles[position]

    }


}