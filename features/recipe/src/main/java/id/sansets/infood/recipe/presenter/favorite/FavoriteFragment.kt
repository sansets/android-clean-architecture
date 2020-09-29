package id.sansets.infood.recipe.presenter.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import id.sansets.infood.InFoodApplication
import id.sansets.infood.core.data.Resource
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.model.Recipe
import id.sansets.infood.core.util.autoCleared
import id.sansets.infood.core.util.setAppBarElevationListener
import id.sansets.infood.recipe.databinding.FragmentFavoriteBinding
import id.sansets.infood.recipe.di.DaggerRecipeComponent
import id.sansets.infood.recipe.presenter.filter.RecipeFilterFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import id.sansets.infood.core.R as coreR

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@ExperimentalCoroutinesApi
@FlowPreview
class FavoriteFragment : Fragment(), FavoriteActionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: FavoriteViewModel by viewModels { viewModelFactory }

    private var binding by autoCleared<FragmentFavoriteBinding>()

    private val recipeSectionAdapter: FavoriteSectionAdapter by lazy {
        FavoriteSectionAdapter(this)
    }

    private val filterDialog: RecipeFilterFragment by lazy {
        RecipeFilterFragment().apply {
            onFilterApply = { viewModel.updateFoodCategoriesFilter(it) }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
        initData()
    }

    override fun onRefreshRecipes() {
        viewModel.getRecipes(
            query = viewModel.query.value?.query,
            filterFoodCategories = viewModel.query.value?.filterFoodCategories
        )
    }

    override fun onOpenRecipeFilter() {
        filterDialog.setSelectedFoodCategories(
            viewModel.filterFoodCategoryList.value ?: ArrayList()
        )
        filterDialog.show(childFragmentManager, RecipeFilterFragment.TAG)
    }

    override fun onRemoveFilterFoodCategory(foodCategory: FoodCategory) {
        viewModel.removeFoodCategoryFilter(foodCategory)
    }

    override fun onOpenRecipeDetail(recipe: Recipe) {
        val action = FavoriteFragmentDirections
            .actionFavoriteFragmentToRecipeDetailFragment(recipe)
        findNavController().navigate(action)
    }

    override fun onUpdateFavoriteRecipe(favorite: Boolean, recipe: Recipe) {
        viewModel.setFavorite(recipe, favorite)
    }

    private fun initDependencyInjection() {
        DaggerRecipeComponent.factory()
            .create(InFoodApplication.coreComponent)
            .inject(this)
    }

    private fun initView() {
        binding.root.findViewById<TextView>(androidx.appcompat.R.id.search_src_text).typeface =
            ResourcesCompat.getFont(requireContext(), coreR.font.raleway_medium)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.searchView.setOnQueryTextListener(binding.searchView.onQueryTextListener())
        binding.btnFilter.setOnClickListener { onOpenRecipeFilter() }
        binding.swipeRefresh.apply {
            setColorSchemeColors(ContextCompat.getColor(context, coreR.color.colorPrimary))
            setOnRefreshListener { onRefreshRecipes() }
        }
        binding.svRecipes.setAppBarElevationListener(binding.appBar)
        binding.rvRecipes.adapter = recipeSectionAdapter
    }

    private fun initObserver() {
        viewModel.recipes.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
                is Resource.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    recipeSectionAdapter.setRecipeList(it.data)
                }
                is Resource.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        })

        viewModel.filterFoodCategoryList.observe(viewLifecycleOwner, {
            recipeSectionAdapter.setFilterFoodCategories(it)
            viewModel.getRecipes(binding.searchView.query.toString(), it)
        })
    }

    private fun SearchView.onQueryTextListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getRecipes(query, viewModel.query.value?.filterFoodCategories)
                clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.getRecipes(newText, viewModel.query.value?.filterFoodCategories)
                }
                return true
            }
        }
    }

    private fun initData() {
        viewModel.getRecipes()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}