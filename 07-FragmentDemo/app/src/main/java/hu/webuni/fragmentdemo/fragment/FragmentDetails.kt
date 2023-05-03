package hu.webuni.fragmentdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.webuni.fragmentdemo.databinding.FragmentDetailsBinding

class FragmentDetails : Fragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_DETAILS"
    }

    private var _binding: FragmentDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.btnFragmentDetails.setOnClickListener {
            Toast.makeText(activity,"Details fragment", Toast.LENGTH_LONG).show()
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}