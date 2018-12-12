package xyz.rakalabs.englishfootball.ui.dashboard.listmatch

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import org.jetbrains.anko.toast
import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.data.model.MatchResponse
import xyz.rakalabs.englishfootball.databinding.ItemMatchBinding
import xyz.rakalabs.englishfootball.ui.dashboard.detailmatch.DetailMatchActivity
import xyz.rakalabs.englishfootball.utils.UtilDate
import java.util.*

class MatchAdapter(val context: Context): RecyclerView.Adapter<MatchViewHolder>() {
    var isNextMatch = false
    private val differ = AsyncListDiffer<MatchResponse.Event>(this,
        DiffCallback
    )
    var matchResponseList: List<MatchResponse.Event> = emptyList()
        set(value){
            field = value
            differ.submitList(value)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MatchViewHolder(
            ItemMatchBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_match, parent, false
                )
            )
        )

    override fun getItemCount() = matchResponseList.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val currentMatch = differ.currentList[position]
        holder.binding.matchTeam = currentMatch
        holder.binding.matchTime.text = UtilDate.getSheduleDate(currentMatch.dateEvent, currentMatch.strTime, true)
        holder.binding.matchTimeHour.text = UtilDate.getSheduleDate(currentMatch.dateEvent, currentMatch.strTime, false)
        holder.binding.containerMatch.setOnClickListener {
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra(DetailMatchActivity.EVENT_ID_DETAIL, currentMatch.idEvent)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
        holder.binding.addCalendar.visibility = if (isNextMatch) View.VISIBLE else View.GONE
        holder.binding.addCalendar.setOnClickListener {
            val startMillis: Long = Calendar.getInstance().run {
                set(
                    UtilDate.getSpecificDate(currentMatch.dateEvent, currentMatch.strTime, UtilDate.SpecificDate.Year),
                    UtilDate.getSpecificDate(currentMatch.dateEvent, currentMatch.strTime, UtilDate.SpecificDate.Month),
                    UtilDate.getSpecificDate(currentMatch.dateEvent, currentMatch.strTime, UtilDate.SpecificDate.Day),
                    UtilDate.getSpecificDate(currentMatch.dateEvent, currentMatch.strTime, UtilDate.SpecificDate.Hour),
                    UtilDate.getSpecificDate(currentMatch.dateEvent, currentMatch.strTime, UtilDate.SpecificDate.Minute))
                timeInMillis
            }

            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                .putExtra(CalendarContract.Events.TITLE, currentMatch.strEvent)
                .putExtra(CalendarContract.Events.DESCRIPTION, currentMatch.strLeague)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, currentMatch.strHomeTeam)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}
class MatchViewHolder(val binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root)
object DiffCallback: DiffUtil.ItemCallback<MatchResponse.Event>() {
    override fun areItemsTheSame(oldItem: MatchResponse.Event, newItem: MatchResponse.Event): Boolean {
        return oldItem.idEvent == newItem.idEvent
    }

    override fun areContentsTheSame(oldItem: MatchResponse.Event, newItem: MatchResponse.Event): Boolean {
        return oldItem.idAwayTeam == newItem.idAwayTeam && oldItem.idHomeTeam == newItem.idHomeTeam
    }

}