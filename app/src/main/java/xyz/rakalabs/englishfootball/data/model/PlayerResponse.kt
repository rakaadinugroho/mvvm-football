package xyz.rakalabs.englishfootball.data.model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("player")
    val player: List<Player>
) {
    data class Player(
        @SerializedName("dateBorn")
        val dateBorn: String,
        @SerializedName("dateSigned")
        val dateSigned: String,
        @SerializedName("idPlayer")
        val idPlayer: String,
        @SerializedName("strBirthLocation")
        val strBirthLocation: String,
        @SerializedName("strCutout")
        val strCutout: String,
        @SerializedName("strDescriptionEN")
        val strDescriptionEN: String,
        @SerializedName("strGender")
        val strGender: String,
        @SerializedName("strHeight")
        val strHeight: String,
        @SerializedName("strNationality")
        val strNationality: String,
        @SerializedName("strPlayer")
        val strPlayer: String,
        @SerializedName("strPosition")
        val strPosition: String,
        @SerializedName("strSigning")
        val strSigning: String,
        @SerializedName("strSport")
        val strSport: String,
        @SerializedName("strTeam")
        val strTeam: String,
        @SerializedName("strThumb")
        val strThumb: String,
        @SerializedName("strWage")
        val strWage: String,
        @SerializedName("strWeight")
        val strWeight: String
    )
}