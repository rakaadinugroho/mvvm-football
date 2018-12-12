package xyz.rakalabs.englishfootball.ui.dashboard.detailteam.listplayer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.rakalabs.englishfootball.data.model.PlayerResponse
import xyz.rakalabs.englishfootball.data.remote.APIResponse
import xyz.rakalabs.englishfootball.data.repository.FootballRepository
import xyz.rakalabs.englishfootball.utils.ViewCallback

class ListPlayerVM(val repository: FootballRepository): ViewModel() {
    private val tag = ListPlayerVM::class.java.simpleName
    lateinit var callbackView: ViewCallback
    var listPlayer: MutableLiveData<List<PlayerResponse.Player>> = MutableLiveData()
    fun setListener(viewCallback: ViewCallback) {
        callbackView = viewCallback
    }
    fun fetchAllPlayer(teamId: String) {
        callbackView.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getListPlayer(teamId)
            when (response) {
                is APIResponse.Success -> {
                    callbackView.hideLoading()
                    listPlayer.postValue(response.body?.player)
                }
                is APIResponse.Error -> {
                    callbackView.hideLoading()
                    Log.e(tag, "error load data")
                }
            }
        }
    }
}