package id.sansets.infood.recipe.presenter.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.model.Recipe
import id.sansets.infood.core.util.gone
import id.sansets.infood.core.util.visible
import id.sansets.infood.recipe.R
import id.sansets.infood.recipe.databinding.ItemSectionFoodCategoriesBinding
import id.sansets.infood.recipe.databinding.ItemSectionRecipesBinding
import id.sansets.infood.core.R as coreR

class FavoriteSectionAdapter(
    private val actionListener: FavoriteActionListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val recipes = ArrayList<Recipe>()
    private val filterFoodCategories = ArrayList<FoodCategory>()

    private var showRecipesProgress = false
    private var showRecipesEmptyMessage = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_section_food_categories -> FoodCategoryViewHolder(
                inflater.inflate(viewType, parent, false), actionListener
            )
            R.layout.item_section_recipes -> RecipeViewHolder(
                inflater.inflate(viewType, parent, false), actionListener
            )
            else -> EmptyViewHolder(
                inflater.inflate(coreR.layout.item_empty, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = 2

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.item_section_food_categories
            1 -> R.layout.item_section_recipes
            else -> coreR.layout.item_empty
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FoodCategoryViewHolder -> holder.onBind(filterFoodCategories)
            is RecipeViewHolder -> holder.onBind(
                recipes,
                showRecipesProgress,
                showRecipesEmptyMessage
            )
        }
    }

    fun setFilterFoodCategories(foodCategories: List<FoodCategory>?) {
        foodCategories?.let {
            this.filterFoodCategories.apply {
                clear()
                addAll(it)
            }
            notifyItemChanged(0)
        }
    }

    fun setRecipeList(recipes: List<Recipe>?) {
        recipes?.let {
            this.recipes.apply {
                clear()
                addAll(it)
            }
        }

        notifyItemChanged(1)
    }

    fun showRecipeListEmptyMessage(show: Boolean) {
        this.showRecipesEmptyMessage = show
    }
}

class FoodCategoryViewHolder(
    itemView: View,
    private val actionListener: FavoriteActionListener
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemSectionFoodCategoriesBinding.bind(itemView)

    fun onBind(foodCategories: List<FoodCategory>?) {
        createFoodCategoryList(foodCategories)
    }

    private fun createFoodCategoryList(foodCategories: List<FoodCategory>?) {
        binding.chipGroupCategory.removeAllViews()

        foodCategories?.forEach { foodCategory ->
            val inflater = LayoutInflater.from(binding.chipGroupCategory.context)
            val layoutRes = R.layout.item_food_category
            val parent = binding.chipGroupCategory
            val chip = (inflater.inflate(layoutRes, parent, false) as Chip).apply {
                text = foodCategory.title
                isClickable = false
                isCloseIconVisible = true
                setOnCloseIconClickListener {
                    actionListener.onRemoveFilterFoodCategory(foodCategory)
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
}

class RecipeViewHolder(
    itemView: View,
    actionListener: FavoriteActionListener
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemSectionRecipesBinding.bind(itemView)
    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter().apply {
            onOpenRecipeDetail = {
                actionListener.onOpenRecipeDetail(it)
            }
            onUpdateArchivedRecipe = { archive, recipe ->
                actionListener.onUpdateFavoriteRecipe(archive, recipe)
            }

            binding.rvRecipes.adapter = this
        }
    }

    fun onBind(
        recipes: List<Recipe>?,
        showProgress: Boolean,
        showEmptyMessage: Boolean
    ) {
        adapter.setRecipeList(recipes)
        showEmptyMessage(showEmptyMessage, showProgress)
    }

    private fun showEmptyMessage(show: Boolean, progressShowing: Boolean) {
        if (show && !progressShowing) {
            binding.tvNotFound.visible()
        } else {
            binding.tvNotFound.gone()
        }
    }
}

class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)