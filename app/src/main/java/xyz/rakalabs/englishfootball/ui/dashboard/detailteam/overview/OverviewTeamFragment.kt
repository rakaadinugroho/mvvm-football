package xyz.rakalabs.englishfootball.ui.dashboard.detailteam.overview


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import xyz.rakalabs.englishfootball.R
import xyz.rakalabs.englishfootball.databinding.FragmentOverviewTeamBinding

class OverviewTeamFragment : Fragment() {
    lateinit var binding: FragmentOverviewTeamBinding
    companion object {
        private const val argument = "ARG_LIST"
        fun newInstance(overview: String): OverviewTeamFragment {
            val args = Bundle()
            args.putSerializable(argument, overview)
            val fragment = OverviewTeamFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOverviewTeamBinding.bind(inflater.inflate(R.layout.fragment_overview_team, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.teamOverview.text = arguments?.getString(argument, "-")
    }


}
