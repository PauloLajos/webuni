package hu.webuni.familybudget.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.webuni.familybudget.databinding.FragmentLoginBinding
import hu.webuni.familybudget.databinding.FragmentMainBinding

class FragmentLogin : Fragment() {
    companion object {
        const val TAG = "TAG_FRAGMENT_LOGIN"
    }

    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}