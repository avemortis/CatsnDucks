package com.example.catsnducks

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.catsnducks.ui.main.SectionsPagerAdapter
import com.example.catsnducks.databinding.ActivityMainBinding
import com.example.catsnducks.ui.looker.PictureLookerDialogFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }

    fun setArgsAndShowPictureLookerDialogFragment(url : String){
        val dialog = PictureLookerDialogFragment()
        val bundle = Bundle()
        bundle.putString(PictureLookerDialogFragment.TAG, url)

        dialog.arguments = bundle
        dialog.show(supportFragmentManager, PictureLookerDialogFragment.TAG)
    }
}