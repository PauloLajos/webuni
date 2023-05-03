package hu.webuni.fragmentdemo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.webuni.fragmentdemo.databinding.FragmentMainBinding

class FragmentMain : Fragment() {

    companion object {
        const val TAG = "TAG_FRAGMENT_MAIN"
    }

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.btnFragmentMain.setOnClickListener {
            Toast.makeText(activity,"Main fragment", Toast.LENGTH_LONG).show()
        }

        return rootView
    }
}