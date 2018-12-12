package xyz.rakalabs.englishfootball.ui.dashboard.searchmatch

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
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.databinding.ActivitySearchMatchBinding
import xyz.rakalabs.englishfootball.ui.dashboard.listmatch.MatchAdapter
import xyz.rakalabs.englishfootball.ui.dashboard.listmatch.MatchListVM
import xyz.rakalabs.englishfootball.utils.ViewCallback

class SearchMatchActivity : AppCompatActivity(), ViewCallback {
    override fun showLoading() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.progressSearch.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.progressSearch.visibility = View.GONE
        }
    }

    lateinit var binding: ActivitySearchMatchBinding
    private lateinit var searchView: SearchView
    private val viewModel: MatchListVM by viewModel()
    private val adapter: MatchAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_match)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        viewModel.setListener(this)
        binding.searchVM = viewModel
        adapter.isNextMatch = false
        with(binding.listOfSearch) {
            val manager = LinearLayoutManager(this@SearchMatchActivity)
            layoutManager = manager
            adapter = this@SearchMatchActivity.adapter
            viewModel.listMatchResponse.observe(this@SearchMatchActivity, Observer {
                Log.e("data", Gson().toJson(it))
                this@SearchMatchActivity.adapter.matchResponseList = it
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
                    viewModel.searchMatch(query)
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
