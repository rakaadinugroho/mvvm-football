package xyz.rakalabs.englishfootball.ui.dashboard.detailteam.listplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.data.model.PlayerResponse
import xyz.rakalabs.englishfootball.databinding.ItemPlayerBinding
import xyz.rakalabs.englishfootball.ui.dashboard.detailplayer.DetailPlayerActivity

class PlayerAdapter(val context: Context): RecyclerView.Adapter<PlayerViewHolder>() {
    private val differ = AsyncListDiffer<PlayerResponse.Player>(this, DiffCallback)
    var playerResponseList: List<PlayerResponse.Player> = emptyList()
        set(value) {
            field = value
            differ.submitList(value)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        PlayerViewHolder(
            ItemPlayerBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_player, parent, false
                )
            )
        )

    override fun getItemCount() = playerResponseList.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val currentPlayer = differ.currentList[position]
        holder.bind(currentPlayer)
        holder.binding.playerContainer.setOnClickListener {
            val intent = Intent(context, DetailPlayerActivity::class.java)
            intent.putExtra(DetailPlayerActivity.PLAYER_DATA, Gson().toJson(currentPlayer))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}
class PlayerViewHolder(val binding: ItemPlayerBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: PlayerResponse.Player) {
        binding.playerName.text = data.strPlayer
        binding.playerPosition.text = data.strPosition
        Picasso.get().load(data.strCutout).fit().into(binding.playerThumb)
    }
}
object DiffCallback: DiffUtil.ItemCallback<PlayerResponse.Player>() {
    override fun areItemsTheSame(oldItem: PlayerResponse.Player, newItem: PlayerResponse.Player): Boolean {
        return oldItem.idPlayer == newItem.idPlayer
    }

    override fun areContentsTheSame(oldItem: PlayerResponse.Player, newItem: PlayerResponse.Player): Boolean {
        return oldItem.idPlayer == newItem.idPlayer
    }

}