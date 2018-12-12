package xyz.rakalabs.englishfootball.di

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import xyz.rakalabs.englishfootball.data.remote.FootballApiClient
import xyz.rakalabs.englishfootball.data.repository.FootballRepository
import xyz.rakalabs.englishfootball.ui.dashboard.fav.match.FavoriteAdater
import xyz.rakalabs.englishfootball.ui.dashboard.fav.match.FavoriteVM
import xyz.rakalabs.englishfootball.ui.dashboard.listmatch.MatchAdapter
import xyz.rakalabs.englishfootball.ui.dashboard.listmatch.MatchListVM
import xyz.rakalabs.englishfootball.ui.dashboard.listteam.TeamAdapter
import xyz.rakalabs.englishfootball.ui.dashboard.listteam.TeamListVM
import xyz.rakalabs.englishfootball.ui.dashboard.detailmatch.DetailMatchVM
import xyz.rakalabs.englishfootball.ui.dashboard.detailteam.listplayer.ListPlayerVM
import xyz.rakalabs.englishfootball.ui.dashboard.detailteam.listplayer.PlayerAdapter
import xyz.rakalabs.englishfootball.ui.dashboard.fav.team.FavoriteTeamAdapter
import xyz.rakalabs.englishfootball.ui.dashboard.fav.team.FavoriteTeamVM

val appModule = module {
    single { FootballApiClient.provideApi() }
    factory { FootballRepository(get()) }
    factory { MatchAdapter(get()) }
    factory { FavoriteAdater(get()) }
    factory { TeamAdapter(get()) }
    factory { PlayerAdapter(get()) }
    factory { FavoriteTeamAdapter(get()) }
    viewModel { MatchListVM(get()) }
    viewModel { FavoriteVM(get()) }
    viewModel { FavoriteTeamVM(get()) }
    viewModel { DetailMatchVM(get()) }
    viewModel { TeamListVM(get()) }
    viewModel { ListPlayerVM(get()) }
}