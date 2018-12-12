package xyz.rakalabs.englishfootball.ui.dashboard.listmatch


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.data.model.LeagueResponse
import xyz.rakalabs.englishfootball.databinding.FragmentMatchListBinding
import xyz.rakalabs.englishfootball.utils.ViewCallback

class MatchListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, ViewCallback {
    override fun hideLoading() {
        binding.refresh.isRefreshing = false
    }

    override fun showLoading() {
        binding.refresh.isRefreshing = true
    }

    override fun onRefresh() {
        loadData()
    }

    private var listOfLeagues: MutableList<LeagueResponse.League> = mutableListOf()
    lateinit var binding: FragmentMatchListBinding
    private val viewModel: MatchListVM by viewModel()
    private val adapter: MatchAdapter by inject()
    companion object {
        private const val argument = "ARG_LIST"
        fun newInstance(scheduler: String): MatchListFragment {
            val args = Bundle()
            args.putSerializable(argument, scheduler)
            val fragment = MatchListFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMatchListBinding.bind(inflater.inflate(R.layout.fragment_match_list, container, false))
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setListener(this)
        viewModel.fetchLeagues()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.matchListVM = viewModel
        adapter.isNextMatch = arguments?.getString(argument, "1")!!.contentEquals("1")
        binding.refresh.setOnRefreshListener(this)
        with(binding.matchList) {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = this@MatchListFragment.adapter
            addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
            viewModel.listMatchResponse.observe(this@MatchListFragment, Observer {
                this@MatchListFragment.adapter.matchResponseList = it
            })
        }

        val adapterSort = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listOfLeagues)
        adapterSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.matchSort.apply {
            adapter = adapterSort
            onItemSelectedListener {
                onItemSelected { _, _, _, _ ->
                    loadData()
                }
            }
        }

        viewModel.listLeague.observe(this, Observer {
            listOfLeagues.clear()
            listOfLeagues.addAll(it)
            adapterSort.notifyDataSetChanged()
        })

        loadData()
    }

    private fun loadData() {
        if (arguments?.getString(argument) == "1") {
            if (listOfLeagues.isEmpty()) viewModel.fetchLastMatch("4328")
            else viewModel.fetchLastMatch(listOfLeagues[binding.matchSort.selectedItemPosition].idLeague)
        } else {
            if (listOfLeagues.isEmpty()) viewModel.fetchNextMatch("4328")
            else viewModel.fetchNextMatch(listOfLeagues[binding.matchSort.selectedItemPosition].idLeague)
        }
    }

}
