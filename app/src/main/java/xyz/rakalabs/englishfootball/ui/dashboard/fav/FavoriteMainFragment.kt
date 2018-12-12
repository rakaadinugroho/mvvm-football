package xyz.rakalabs.englishfootball.ui.dashboard.fav


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.databinding.FragmentFavoriteMainBinding
import xyz.rakalabs.englishfootball.ui.dashboard.DasboardActivity

class FavoriteMainFragment : Fragment() {
    lateinit var binding: FragmentFavoriteMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteMainBinding.bind(inflater.inflate(R.layout.fragment_favorite_main, container, false))
        val thisActivity = activity as DasboardActivity
        thisActivity.setSupportActionBar(binding.toolbar)
        thisActivity.actionBar?.title = "My Favorite"
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainPagerAdapter = FavPagerAdapter(childFragmentManager)
        binding.mainOfFavorite.adapter = mainPagerAdapter
        binding.tabs.setupWithViewPager(binding.mainOfFavorite)
    }
}
