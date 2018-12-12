package xyz.rakalabs.englishfootball.ui.dashboard.listmatch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.rakalabs.englishfootball.data.model.LeagueResponse
import xyz.rakalabs.englishfootball.data.model.MatchResponse
import xyz.rakalabs.englishfootball.data.remote.APIResponse
import xyz.rakalabs.englishfootball.data.repository.FootballRepository
import xyz.rakalabs.englishfootball.utils.ViewCallback

class MatchListVM(val repository: FootballRepository): ViewModel() {
    private val tag = MatchListVM::class.java.simpleName
    lateinit var callbackView: ViewCallback
    var listMatchResponse: MutableLiveData<List<MatchResponse.Event>> = MutableLiveData()
    var listLeague: MutableLiveData<List<LeagueResponse.League>> = MutableLiveData()
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
    fun fetchLastMatch(league: String) {
        callbackView.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getLastMatch(league)
            when (response) {
                is APIResponse.Success -> {
                    callbackView.hideLoading()
                    listMatchResponse.postValue(response.body?.events)
                }
                is APIResponse.Error -> {
                    callbackView.hideLoading()
                    Log.e(tag, "error load list matchResponse")
                }
            }
        }
    }

    fun fetchNextMatch(league: String) {
        callbackView.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getNextMatch(league)
            when (response) {
                is APIResponse.Success ->  {
                    listMatchResponse.postValue(response.body?.events)
                    callbackView.hideLoading()
                }
                is APIResponse.Error -> {
                    Log.e(tag, "error load list matchResponse")
                    callbackView.hideLoading()
                }
            }
        }
    }

    fun searchMatch(query: String) {
        callbackView.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.searchEvent(query)
            when (response) {
                is APIResponse.Success ->  {
                    listMatchResponse.postValue(response.body?.events)
                    callbackView.hideLoading()
                }
                is APIResponse.Error -> {
                    Log.e(tag, "error load list matchResponse")
                    callbackView.hideLoading()
                }
            }
        }
    }
}