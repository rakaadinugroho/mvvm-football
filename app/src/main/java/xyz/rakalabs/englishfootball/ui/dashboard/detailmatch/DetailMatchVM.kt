package xyz.rakalabs.englishfootball.ui.dashboard.detailmatch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.rakalabs.englishfootball.data.model.MatchResponse
import xyz.rakalabs.englishfootball.data.remote.APIResponse
import xyz.rakalabs.englishfootball.data.repository.FootballRepository
import xyz.rakalabs.englishfootball.utils.ViewCallback

class DetailMatchVM(val repository: FootballRepository): ViewModel() {
    lateinit var callbackView: ViewCallback
    var homePicture: MutableLiveData<String> = MutableLiveData()
    var awayPicture: MutableLiveData<String> = MutableLiveData()
    var listMatchResponse: MutableLiveData<List<MatchResponse.Event>> = MutableLiveData()

    fun setListener(viewCallback: ViewCallback) {
        callbackView = viewCallback
    }
    fun fetchDetailTeam(teamId: String, mode: Int) {
        callbackView.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getDetailTeam(teamId)
            when (response) {
                is APIResponse.Success -> {
                    callbackView.hideLoading()
                    if (mode == 1)
                        homePicture.postValue(response.body?.teams?.first()?.strTeamBadge)
                    else
                        awayPicture.postValue(response.body?.teams?.first()?.strTeamBadge)
                }
                is APIResponse.Error -> {
                    callbackView.hideLoading()
                    Log.e("error", "error load list matchResponse")
                }
            }
        }
    }

    fun fetchLastMatch(event: String) {
        callbackView.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getDetailMatch(event)
            when (response) {
                is APIResponse.Success -> {
                    callbackView.hideLoading()
                    listMatchResponse.postValue(response.body?.events)
                }
                is APIResponse.Error -> {
                    callbackView.hideLoading()
                    Log.e("error", "error load list matchResponse")
                }
            }
        }
    }
}