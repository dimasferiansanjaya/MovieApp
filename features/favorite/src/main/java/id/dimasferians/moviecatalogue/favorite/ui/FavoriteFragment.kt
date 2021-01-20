package id.dimasferians.moviecatalogue.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import id.dimasferians.moviecatalogue.R
import id.dimasferians.moviecatalogue.core.utils.viewBindings
import id.dimasferians.moviecatalogue.favorite.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding by viewBindings()

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
        binding.run {
            viewPagerFavorite.adapter =
                FavoritePagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
            TabLayoutMediator(tabLayout, viewPagerFavorite) { tab, position ->
                tab.text = resources.getString(tabTitles[position])
            }.attach()
        }
    }

}