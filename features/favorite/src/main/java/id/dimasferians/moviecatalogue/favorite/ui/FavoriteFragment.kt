package id.dimasferians.moviecatalogue.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.dimasferians.moviecatalogue.R
import id.dimasferians.moviecatalogue.databinding.FragmentFavoriteBinding

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabWithViewPager()
    }

    private fun setupTabWithViewPager() {
        @StringRes
        val tabTitles = intArrayOf(
            R.string.title_movie,
            R.string.title_tv_show
        )
        binding.viewPagerFavorite.adapter = FavoritePagerAdapter(requireActivity())
        TabLayoutMediator(binding.tabLayout, binding.viewPagerFavorite) { tab, position ->
            tab.text = getString(tabTitles[position])
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}