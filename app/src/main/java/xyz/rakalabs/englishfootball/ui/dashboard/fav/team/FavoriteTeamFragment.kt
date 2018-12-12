package xyz.rakalabs.englishfootball.ui.dashboard.fav.team


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.databinding.FragmentFavoriteTeamBinding
import xyz.rakalabs.englishfootball.utils.ViewCallback

class FavoriteTeamFragment : Fragment(), ViewCallback, SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        viewModel.fetchFavoriteTeam()
    }

    override fun showLoading() {
        binding.refreshTeam.isRefreshing = true
    }

    override fun hideLoading() {
        binding.refreshTeam.isRefreshing = false
    }

    lateinit var binding: FragmentFavoriteTeamBinding
    private val viewModel: FavoriteTeamVM by viewModel()
    private val adapter: FavoriteTeamAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteTeamBinding.bind(inflater.inflate(R.layout.fragment_favorite_team, container, false))
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.favoriteVM = viewModel
        binding.refreshTeam.setOnRefreshListener(this)

        with(binding.matchListTeam) {
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            adapter = this@FavoriteTeamFragment.adapter
            viewModel.listFavTeam.observe(this@FavoriteTeamFragment, Observer {
                this@FavoriteTeamFragment.adapter.teamList = it
            })
        }

        viewModel.fetchFavoriteTeam()
    }
}
