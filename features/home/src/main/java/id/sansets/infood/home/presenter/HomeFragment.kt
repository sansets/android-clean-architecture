package id.sansets.infood.home.presenter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import id.sansets.infood.InFoodApplication
import id.sansets.infood.core.data.Resource
import id.sansets.infood.core.domain.model.FoodCategory
import id.sansets.infood.core.domain.model.ListFoodCategory
import id.sansets.infood.core.util.autoCleared
import id.sansets.infood.core.util.setAppBarElevationListener
import id.sansets.infood.home.databinding.FragmentHomeBinding
import id.sansets.infood.home.di.DaggerHomeComponent
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), HomeActionListener {

    private var binding by autoCleared<FragmentHomeBinding>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }
    private val homeSectionAdapter: HomeSectionAdapter by lazy { HomeSectionAdapter(this) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
        initData()
    }

    private fun initDependencyInjection() {
        DaggerHomeComponent.factory()
            .create(InFoodApplication.coreComponent)
            .inject(this)
    }

    override fun onFoodCategoryClicked(foodCategory: FoodCategory) {
        val action = HomeFragmentDirections.actionHomeFragmentToRecipeListFragment(
            ListFoodCategory(
                listOf(foodCategory)
            )
        )
        findNavController().navigate(action)
    }

    private fun initView() {
        binding.rvHome.apply {
            adapter = homeSectionAdapter
            setAppBarElevationListener(binding.appBar)
        }
        binding.searchView
            .findViewById<EditText>(androidx.appcompat.R.id.search_src_text).apply {
                isFocusableInTouchMode = false

                setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToRecipeListFragment()
                    findNavController().navigate(action)
                }
            }
        binding.cardSearch.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRecipeListFragment()
            findNavController().navigate(action)
        }
    }

    private fun initObserver() {
        viewModel.foodCategories.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    homeSectionAdapter.setFoodCategories(it.data)
                }
                is Resource.Error -> {

                }
            }
        })
    }

    private fun initData() {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}