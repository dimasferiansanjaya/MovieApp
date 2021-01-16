package id.dimasferians.moviecatalogue.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import id.dimasferians.moviecatalogue.R
import id.dimasferians.moviecatalogue.core.utils.autoCleared
import id.dimasferians.moviecatalogue.favorite.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var binding by autoCleared<FragmentFavoriteBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
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

    override fun onStop() {
        super.onStop()
        binding.viewPagerFavorite.adapter = null
    }



}