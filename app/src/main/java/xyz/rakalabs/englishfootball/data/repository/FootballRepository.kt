package xyz.rakalabs.englishfootball.data.repository

import xyz.rakalabs.englishfootball.data.model.*
import xyz.rakalabs.englishfootball.data.remote.APIResponse
import xyz.rakalabs.englishfootball.data.remote.FootballApiService

class FootballRepository(private val apiService: FootballApiService) {
    suspend fun getLastMatch(league: String): APIResponse<MatchResponse?> {
        val apiResponse = apiService.getLastMatch(league).await()
        return if (apiResponse.isSuccessful)
            APIResponse.Success(apiResponse.body())
        else {
            APIResponse.Error(apiResponse.message(), apiResponse.code())
        }
    }
    suspend fun getNextMatch(league: String): APIResponse<MatchResponse?> {
        val apiResponse = apiService.getNextMatch(league).await()
        return if (apiResponse.isSuccessful)
            APIResponse.Success(apiResponse.body())
        else {
            APIResponse.Error(apiResponse.message(), apiResponse.code())
        }
    }

    suspend fun getDetailTeam(teamId: String): APIResponse<TeamResponse?> {
        val apiResponse = apiService.getDetailTeam(teamId).await()
        return if (apiResponse.isSuccessful)
            APIResponse.Success(apiResponse.body())
        else {
            APIResponse.Error(apiResponse.message(), apiResponse.code())
        }
    }

    suspend fun getDetailMatch(eventId: String): APIResponse<MatchResponse?> {
        val apiResponse = apiService.getDetailMatch(eventId).await()
        return if (apiResponse.isSuccessful)
            APIResponse.Success(apiResponse.body())
        else {
            APIResponse.Error(apiResponse.message(), apiResponse.code())
        }
    }

    suspend fun getListLeague(): APIResponse<LeagueResponse?> {
        val apiResponse = apiService.getListLeagues().await()
        return if (apiResponse.isSuccessful)
            APIResponse.Success(apiResponse.body())
        else
            APIResponse.Error(apiResponse.message(), apiResponse.code())
    }

    suspend fun getListTeam(leagueId: String): APIResponse<TeamResponse?> {
        val apiResponse = apiService.getAllTeamInLeague(leagueId).await()
        return if (apiResponse.isSuccessful)
            APIResponse.Success(apiResponse.body())
        else
            APIResponse.Error(apiResponse.message(), apiResponse.code())
    }

    suspend fun getListPlayer(teamId: String): APIResponse<PlayerResponse?> {
        val apiResponse = apiService.getAllPlayer(teamId).await()
        return if (apiResponse.isSuccessful)
            APIResponse.Success(apiResponse.body())
        else
            APIResponse.Error(apiResponse.message(), apiResponse.code())
    }

    suspend fun searchEvent(query: String): APIResponse<MatchSearchResponse?> {
        val apiResponse = apiService.searchEvent(query).await()
        return if (apiResponse.isSuccessful)
            APIResponse.Success(apiResponse.body())
        else
            APIResponse.Error(apiResponse.message(), apiResponse.code())
    }

    suspend fun searchTeam(query: String): APIResponse<TeamResponse?> {
        val apiResponse = apiService.searchTeam(query).await()
        return if (apiResponse.isSuccessful)
            APIResponse.Success(apiResponse.body())
        else
            APIResponse.Error(apiResponse.message(), apiResponse.code())
    }

}