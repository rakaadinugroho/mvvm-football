package xyz.rakalabs.englishfootball.ui.dashboard.listteam

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
import xyz.rakalabs.englishfootball.data.model.TeamResponse
import xyz.rakalabs.englishfootball.databinding.ItemTeamBinding
import xyz.rakalabs.englishfootball.ui.dashboard.detailteam.DetailTeamActivity

class TeamAdapter(var context: Context): RecyclerView.Adapter<TeamViewHolder>() {
    private val differ = AsyncListDiffer<TeamResponse.Team>(this, DiffCallback)
    var teamResponseList: List<TeamResponse.Team> = emptyList()
        set(value) {
            field = value
            differ.submitList(value)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        TeamViewHolder(
            ItemTeamBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_team, parent, false
                )
            )
        )

    override fun getItemCount() = teamResponseList.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val currentTeam = differ.currentList[position]
        holder.bind(currentTeam)

        holder.binding.containerTeam.setOnClickListener {
            val intent = Intent(context, DetailTeamActivity::class.java)
            intent.putExtra(DetailTeamActivity.TEAM_DETAIL, Gson().toJson(currentTeam))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
class TeamViewHolder(val binding: ItemTeamBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: TeamResponse.Team) {
        Picasso.get().load(data.strTeamBadge).fit().into(binding.teamThumb)
        binding.teamName.text = data.strTeam
    }
}
object DiffCallback: DiffUtil.ItemCallback<TeamResponse.Team>() {
    override fun areItemsTheSame(oldItem: TeamResponse.Team, newItem: TeamResponse.Team): Boolean {
        return oldItem.idTeam == newItem.idTeam
    }

    override fun areContentsTheSame(oldItem: TeamResponse.Team, newItem: TeamResponse.Team): Boolean {
        return oldItem.idTeam == newItem.idTeam
    }

}