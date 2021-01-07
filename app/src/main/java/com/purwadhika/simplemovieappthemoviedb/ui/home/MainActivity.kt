package com.purwadhika.simplemovieappthemoviedb.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.purwadhika.simplemovieappthemoviedb.R
import com.purwadhika.simplemovieappthemoviedb.databinding.ActivityMainBinding
import com.purwadhika.simplemovieappthemoviedb.ui.favorite.FavoriteListFragment
import com.purwadhika.simplemovieappthemoviedb.ui.trending.TrendingFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        switchFragment(TrendingFragment())

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_trending -> {
                    switchFragment(TrendingFragment())
                }
                R.id.menu_favorite -> {
                    switchFragment(FavoriteListFragment())
                }
            }
            true
        }
    }

    private fun switchFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

}