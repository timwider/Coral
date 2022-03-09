package com.example.coral.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.coral.App
import com.example.coral.R
import com.example.coral.view.member_details.MemberDetailsHolderFragment
import com.example.coral.viewmodels.MainViewModel
import com.example.coral.viewmodels.MainViewModelFactory

private const val HOME_FRAGMENT_TAG = "home_fragment"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewModelProvider(
            this,
            MainViewModelFactory( (applicationContext as App).repository)
        ) [MainViewModel::class.java]

        val homeFragment = if (savedInstanceState == null) {
            HomeFragment()

        } else supportFragmentManager.findFragmentByTag(HOME_FRAGMENT_TAG)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, homeFragment!!, HOME_FRAGMENT_TAG)
            .commit()
    }
}