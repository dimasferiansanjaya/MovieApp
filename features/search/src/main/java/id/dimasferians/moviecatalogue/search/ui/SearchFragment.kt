package id.dimasferians.moviecatalogue.search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import id.dimasferians.moviecatalogue.core.R
import id.dimasferians.moviecatalogue.core.utils.provideCoreComponent
import id.dimasferians.moviecatalogue.search.databinding.FragmentSearchBinding
import id.dimasferians.moviecatalogue.search.di.DaggerSearchComponent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SearchViewModel by activityViewModels { viewModelFactory }

    private var searchJob: Job? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabWithViewPager()
        initAction()
    }

    private fun initDependencyInjection() {
        DaggerSearchComponent.factory()
            .create(provideCoreComponent())
            .inject(this)
    }

    private fun setupTabWithViewPager() {
        @StringRes
        val tabTitles = intArrayOf(
            R.string.title_movie,
            R.string.title_tv_show
        )
        binding.viewPagerSearch.adapter = SearchPagerAdapter(requireActivity())
        TabLayoutMediator(binding.tabLayout, binding.viewPagerSearch) { tab, position ->
            tab.text = getString(tabTitles[position])
        }.attach()
    }

    private fun initAction() {
        binding.searchView.setOnQueryTextListener(this)
    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            delay(300L)
            viewModel.search(query)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            search(query)
        }
        binding.searchView.clearFocus()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            if (newText.isEmpty()) {
                searchJob?.cancel()
                viewModel.setIsEmptyQuery(true)
                viewModel.clearData()
            } else {
                search(newText)
                viewModel.setIsEmptyQuery(false)
            }
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}