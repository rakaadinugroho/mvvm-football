package xyz.rakalabs.englishfootball.data.model

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("events")
    var events: List<Event>
) {
    data class Event(
        @SerializedName("dateEvent")
        var dateEvent: String,
        @SerializedName("idAwayTeam")
        var idAwayTeam: String,
        @SerializedName("idEvent")
        var idEvent: String,
        @SerializedName("idHomeTeam")
        var idHomeTeam: String,
        @SerializedName("idLeague")
        var idLeague: String,
        @SerializedName("idSoccerXML")
        var idSoccerXML: String,
        @SerializedName("intAwayScore")
        var intAwayScore: Any?,
        @SerializedName("intAwayShots")
        var intAwayShots: Any?,
        @SerializedName("intHomeScore")
        var intHomeScore: Any?,
        @SerializedName("intHomeShots")
        var intHomeShots: Any?,
        @SerializedName("intRound")
        var intRound: String,
        @SerializedName("intSpectators")
        var intSpectators: Any,
        @SerializedName("strAwayFormation")
        var strAwayFormation: Any,
        @SerializedName("strAwayGoalDetails")
        var strAwayGoalDetails: Any,
        @SerializedName("strAwayLineupDefense")
        var strAwayLineupDefense: Any?,
        @SerializedName("strAwayLineupForward")
        var strAwayLineupForward: Any?,
        @SerializedName("strAwayLineupGoalkeeper")
        var strAwayLineupGoalkeeper: Any?,
        @SerializedName("strAwayLineupMidfield")
        var strAwayLineupMidfield: Any?,
        @SerializedName("strAwayLineupSubstitutes")
        var strAwayLineupSubstitutes: Any,
        @SerializedName("strAwayRedCards")
        var strAwayRedCards: Any,
        @SerializedName("strAwayTeam")
        var strAwayTeam: String,
        @SerializedName("strAwayYellowCards")
        var strAwayYellowCards: Any,
        @SerializedName("strBanner")
        var strBanner: Any,
        @SerializedName("strCircuit")
        var strCircuit: Any,
        @SerializedName("strCity")
        var strCity: Any,
        @SerializedName("strCountry")
        var strCountry: Any,
        @SerializedName("strDate")
        var strDate: String,
        @SerializedName("strDescriptionEN")
        var strDescriptionEN: Any,
        @SerializedName("strEvent")
        var strEvent: String,
        @SerializedName("strFanart")
        var strFanart: Any,
        @SerializedName("strFilename")
        var strFilename: String,
        @SerializedName("strHomeFormation")
        var strHomeFormation: Any,
        @SerializedName("strHomeGoalDetails")
        var strHomeGoalDetails: Any,
        @SerializedName("strHomeLineupDefense")
        var strHomeLineupDefense: Any?,
        @SerializedName("strHomeLineupForward")
        var strHomeLineupForward: Any?,
        @SerializedName("strHomeLineupGoalkeeper")
        var strHomeLineupGoalkeeper: Any?,
        @SerializedName("strHomeLineupMidfield")
        var strHomeLineupMidfield: Any?,
        @SerializedName("strHomeLineupSubstitutes")
        var strHomeLineupSubstitutes: Any,
        @SerializedName("strHomeRedCards")
        var strHomeRedCards: Any,
        @SerializedName("strHomeTeam")
        var strHomeTeam: String,
        @SerializedName("strHomeYellowCards")
        var strHomeYellowCards: Any,
        @SerializedName("strLeague")
        var strLeague: String,
        @SerializedName("strLocked")
        var strLocked: String,
        @SerializedName("strMap")
        var strMap: Any,
        @SerializedName("strPoster")
        var strPoster: Any,
        @SerializedName("strResult")
        var strResult: Any,
        @SerializedName("strSeason")
        var strSeason: String,
        @SerializedName("strSport")
        var strSport: String,
        @SerializedName("strTVStation")
        var strTVStation: Any,
        @SerializedName("strThumb")
        var strThumb: Any,
        @SerializedName("strTime")
        var strTime: String
    )
}