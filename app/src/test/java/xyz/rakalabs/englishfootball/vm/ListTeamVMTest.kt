package xyz.rakalabs.englishfootball.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import xyz.rakalabs.englishfootball.data.repository.FootballRepository
import xyz.rakalabs.englishfootball.di.appModule
import xyz.rakalabs.englishfootball.ui.dashboard.listmatch.MatchListVM
import xyz.rakalabs.englishfootball.utils.ViewCallback

class ListTeamVMTest: KoinTest {
    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val repository = mock(FootballRepository::class.java)
    private val viewModel = MatchListVM(repository)
    @Before
    fun setupKoin(){
        startKoin(listOf(appModule))
        viewModel.callbackView = object : ViewCallback {
            override fun showLoading() {

            }

            override fun hideLoading() {

            }

        }
    }

    @After
    fun shutdownKoin(){
        stopKoin()
    }

    @Test
    fun testIfNull() {
        assertThat(viewModel.repository, CoreMatchers.notNullValue())
    }

    @Test
    fun changeWhenObserved() {
        viewModel.fetchLastMatch("4328")
        viewModel.listMatchResponse
        runBlocking { verify(repository).getLastMatch("4328") }
    }

}