package xyz.rakalabs.englishfootball.ui.dashboard.fav.team

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.data.model.FavoriteTeam
import xyz.rakalabs.englishfootball.data.model.TeamResponse
import xyz.rakalabs.englishfootball.databinding.ItemTeamBinding
import xyz.rakalabs.englishfootball.ui.dashboard.detailteam.DetailTeamActivity

class FavoriteTeamAdapter(val context: Context): RecyclerView.Adapter<FavoriteTeamHolder>() {
    private val differ = AsyncListDiffer<FavoriteTeam>(this, DiffCallback)
    var teamList: List<FavoriteTeam> = emptyList()
        set(value) {
            field = value
            differ.submitList(value)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteTeamHolder (
            ItemTeamBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_team, parent, false
                )
            )
        )

    override fun getItemCount() = teamList.size

    override fun onBindViewHolder(holder: FavoriteTeamHolder, position: Int) {
        val currentTeam = differ.currentList[position]
        holder.bind(currentTeam)
        holder.binding.containerTeam.setOnClickListener {
            val team: TeamResponse.Team = TeamResponse.Team(currentTeam.idTeam!!, currentTeam.strCountry!!, "", currentTeam.strTeamBadge!!, currentTeam.strTeamJersey!!, currentTeam.strTeamLogo!!, currentTeam.strTeam!!, currentTeam.strTeam!!, currentTeam.intFormedYear!!, currentTeam.strStadium!!, currentTeam.strDescriptionEN!!)
            val intent = Intent(context, DetailTeamActivity::class.java)
            intent.putExtra(DetailTeamActivity.TEAM_DETAIL, Gson().toJson(team))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
class FavoriteTeamHolder(val binding: ItemTeamBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: FavoriteTeam) {
        binding.teamName.text = data.strTeam
        Picasso.get().load(data.strTeamBadge).fit().into(binding.teamThumb)
    }
}
object DiffCallback: DiffUtil.ItemCallback<FavoriteTeam>() {
    override fun areItemsTheSame(oldItem: FavoriteTeam, newItem: FavoriteTeam): Boolean {
        return oldItem.idTeam == newItem.idTeam
    }

    override fun areContentsTheSame(oldItem: FavoriteTeam, newItem: FavoriteTeam): Boolean {
        return oldItem.idTeam == newItem.idTeam
    }

}