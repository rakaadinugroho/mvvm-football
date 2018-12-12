package xyz.rakalabs.englishfootball.ui.dashboard.listteam

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.rakalabs.englishfootball.data.model.LeagueResponse
import xyz.rakalabs.englishfootball.data.model.TeamResponse
import xyz.rakalabs.englishfootball.data.remote.APIResponse
import xyz.rakalabs.englishfootball.data.repository.FootballRepository
import xyz.rakalabs.englishfootball.utils.ViewCallback

class TeamListVM(val repository: FootballRepository): ViewModel() {
    private val tag = TeamListVM::class.java.simpleName
    lateinit var callbackView: ViewCallback
    var listLeague: MutableLiveData<List<LeagueResponse.League>> = MutableLiveData()
    var listOfTeam: MutableLiveData<List<TeamResponse.Team>> = MutableLiveData()
    fun setListener(viewCallback: ViewCallback) {
        callbackView = viewCallback
    }

    fun fetchLeagues() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getListLeague()
            when (response) {
                is APIResponse.Success -> {
                    listLeague.postValue(response.body?.leagues)
                }
                is APIResponse.Error -> {
                    Log.e(tag, "cannot load list leagues")
                }
            }
        }
    }

    fun fetchAllTeamInLeague(league: String) {
        callbackView.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getListTeam(league)
            when (response) {
                is APIResponse.Success -> {
                    listOfTeam.postValue(response.body?.teams)
                    callbackView.hideLoading()
                }
                is APIResponse.Error -> {
                    callbackView.hideLoading()
                }
            }
        }
    }

    fun searchTeam(query: String) {
        callbackView.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.searchTeam(query)
            when (response) {
                is APIResponse.Success -> {
                    listOfTeam.postValue(response.body?.teams)
                    callbackView.hideLoading()
                }
                is APIResponse.Error -> {
                    callbackView.hideLoading()
                }
            }
        }
    }

}