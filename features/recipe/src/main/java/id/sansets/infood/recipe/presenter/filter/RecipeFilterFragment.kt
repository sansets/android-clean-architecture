package id.sansets.infood.recipe.presenter.filter

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import id.sansets.infood.InFoodApplication
import id.sansets.infood.core.data.Resource
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.util.autoCleared
import id.sansets.infood.core.util.gone
import id.sansets.infood.core.util.visible
import id.sansets.infood.recipe.R
import id.sansets.infood.recipe.databinding.FragmentRecipeFilterBinding
import id.sansets.infood.recipe.di.DaggerRecipeComponent
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeFilterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeFilterFragment : BottomSheetDialogFragment() {

    var onFilterApply: ((foodCategories: ArrayList<FoodCategory>) -> Unit)? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: RecipeFilterViewModel by viewModels { viewModelFactory }

    private var binding by autoCleared<FragmentRecipeFilterBinding>()

    private var selectedFoodCategories = ArrayList<FoodCategory>()
    private var selectedFoodCategoriesChanged = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
        initData()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (selectedFoodCategoriesChanged) onFilterApply?.invoke(selectedFoodCategories)
    }

    private fun initDependencyInjection() {
        DaggerRecipeComponent.factory()
            .create(InFoodApplication.coreComponent)
            .inject(this)
    }

    fun setSelectedFoodCategories(foodCategories: ArrayList<FoodCategory>) {
        selectedFoodCategories = foodCategories
    }

    private fun initView() {
        binding.toolbar.setNavigationOnClickListener { dismiss() }
    }

    private fun initObserver() {
        viewModel.foodCategories.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    createFoodCategoryList(it.data)
                }
                is Resource.Error -> {

                }
            }
        })
    }

    private fun initData() {
        selectedFoodCategoriesChanged = false
    }

    private fun createFoodCategoryList(foodCategories: List<FoodCategory>?) {
        binding.chipGroupCategory.clearCheck()
        binding.chipGroupCategory.removeAllViews()

        foodCategories?.forEach { foodCategory ->
            val inflater = LayoutInflater.from(binding.chipGroupCategory.context)
            val layoutRes = R.layout.item_food_category
            val parent = binding.chipGroupCategory
            val chip = (inflater.inflate(layoutRes, parent, false) as Chip).apply {
                text = foodCategory.title
                isChecked = selectedFoodCategories.any { it.id == foodCategory.id }
                isSelected = selectedFoodCategories.any { it.id == foodCategory.id }
                setOnClickListener {
                    setSelectedFoodCategory(!isSelected, foodCategory)
                    isSelected = !isSelected
                }
            }

            binding.chipGroupCategory.addView(chip)
        }

        if (foodCategories.isNullOrEmpty()) {
            binding.chipGroupCategory.gone()
        } else {
            binding.chipGroupCategory.visible()
        }
    }

    private fun setSelectedFoodCategory(isChecked: Boolean, foodCategory: FoodCategory) {
        if (isChecked) {
            if (!selectedFoodCategories.any { it.id == foodCategory.id }) {
                selectedFoodCategories.add(foodCategory)
                selectedFoodCategoriesChanged = true
            }
        } else {
            if (selectedFoodCategories.any { it.id == foodCategory.id }) {
                selectedFoodCategoriesChanged = true
            }
            selectedFoodCategories.removeAll { it.id == foodCategory.id }
        }
    }

    companion object {
        const val TAG = "recipe_filter"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RecipeFilterFragment.
         */
        @JvmStatic
        fun newInstance() = RecipeFilterFragment()
    }
}