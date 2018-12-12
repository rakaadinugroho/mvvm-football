package xyz.rakalabs.englishfootball.ui.dashboard.detailmatch

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.data.model.Favorite
import xyz.rakalabs.englishfootball.data.model.MatchResponse
import xyz.rakalabs.englishfootball.data.repository.database
import xyz.rakalabs.englishfootball.databinding.ActivityDetailMatchBinding
import xyz.rakalabs.englishfootball.utils.UtilDate
import xyz.rakalabs.englishfootball.utils.ViewCallback

class DetailMatchActivity : AppCompatActivity(), ViewCallback {
    companion object {
        const val EVENT_ID_DETAIL = "EVENT_ID"
    }
    override fun showLoading() {
        binding.progressDetail.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressDetail.visibility = View.INVISIBLE
    }

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    lateinit var binding: ActivityDetailMatchBinding
    lateinit var matchResponse: MatchResponse.Event
    private val viewModel: DetailMatchVM by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_match)
        viewModel.setListener(this)
        viewModel.fetchLastMatch(intent?.getStringExtra(EVENT_ID_DETAIL)!!)

        setSupportActionBar(binding.toolbar)
        Picasso.get().load(R.drawable.league).fit().into(binding.detailBanner)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        viewModel.homePicture.observe(this, Observer {
            Picasso.get().load(it).fit().into(binding.detailTeamHome)
        })
        viewModel.awayPicture.observe(this, Observer {
            Picasso.get().load(it).fit().into(binding.detailTeamAway)
        })
        viewModel.listMatchResponse.observe(this, Observer {
            matchResponse = it.first()
            loadedFavState()
        })
    }

    private fun loadedFavState() {
        favoriteState()
        loadDetailData()
        invalidateOptionsMenu()
    }

    private fun loadDetailData() {
        binding.detailDate.text = "${matchResponse.strDate} - ${matchResponse.strTime}"
        if (matchResponse.intHomeScore!=null)
            binding.detailScoreHome.text = matchResponse.intHomeScore.toString()
        if (matchResponse.intAwayScore!= null)
            binding.detailScoreAway.text = matchResponse.intAwayScore.toString()
        if (matchResponse.intHomeShots!=null)
            binding.homeShot.text = matchResponse.intHomeShots.toString()
        if (matchResponse.intAwayShots!= null)
            binding.awayShot.text = matchResponse.intAwayShots.toString()
        if (matchResponse.strHomeLineupGoalkeeper!=null)
            binding.goalKeeperHome.text = matchResponse.strHomeLineupGoalkeeper.toString()
        if (matchResponse.strAwayLineupGoalkeeper!= null)
            binding.goalKeeperAway.text = matchResponse.strAwayLineupGoalkeeper.toString()
        if (matchResponse.strHomeLineupForward != null) {
            matchResponse.strHomeLineupForward.toString().split(";").forEach {
                binding.forwardHome.append("$it \n")
            }
        }
        if (matchResponse.strHomeLineupMidfield != null) {
            matchResponse.strHomeLineupMidfield.toString().split(";").forEach {
                binding.midHome.append("$it \n")
            }
        }
        if (matchResponse.strHomeLineupDefense != null) {
            matchResponse.strHomeLineupDefense.toString().split(";").forEach {
                binding.defHome.append("$it \n")
            }
        }
        if (matchResponse.strAwayLineupForward != null) {
            matchResponse.strAwayLineupForward.toString().split(";").forEach {
                binding.forwardAway.append("$it \n")
            }
        }
        if (matchResponse.strAwayLineupMidfield != null) {
            matchResponse.strAwayLineupMidfield.toString().split(";").forEach {
                binding.midAway.append("$it \n")
            }
        }
        if (matchResponse.strAwayLineupDefense != null) {
            matchResponse.strAwayLineupDefense.toString().split(";").forEach {
                binding.defAway.append("$it \n")
            }
        }
        binding.detailNameHome.text = matchResponse.strHomeTeam
        binding.detailNameAway.text = matchResponse.strAwayTeam
        binding.dateScheduler.text = UtilDate.getSheduleDate(matchResponse.dateEvent, matchResponse.strTime, true)
        binding.timeScheduler.text = UtilDate.getSheduleDate(matchResponse.dateEvent, matchResponse.strTime, false)

        viewModel.fetchDetailTeam(matchResponse.idHomeTeam, 1)
        viewModel.fetchDetailTeam(matchResponse.idAwayTeam, 2)
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

    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_ID to matchResponse.idEvent,
                    Favorite.HOME_ID to matchResponse.idHomeTeam,
                    Favorite.HOME_NAME to matchResponse.strHomeTeam,
                    Favorite.HOME_SCORE to matchResponse.intHomeScore,
                    Favorite.AWAY_ID to matchResponse.idAwayTeam,
                    Favorite.AWAY_NAME to matchResponse.strAwayTeam,
                    Favorite.AWAY_SCORE to matchResponse.intAwayScore,
                    Favorite.STR_DATE to matchResponse.strDate)
            }
            toast(R.string.success_add_to_fav)
        } catch (e: SQLiteConstraintException){
            toast("Error ${e.localizedMessage}")
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to matchResponse.idEvent)
            }
            toast(R.string.success_remove_from_fav)
        } catch (e: SQLiteConstraintException){
            toast("Error ${e.localizedMessage}")
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(EVENT_ID = {id})",
                    "id" to matchResponse.idEvent)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }
}
