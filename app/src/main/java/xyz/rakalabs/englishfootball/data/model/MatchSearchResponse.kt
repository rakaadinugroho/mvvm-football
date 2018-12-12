package xyz.rakalabs.englishfootball.data.model

import com.google.gson.annotations.SerializedName


data class MatchSearchResponse(
    @SerializedName("event")
    var events: List<MatchResponse.Event>
)