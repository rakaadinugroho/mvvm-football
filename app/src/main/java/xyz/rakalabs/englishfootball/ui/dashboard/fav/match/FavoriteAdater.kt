package xyz.rakalabs.englishfootball.ui.dashboard.fav.match

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.data.model.Favorite
import xyz.rakalabs.englishfootball.databinding.ItemFavoriteBinding
import xyz.rakalabs.englishfootball.ui.dashboard.detailmatch.DetailMatchActivity

class FavoriteAdater(val context: Context): RecyclerView.Adapter<FavoriteViewHolder>() {
    private val differ = AsyncListDiffer<Favorite>(this,
        DiffCallback
    )
    var matchResponseList: List<Favorite> = emptyList()
        set(value){
            field = value
            differ.submitList(value)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteViewHolder(
            ItemFavoriteBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_favorite, parent, false
                )
            )
        )

    override fun getItemCount() = matchResponseList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentMatch = differ.currentList[position]
        holder.binding.matchTeam = currentMatch
        holder.binding.root.setOnClickListener {
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra(DetailMatchActivity.EVENT_ID_DETAIL, currentMatch.eventId)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}
class FavoriteViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)
object DiffCallback: DiffUtil.ItemCallback<Favorite>() {
    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem.eventId == newItem.eventId
    }

    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem.awayId == newItem.awayId && oldItem.homeId == newItem.homeId
    }

}