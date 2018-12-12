package xyz.rakalabs.englishfootball.ui.dashboard.detailplayer

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.data.model.PlayerResponse
import xyz.rakalabs.englishfootball.databinding.ActivityDetailPlayerBinding

class DetailPlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailPlayerBinding
    lateinit var player: PlayerResponse.Player

    companion object {
        const val PLAYER_DATA = "player_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_player)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        loadData()
    }

    private fun loadData() {
        player = Gson().fromJson(intent.getStringExtra(PLAYER_DATA), PlayerResponse.Player::class.java)
        Picasso.get().load(player.strThumb).fit().into(binding.thumbnail)
        supportActionBar?.title = player.strPlayer
        binding.weight.text = player.strWeight
        binding.height.text = player.strHeight
        binding.position.text = player.strPosition
        binding.description.text = player.strDescriptionEN
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
