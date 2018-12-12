package xyz.rakalabs.englishfootball.data.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import xyz.rakalabs.englishfootball.data.model.Favorite
import xyz.rakalabs.englishfootball.data.model.FavoriteTeam

class MyDatabaseOpenHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null
        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null){
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.TABLE_FAVORITE)
        db.dropTable(FavoriteTeam.TABLE_FAV_TEAM)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Favorite.TABLE_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.EVENT_ID to TEXT + UNIQUE,
            Favorite.AWAY_ID to TEXT,
            Favorite.AWAY_NAME to TEXT,
            Favorite.AWAY_SCORE to TEXT,
            Favorite.HOME_ID to TEXT,
            Favorite.HOME_NAME to TEXT,
            Favorite.HOME_SCORE to TEXT,
            Favorite.STR_DATE to TEXT
            )

        db.createTable(FavoriteTeam.TABLE_FAV_TEAM, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.COUNTRY to TEXT,
            FavoriteTeam.BADGE to TEXT,
            FavoriteTeam.JERSEY to TEXT,
            FavoriteTeam.LOGO to TEXT,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.FORMED to TEXT,
            FavoriteTeam.STADIUM to TEXT,
            FavoriteTeam.DESCRIPTION to TEXT
        )

    }
}
val Context.database: MyDatabaseOpenHelper
get() = MyDatabaseOpenHelper.getInstance(applicationContext)