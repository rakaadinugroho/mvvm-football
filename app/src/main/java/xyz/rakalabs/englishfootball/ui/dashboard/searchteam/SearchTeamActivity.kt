package xyz.rakalabs.englishfootball.ui.dashboard.searchteam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.databinding.ActivitySearchTeamBinding
import xyz.rakalabs.englishfootball.ui.dashboard.listteam.TeamAdapter
import xyz.rakalabs.englishfootball.ui.dashboard.listteam.TeamListVM
import xyz.rakalabs.englishfootball.utils.ViewCallback

class SearchTeamActivity : AppCompatActivity(), ViewCallback {
    override fun showLoading() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.progressSearchTeam.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.progressSearchTeam.visibility = View.GONE
        }
    }

    lateinit var binding: ActivitySearchTeamBinding
    private lateinit var searchView: SearchView
    private val viewModel: TeamListVM by viewModel()
    private val adapter: TeamAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_team)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        viewModel.setListener(this)
        binding.searchVM = viewModel
        with(binding.listOfSearchTeam) {
            val manager = LinearLayoutManager(this@SearchTeamActivity)
            layoutManager = manager
            adapter = this@SearchTeamActivity.adapter
            viewModel.listOfTeam.observe(this@SearchTeamActivity, Observer {
                this@SearchTeamActivity.adapter.teamResponseList = it
            })
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        searchView.isIconified = false
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.match_search, menu)
        val search = menu.findItem(R.id.match_search)
        searchView = search.actionView as SearchView
        val closeButton: ImageView = searchView.findViewById(R.id.search_close_btn)
        closeButton.setOnClickListener { finish() }
        search.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                finish()
                return false
            }

        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean{
                Log.e("query-last", query)
                if (query != null) {
                    viewModel.searchTeam(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}
