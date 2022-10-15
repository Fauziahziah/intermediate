package com.example.mysubmission_intermediate.UI.Story.Home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission_intermediate.MainActivity
import com.example.mysubmission_intermediate.UI.ViewModelFactory
import com.example.mysubmission_intermediate.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodelFactory: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { viewmodelFactory }
    private var token = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.etStory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        setupViewModel()

        val storyAdapter = StoryAdapter()
        binding.etStory.adapter = storyAdapter
        homeViewModel.loadState().observe(viewLifecycleOwner) { pref ->

            homeViewModel.getAllStories(pref.token).observe(viewLifecycleOwner) { pagingData ->
                storyAdapter.submitData(lifecycle, pagingData)
            }
        }
        return root
    }

    private fun setupViewModel() {
        viewmodelFactory = ViewModelFactory.getInstance(requireContext())

        homeViewModel.loadState().observe(viewLifecycleOwner) {
            token = it.token
            if (!it.isLogin) {
                intentActivity()
            } else {
                getAllStories(token)
            }
        }
        showToast()
    }

    private fun showToast() {
        homeViewModel.toastText.observe(viewLifecycleOwner) { toastText ->
            Toast.makeText(
                requireContext(), toastText, Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun intentActivity() {
        startActivity((Intent(requireContext(), MainActivity::class.java)))
    }

    private fun getAllStories(token: String) {
        homeViewModel.getAllStories(token)
    }

}