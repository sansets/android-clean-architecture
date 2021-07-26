package id.sansets.infood.onboard.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.sansets.infood.core.util.autoCleared
import id.sansets.infood.onboard.databinding.FragmentOnboardBinding

/**
 * A simple [Fragment] subclass.
 * Use the [OnboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OnboardFragment : Fragment(), OnboardActionListener {

    private var binding by autoCleared<FragmentOnboardBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOnboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewListener()
    }

    override fun onOpenHome() {
        val action = OnboardFragmentDirections.actionOnboardFragmentToNavigationHomeFragment()
        findNavController().navigate(action)
    }

    private fun initViewListener() {
        binding.btnGetStarted.setOnClickListener { onOpenHome() }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment OnboardFragment.
         */
        @JvmStatic
        fun newInstance() = OnboardFragment()
    }
}