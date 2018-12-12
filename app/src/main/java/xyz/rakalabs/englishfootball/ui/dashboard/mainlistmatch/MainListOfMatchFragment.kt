package xyz.rakalabs.englishfootball.ui.dashboard.mainlistmatch


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast

import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.databinding.FragmentMainListOfMatchBinding
import xyz.rakalabs.englishfootball.ui.dashboard.DasboardActivity
import xyz.rakalabs.englishfootball.ui.dashboard.searchmatch.SearchMatchActivity

class MainListOfMatchFragment : Fragment() {
    lateinit var binding: FragmentMainListOfMatchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainListOfMatchBinding.bind(inflater.inflate(R.layout.fragment_main_list_of_match, container, false))
        val thisActivity = activity as DasboardActivity
        thisActivity.setSupportActionBar(binding.toolbar)
        thisActivity.actionBar?.title = "List Match"
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainPagerAdapter = MainPagerAdapter(childFragmentManager)
        binding.mainListOfMatch.adapter = mainPagerAdapter
        binding.tabs.setupWithViewPager(binding.mainListOfMatch)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.match_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.match_search_action -> {
                val intent = Intent(context, SearchMatchActivity::class.java)
                startActivity(intent)
            }
            else -> {

            }
        }
        return true
    }

}
