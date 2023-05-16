package hu.webuni.familybudget.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.webuni.familybudget.PreferenceHelper
import hu.webuni.familybudget.databinding.FragmentMainBinding

class FragmentMain : Fragment() {
    companion object {
        const val TAG = "TAG_FRAGMENT_MAIN"
    }

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val mainBinding get() = _binding!!

    private var preferenceHelper: PreferenceHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        preferenceHelper = PreferenceHelper(requireContext())

        mainBinding.tvName.text = preferenceHelper!!.getName()

        return mainBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}