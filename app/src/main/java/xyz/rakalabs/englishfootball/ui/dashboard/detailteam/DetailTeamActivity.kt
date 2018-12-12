package xyz.rakalabs.englishfootball.ui.dashboard.detailteam

import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.data.model.FavoriteTeam
import xyz.rakalabs.englishfootball.data.model.TeamResponse
import xyz.rakalabs.englishfootball.data.repository.database
import xyz.rakalabs.englishfootball.databinding.ActivityDetailTeamBinding

class DetailTeamActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailTeamBinding
    companion object {
        const val TEAM_DETAIL = "team_detail"
    }
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    lateinit var teamData: TeamResponse.Team
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_team)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        loadData()
    }

    private fun loadData() {
        teamData = Gson().fromJson(intent.getStringExtra(TEAM_DETAIL), TeamResponse.Team::class.java)
        val detailPagerAdapter = DetailTeamAdapter(supportFragmentManager, overView = teamData.strDescriptionEN, teamId = teamData.idTeam)
        binding.teamDetailView.adapter = detailPagerAdapter
        binding.tabs.setupWithViewPager(binding.teamDetailView)

        /*
        Detail on this Activity
        */
        binding.teamName.text = teamData.strTeam
        binding.teamSience.text = teamData.intFormedYear
        binding.teamStadium.text = teamData.strStadium
        Picasso.get().load(teamData.strTeamBadge).fit().into(binding.teamBadge)

        favoriteState()
        invalidateOptionsMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteTeam.TABLE_FAV_TEAM,
                    FavoriteTeam.TEAM_ID to teamData.idTeam,
                    FavoriteTeam.COUNTRY to teamData.strCountry,
                    FavoriteTeam.BADGE to teamData.strTeamBadge,
                    FavoriteTeam.JERSEY to teamData.strTeamJersey,
                    FavoriteTeam.LOGO to teamData.strTeamLogo,
                    FavoriteTeam.TEAM_NAME to teamData.strTeam,
                    FavoriteTeam.FORMED to teamData.intFormedYear,
                    FavoriteTeam.STADIUM to teamData.strStadium,
                    FavoriteTeam.DESCRIPTION to teamData.strDescriptionEN
                    )
                toast(R.string.success_add_to_fav)
            }
        } catch (e: SQLiteConstraintException) {
            toast("Error ${e.localizedMessage}")
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAV_TEAM, "(TEAM_ID = {id})",
                    "id" to teamData.idTeam)
            }
            toast(R.string.success_remove_from_fav)
        } catch (e: SQLiteConstraintException){
            toast("Error ${e.localizedMessage}")
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeam.TABLE_FAV_TEAM)
                .whereArgs("(TEAM_ID = {id})", "id" to teamData.idTeam)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
    }

}
