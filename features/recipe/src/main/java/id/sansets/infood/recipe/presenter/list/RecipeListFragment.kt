package id.sansets.infood.recipe.presenter.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.sansets.infood.core.util.autoCleared
import id.sansets.infood.recipe.databinding.FragmentRecipeListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeListFragment : Fragment() {

    private var binding by autoCleared<FragmentRecipeListBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RecipeFragment.
         */
        @JvmStatic
        fun newInstance() = RecipeListFragment()
    }
}