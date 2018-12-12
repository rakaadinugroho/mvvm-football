package xyz.rakalabs.englishfootball.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import xyz.rakalabs.englishfootball.R

import xyz.rakalabs.englishfootball.databinding.ActivityDasboardBinding
import xyz.rakalabs.englishfootball.ui.dashboard.fav.FavoriteMainFragment
import xyz.rakalabs.englishfootball.ui.dashboard.fav.match.FavoriteFragment
import xyz.rakalabs.englishfootball.ui.dashboard.listmatch.MatchListFragment
import xyz.rakalabs.englishfootball.ui.dashboard.listteam.ListTeamFragment
import xyz.rakalabs.englishfootball.ui.dashboard.mainlistmatch.MainListOfMatchFragment

class DasboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDasboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dasboard)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.list_match -> {loadListMatch(savedInstanceState)}
                R.id.list_team -> {loadListTeam(savedInstanceState)}
                R.id.favorites -> {loadFavoriteMatch(savedInstanceState)}
                else -> {}
            }
            true
        }
        binding.bottomNavigation.selectedItemId = R.id.list_match
    }

    private fun loadListTeam(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, ListTeamFragment(), ListTeamFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadListMatch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, MainListOfMatchFragment(), MatchListFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoriteMatch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container,
                    FavoriteMainFragment(), FavoriteMainFragment::class.java.simpleName)
                .commit()
        }
    }
}
