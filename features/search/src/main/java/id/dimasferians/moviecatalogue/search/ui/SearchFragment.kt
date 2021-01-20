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
import com.google.android.material.tabs.TabLayoutMediator
import id.dimasferians.moviecatalogue.core.R
import id.dimasferians.moviecatalogue.core.utils.provideCoreComponent
import id.dimasferians.moviecatalogue.core.utils.viewBindings
import id.dimasferians.moviecatalogue.search.databinding.FragmentSearchBinding
import id.dimasferians.moviecatalogue.search.di.DaggerSearchComponent
import javax.inject.Inject

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding by viewBindings()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SearchViewModel by activityViewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
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
        binding.run {
            viewPagerSearch.adapter =
                SearchPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
            TabLayoutMediator(tabLayout, viewPagerSearch) { tab, position ->
                tab.text = resources.getString(tabTitles[position])
            }.attach()
        }
    }

    private fun initAction() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.search(query)
                }
                binding.searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.clearData()
                } else {
                    viewModel.setIsEmptyQuery(false)
                }
                return false
            }
        })
    }

}