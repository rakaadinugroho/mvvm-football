package xyz.rakalabs.englishfootball.ui.dashboard.detailteam.listplayer


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
import xyz.rakalabs.englishfootball.databinding.FragmentListPlayerBinding
import xyz.rakalabs.englishfootball.utils.ViewCallback

class ListPlayerFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, ViewCallback {
    override fun showLoading() {
        binding.listPlayerContainer.isRefreshing = true
    }

    override fun hideLoading() {
        binding.listPlayerContainer.isRefreshing = false
    }

    override fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        viewModel.fetchAllPlayer(arguments?.getString(argument)!!)
    }

    lateinit var binding: FragmentListPlayerBinding
    private val viewModel: ListPlayerVM by viewModel()
    private val adapter: PlayerAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setListener(this)
    }
    companion object {
        private const val argument = "ARG_LIST"
        fun newInstance(teamId: String): ListPlayerFragment {
            val args = Bundle()
            args.putSerializable(argument, teamId)
            val fragment = ListPlayerFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListPlayerBinding.bind(inflater.inflate(R.layout.fragment_list_player, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playerVM = viewModel
        binding.listPlayerContainer.setOnRefreshListener(this)
        with(binding.listOfPlayer) {
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            adapter = this@ListPlayerFragment.adapter
            viewModel.listPlayer.observe(this@ListPlayerFragment, Observer {
                this@ListPlayerFragment.adapter.playerResponseList = it
            })
        }
        loadData()
    }


}
