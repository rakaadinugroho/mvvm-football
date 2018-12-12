package xyz.rakalabs.englishfootball.ui.dashboard.mainlistmatch

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import xyz.rakalabs.englishfootball.ui.dashboard.listmatch.MatchListFragment

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MatchListFragment.newInstance("1")
            1 -> MatchListFragment.newInstance("2")
            else -> MatchListFragment.newInstance("1")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Next"
            1 -> "Last"
            else -> ""
        }
    }
}