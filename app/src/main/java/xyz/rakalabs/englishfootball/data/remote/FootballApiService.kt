package xyz.rakalabs.englishfootball.data.remote

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.rakalabs.englishfootball.data.model.*

interface FootballApiService {
    @GET("json/1/eventsnextleague.php")
    fun getNextMatch(@Query("id") league: String): Deferred<Response<MatchResponse>>

    @GET("json/1/eventspastleague.php")
    fun getLastMatch(@Query("id") league: String): Deferred<Response<MatchResponse>>

    @GET("json/1/lookupteam.php")
    fun getDetailTeam(@Query("id") teamId: String): Deferred<Response<TeamResponse>>

    @GET("json/1/lookupevent.php")
    fun getDetailMatch(@Query("id") eventId: String): Deferred<Response<MatchResponse>>

    @GET("json/1/all_leagues.php")
    fun getListLeagues(): Deferred<Response<LeagueResponse>>

    @GET("json/1/lookup_all_teams.php")
    fun getAllTeamInLeague(@Query("id") league: String): Deferred<Response<TeamResponse>>

    @GET("json/1/lookup_all_players.php")
    fun getAllPlayer(@Query("id") teamId: String): Deferred<Response<PlayerResponse>>

    @GET("json/1/searchevents.php")
    fun searchEvent(@Query("e") query: String): Deferred<Response<MatchSearchResponse>>

    @GET("json/1/searchteams.php")
    fun searchTeam(@Query("t") query: String): Deferred<Response<TeamResponse>>
}