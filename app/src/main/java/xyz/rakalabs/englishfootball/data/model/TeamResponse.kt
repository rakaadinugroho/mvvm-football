package xyz.rakalabs.englishfootball.data.model

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("teams")
    var teams: List<Team>
) {
    data class Team(
        @SerializedName("idTeam")
        var idTeam: String,
        @SerializedName("strCountry")
        var strCountry: String,
        @SerializedName("strGender")
        var strGender: String,
        @SerializedName("strTeamBadge")
        var strTeamBadge: String,
        @SerializedName("strTeamJersey")
        var strTeamJersey: String,
        @SerializedName("strTeamLogo")
        var strTeamLogo: String,
        @SerializedName("strTeam")
        var strTeam: String,
        @SerializedName("strAlternate")
        var strAlternate: String,
        @SerializedName("intFormedYear")
        var intFormedYear: String,
        @SerializedName("strStadium")
        var strStadium: String,
        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String
    )
}