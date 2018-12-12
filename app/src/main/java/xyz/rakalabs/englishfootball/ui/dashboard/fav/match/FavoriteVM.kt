package xyz.rakalabs.englishfootball.ui.dashboard.fav.match

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
import xyz.rakalabs.englishfootball.data.model.Favorite
import xyz.rakalabs.englishfootball.data.repository.database
import xyz.rakalabs.englishfootball.utils.ViewCallback

class FavoriteVM(val context: Context) : ViewModel() {
    val tag = FavoriteVM::class.java.simpleName
    private lateinit var callbackView: ViewCallback
    var listMatchResponse: MutableLiveData<List<Favorite>> = MutableLiveData()
    fun setListener(viewCallback: ViewCallback) {
        callbackView = viewCallback
    }

    fun fetchFavorite() {
        callbackView.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            context.database.use {
                val result = select(Favorite.TABLE_FAVORITE)
                val favorite = result.parseList(classParser<Favorite>())
                listMatchResponse.postValue(favorite)
                Log.e(tag, Gson().toJson(favorite))
            }
            callbackView.hideLoading()
        }
    }
}