package com.example.mysubmission_intermediate.UI.Story.logout

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mysubmission_intermediate.MainActivity
import com.example.mysubmission_intermediate.R
import com.example.mysubmission_intermediate.UI.ViewModelFactory
import com.example.mysubmission_intermediate.databinding.FragmentLogoutBinding


class LogoutFragment : Fragment() {
    private var _binding: FragmentLogoutBinding? = null
    private val binding get() = _binding!!
    private val logoutViewModel: LogoutViewModel by viewModels { viewModelFactory }
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogoutBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModelFactory = ViewModelFactory.getInstance(requireContext())

        setupAction()

        return root
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            logoutViewModel.logout()
            intentFragment()
        }
    }

    private fun intentFragment() {
        activity?.let{
            val intent = Intent (it, MainActivity::class.java)
            it.startActivity(intent)
        }
    }
}