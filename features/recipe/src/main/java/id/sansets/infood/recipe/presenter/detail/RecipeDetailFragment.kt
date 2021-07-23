package id.sansets.infood.recipe.presenter.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.Chip
import id.sansets.infood.InFoodApplication
import id.sansets.infood.core.data.Resource
import id.sansets.infood.core.domain.model.Recipe
import id.sansets.infood.core.util.UrlHelper
import id.sansets.infood.core.util.autoCleared
import id.sansets.infood.core.util.loadRecipeImage
import id.sansets.infood.core.util.setTextFromHtml
import id.sansets.infood.recipe.R
import id.sansets.infood.recipe.databinding.FragmentRecipeDetailBinding
import id.sansets.infood.recipe.di.DaggerRecipeComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import kotlin.math.abs
import id.sansets.infood.core.R as coreR

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@ExperimentalCoroutinesApi
@FlowPreview
class RecipeDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: RecipeDetailViewModel by viewModels { viewModelFactory }

    private var binding by autoCleared<FragmentRecipeDetailBinding>()
    private val args: RecipeDetailFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAppBarScrollListener()
        initView()
        initObserver()
        initData()
    }

    private fun initDependencyInjection() {
        DaggerRecipeComponent.factory()
            .create(InFoodApplication.coreComponent)
            .inject(this)
    }

    private fun initView() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.toolbar.menu[0].setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_favorite -> {
                    args.StringArgumentRecipe?.let { recipe ->
                        viewModel.setFavorite(recipe)
                        findNavController().previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(RESULT_IS_FAVORITE_CHANGED, true)
                    }
                }
            }
            return@setOnMenuItemClickListener true
        }

        binding.tvSummary.setOnClickListener {
            binding.tvSummary.toggle()
        }
    }

    private fun initObserver() {
        viewModel.isFavorite.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    setFavoriteIcon(it.data ?: false)
                }
                is Resource.Error -> {
                    setFavoriteIcon(it.data ?: false)
                }
            }
        })
    }

    private fun initData() {
        args.StringArgumentRecipe?.let {
            showData(it)
            viewModel.checkFavorite(it)
        }
    }

    private fun showData(recipe: Recipe) {
        binding.tvTitle.text = recipe.title
        binding.tvSummary.setTextFromHtml(recipe.summary)
        binding.tvIngredients.text = viewModel.getFormattedIngredients(recipe.ingredients)
        binding.tvSteps.text = viewModel.getFormattedSteps(recipe.steps)
        binding.imgBackdrop.loadRecipeImage(
            UrlHelper.getBackdropUrl(
                id = recipe.id.toString(),
                imageType = recipe.imageType
            )
        )

        setDishTypesChip(recipe.dishTypes)

        if (recipe.ingredients.isNullOrEmpty()) {
            binding.tvHintIngredients.visibility = View.GONE
            binding.tvIngredients.visibility = View.GONE
        }

        if (recipe.steps.isNullOrEmpty()) {
            binding.tvHintSteps.visibility = View.GONE
            binding.tvSteps.visibility = View.GONE
        }
    }

    private fun setAppBarScrollListener() {
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
            if (abs(i) - appBarLayout.totalScrollRange == 0) {
                binding.toolbar.setTitleTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.black
                    )
                )
            } else {
                binding.toolbar.setTitleTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.transparent
                    )
                )
            }
        })
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.toolbar.menu?.get(0)?.icon =
                ContextCompat
                    .getDrawable(requireContext(), coreR.drawable.ic_baseline_favorite_24)?.apply {
                        setTint(
                            ContextCompat.getColor(
                                requireContext(),
                                coreR.color.colorPrimary
                            )
                        )
                    }
        } else {
            binding.toolbar.menu?.get(0)?.icon =
                ContextCompat
                    .getDrawable(requireContext(), coreR.drawable.ic_outline_favorite_24)?.apply {
                        setTint(ContextCompat.getColor(requireContext(), android.R.color.black))
                    }
        }
    }

    private fun setDishTypesChip(dishTypes: List<String>) {
        dishTypes.forEach {
            val inflater = LayoutInflater.from(binding.chipGroupTypes.context)
            val layoutRes = R.layout.item_food_category
            val parent = binding.chipGroupTypes
            val chip = (inflater.inflate(layoutRes, parent, false) as Chip).apply {
                text = it
                isCheckable = false
                isClickable = false
            }

            binding.chipGroupTypes.addView(chip)
        }
    }

    companion object {
        const val RESULT_IS_FAVORITE_CHANGED = "is_favorite_changed"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RecipeDetailFragment.
         */
        @JvmStatic
        fun newInstance() = RecipeDetailFragment()
    }
}