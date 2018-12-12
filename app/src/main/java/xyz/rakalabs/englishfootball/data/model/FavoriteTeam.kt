package xyz.rakalabs.englishfootball.data.model

data class FavoriteTeam(
    val id: Long?,
    var idTeam: String?,
    var strCountry: String?,
    var strTeamBadge: String?,
    var strTeamJersey: String?,
    var strTeamLogo: String?,
    var strTeam: String?,
    var intFormedYear: String?,
    var strStadium: String?,
    var strDescriptionEN: String?
) {
    companion object {
        const val TABLE_FAV_TEAM = "TABLE_FAV_TEAM"
        const val ID = "ID_"
        const val TEAM_ID = "TEAM_ID"
        const val COUNTRY = "COUNTRY"
        const val BADGE = "BADGE"
        const val JERSEY = "JERSEY"
        const val LOGO = "LOGO"
        const val TEAM_NAME = "TEAM_NAME"
        const val FORMED = "FORMED"
        const val STADIUM = "STADIUM"
        const val DESCRIPTION = "DESCRIPTION"
    }
}