package id.sansets.infood.recipe.presenter.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.sansets.infood.core.domain.model.Recipe
import id.sansets.infood.core.util.UrlHelper.getCoverUrl
import id.sansets.infood.recipe.R
import id.sansets.infood.recipe.databinding.ItemRecipeBinding
import id.sansets.infood.core.R as coreR

class RecipeListAdapter : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {

    var onOpenRecipeDetail: ((recipe: Recipe) -> Unit)? = null
    var onUpdateArchivedRecipe: ((archive: Boolean, recipe: Recipe?) -> Unit)? = null

    private val recipes = ArrayList<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder =
        RecipeListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        )

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) =
        holder.onBind(recipes[position])

    fun setRecipeList(recipes: List<Recipe>?) {
        recipes?.let {
            this.recipes.apply {
                clear()
                addAll(it)
            }
        }

        notifyDataSetChanged()
    }

    inner class RecipeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemRecipeBinding.bind(itemView)
        private var isFavorite = false

        fun onBind(recipe: Recipe) {
            isFavorite = recipe.isFavorite

            binding.layoutRecipe.setOnClickListener { onOpenRecipeDetail?.invoke(recipe) }
            binding.imgCover.contentDescription = recipe.title
            binding.tvTitle.text = recipe.title
            binding.tvAuthor.text = recipe.sourceName
            binding.btnFavorite.apply {
                setIconResource(getArchiveIcon(isFavorite))
                setIconTintResource(getArchiveIconTint(isFavorite))
                setOnClickListener {
                    isFavorite = !isFavorite
                    onUpdateArchivedRecipe?.invoke(isFavorite, recipe)
                    setIconResource(getArchiveIcon(isFavorite))
                    setIconTintResource(getArchiveIconTint(isFavorite))
                }
            }

            Picasso.get()
                .load(getCoverUrl(recipe.id.toString(), recipe.imageType))
                .placeholder(coreR.drawable.ic_placeholder_food)
                .error(coreR.drawable.ic_placeholder_food)
                .into(binding.imgCover)
        }

        private fun getArchiveIcon(isFavorite: Boolean): Int {
            return if (isFavorite) coreR.drawable.ic_baseline_favorite_24
            else coreR.drawable.ic_outline_favorite_24
        }

        private fun getArchiveIconTint(isFavorite: Boolean): Int {
            return if (isFavorite) coreR.color.colorPrimary
            else coreR.color.colorBoulder
        }
    }
}