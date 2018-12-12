package xyz.rakalabs.englishfootball.ui.dashboard.fav.team

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import xyz.rakalabs.englishfootball.data.model.FavoriteTeam
import xyz.rakalabs.englishfootball.data.repository.database
import xyz.rakalabs.englishfootball.utils.ViewCallback

class FavoriteTeamVM(val context: Context): ViewModel() {
    private val tag = "favorite team"
    lateinit var callbackView: ViewCallback
    var listFavTeam: MutableLiveData<List<FavoriteTeam>> = MutableLiveData()
    fun setListener(viewCallback: ViewCallback) {
        callbackView = viewCallback
    }

    fun fetchFavoriteTeam() {
        callbackView.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            context.database.use {
                val result = select(FavoriteTeam.TABLE_FAV_TEAM)
                val favorite = result.parseList(classParser<FavoriteTeam>())
                listFavTeam.postValue(favorite)
                Log.e(tag, Gson().toJson(favorite))
            }
            callbackView.hideLoading()
        }
    }
}