package xyz.rakalabs.englishfootball

import android.app.Application
import org.koin.android.ext.android.startKoin
import xyz.rakalabs.englishfootball.di.appModule

class EnglishFootballApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}