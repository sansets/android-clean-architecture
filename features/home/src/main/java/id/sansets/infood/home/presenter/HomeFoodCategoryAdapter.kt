package id.sansets.infood.home.presenter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.home.R
import id.sansets.infood.home.databinding.ItemFoodCategoryBinding
import id.sansets.infood.core.R as coreR

class HomeFoodCategoryAdapter(
    private val actionListener: HomeActionListener
) : RecyclerView.Adapter<HomeFoodCategoryAdapter.FoodCategoryViewHolder>() {

    private val foodCategories = ArrayList<FoodCategory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCategoryViewHolder {
        return FoodCategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_food_category, parent, false)
        )
    }

    override fun getItemCount(): Int = foodCategories.size

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: FoodCategoryViewHolder, position: Int) {
        holder.onBind(foodCategories[position])
    }

    fun setFoodCategories(foodCategories: List<FoodCategory>?) {
        foodCategories?.let {
            this.foodCategories.apply {
                clear()
                addAll(it)
            }
            notifyDataSetChanged()
        }
    }

    inner class FoodCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemFoodCategoryBinding.bind(itemView)

        fun onBind(foodCategory: FoodCategory?) {
            val context = binding.btnFoodCategory.context

            binding.btnFoodCategory.apply {
                text = foodCategory?.title
                compoundDrawablePadding = context.resources.getDimension(coreR.dimen.default_compound_drawable_padding).toInt()
                setOnClickListener { actionListener.onFoodCategoryClicked(foodCategory) }
            }

            Picasso.get()
                .load(foodCategory?.iconUrl)
                .into(object : com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                        val drawable = createScaledDrawable(context, bitmap)
                        binding.btnFoodCategory.setCategoryIcon(drawable)
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                        // Nothing to do
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        // Nothing to do
                    }
                })
        }

        private fun createScaledDrawable(context: Context, bitmap: Bitmap): Drawable {
            return BitmapDrawable(
                context.resources,
                Bitmap.createScaledBitmap(
                    bitmap,
                    context.resources.getDimension(coreR.dimen.default_icon_category_width).toInt(),
                    context.resources.getDimension(coreR.dimen.default_icon_category_height).toInt(),
                    true
                )
            )
        }

        private fun Button.setCategoryIcon(icon: Drawable?) {
            setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null)
        }
    }
}