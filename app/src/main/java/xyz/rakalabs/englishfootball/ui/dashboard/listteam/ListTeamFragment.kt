package xyz.rakalabs.englishfootball.ui.dashboard.listteam


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.data.model.LeagueResponse
import xyz.rakalabs.englishfootball.data.model.TeamResponse
import xyz.rakalabs.englishfootball.databinding.FragmentListTeamBinding
import xyz.rakalabs.englishfootball.ui.dashboard.DasboardActivity
import xyz.rakalabs.englishfootball.ui.dashboard.searchmatch.SearchMatchActivity
import xyz.rakalabs.englishfootball.ui.dashboard.searchteam.SearchTeamActivity
import xyz.rakalabs.englishfootball.utils.ViewCallback

class ListTeamFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, ViewCallback {
    override fun showLoading() {
        binding.teamContainer.isRefreshing = true
    }

    override fun hideLoading() {
        binding.teamContainer.isRefreshing = false
    }

    override fun onRefresh() {
        loadData()
    }

    private var listOfleague: MutableList<LeagueResponse.League> = mutableListOf()
    lateinit var binding: FragmentListTeamBinding
    private val viewModel: TeamListVM by viewModel()
    private val adapter: TeamAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.setListener(this)
        viewModel.fetchLeagues()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListTeamBinding.bind(inflater.inflate(R.layout.fragment_list_team, container, false))
        val thisActivity = activity as DasboardActivity
        thisActivity.setSupportActionBar(binding.toolbar)
        thisActivity.actionBar?.title = "List Teams"
        return binding.root
    }

    private fun loadData() {
        if (listOfleague.isEmpty()) viewModel.fetchAllTeamInLeague("4328")
        else viewModel.fetchAllTeamInLeague(listOfleague[binding.sortLeague.selectedItemPosition].idLeague)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listTeamVM = viewModel
        binding.teamContainer.setOnRefreshListener(this)
        with(binding.listOfTeam) {
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            adapter = this@ListTeamFragment.adapter
            viewModel.listOfTeam.observe(this@ListTeamFragment, Observer {
                this@ListTeamFragment.adapter.teamResponseList = it
            })
        }
        val adapterSort = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listOfleague)
        adapterSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sortLeague.apply {
            adapter = adapterSort
            onItemSelectedListener {
                onItemSelected { _, _, _, _ ->
                    loadData()
                }
            }
        }
        viewModel.listLeague.observe(this, Observer {
            listOfleague.clear()
            listOfleague.addAll(it)
            adapterSort.notifyDataSetChanged()
        })

        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.match_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.match_search_action -> {
                val intent = Intent(context, SearchTeamActivity::class.java)
                startActivity(intent)
            }
            else -> {

            }
        }
        return true
    }
}
