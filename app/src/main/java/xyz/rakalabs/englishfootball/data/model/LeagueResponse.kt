package xyz.rakalabs.englishfootball.data.model

import com.google.gson.annotations.SerializedName

data class LeagueResponse(
    @SerializedName("leagues")
    val leagues: List<League>
) {
    data class League(
        @SerializedName("idLeague")
        val idLeague: String,
        @SerializedName("strLeague")
        val strLeague: String,
        @SerializedName("strLeagueAlternate")
        val strLeagueAlternate: String,
        @SerializedName("strSport")
        val strSport: String
    ) {
        override fun toString(): String {
            return strLeague
        }
    }
}