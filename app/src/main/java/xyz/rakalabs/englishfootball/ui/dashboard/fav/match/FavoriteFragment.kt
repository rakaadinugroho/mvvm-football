package xyz.rakalabs.englishfootball.ui.dashboard.fav.match


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.databinding.FragmentFavoriteBinding
import xyz.rakalabs.englishfootball.utils.ViewCallback

class FavoriteFragment : Fragment(), ViewCallback, SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        viewModel.fetchFavorite()
    }

    override fun showLoading() {
        binding.refresh.isRefreshing = true
    }

    override fun hideLoading() {
        binding.refresh.isRefreshing = false
    }

    lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteVM by viewModel()
    private val adapter: FavoriteAdater by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.bind(inflater.inflate(R.layout.fragment_favorite, container, false))
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.favoriteVM = viewModel
        binding.refresh.setOnRefreshListener(this)

        with(binding.matchList) {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = this@FavoriteFragment.adapter
            addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
            viewModel.listMatchResponse.observe(this@FavoriteFragment, Observer {
                this@FavoriteFragment.adapter.matchResponseList = it
            })
        }

        viewModel.fetchFavorite()
    }
}
