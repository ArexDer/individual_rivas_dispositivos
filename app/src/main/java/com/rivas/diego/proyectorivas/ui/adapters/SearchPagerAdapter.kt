package com.rivas.diego.proyectorivas.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.rivas.diego.proyectorivas.ui.fragments.search.SearchMoviesFragment
//import com.rivas.diego.proyectorivas.ui.fragments.search.SearchTVShowsFragment
//import com.rivas.diego.proyectorivas.ui.fragments.search.SearchPeopleFragment

class SearchPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SearchMoviesFragment()
           // 1 -> SearchTVShowsFragment()
            //2 -> SearchPeopleFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Movies"
            1 -> "TV Shows"
            2 -> "People"
            else -> null
        }
    }
}