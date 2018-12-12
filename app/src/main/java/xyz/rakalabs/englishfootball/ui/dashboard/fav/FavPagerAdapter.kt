package xyz.rakalabs.englishfootball.ui.dashboard.fav

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import xyz.rakalabs.englishfootball.ui.dashboard.fav.match.FavoriteFragment
import xyz.rakalabs.englishfootball.ui.dashboard.fav.team.FavoriteTeamFragment

class FavPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteFragment()
            1 -> FavoriteTeamFragment()
            else -> FavoriteFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Matches"
            1 -> "Teams"
            else -> ""
        }
    }
}