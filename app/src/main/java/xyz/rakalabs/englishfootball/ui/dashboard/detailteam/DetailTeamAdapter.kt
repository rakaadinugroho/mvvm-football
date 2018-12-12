package xyz.rakalabs.englishfootball.ui.dashboard.detailteam

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import xyz.rakalabs.englishfootball.ui.dashboard.detailteam.listplayer.ListPlayerFragment
import xyz.rakalabs.englishfootball.ui.dashboard.detailteam.overview.OverviewTeamFragment
import xyz.rakalabs.englishfootball.ui.dashboard.listmatch.MatchListFragment

class DetailTeamAdapter(fm: FragmentManager?, private val teamId: String, private val overView: String) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> OverviewTeamFragment.newInstance(overView)
            1 -> ListPlayerFragment.newInstance(teamId)
            else -> OverviewTeamFragment.newInstance(overView)
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Overview"
            1 -> "Players"
            else -> ""
        }
    }
}