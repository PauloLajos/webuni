package hu.webuni.fragmentdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.webuni.fragmentdemo.databinding.FragmentDemoBinding

class FragmentDemo : Fragment() {

    private var _binding: FragmentDemoBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDemoBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.btnDemo.setOnClickListener {
            Toast.makeText(activity,"Demo",Toast.LENGTH_LONG).show()
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}