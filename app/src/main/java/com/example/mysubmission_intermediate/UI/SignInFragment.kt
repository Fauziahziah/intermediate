package com.example.mysubmission_intermediate.UI

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.mysubmission_intermediate.Model.UserModel
import com.example.mysubmission_intermediate.MainActivity
import com.example.mysubmission_intermediate.R
import com.example.mysubmission_intermediate.UI.SignUpFragment
import com.example.mysubmission_intermediate.UI.Story.StoryActivity
import com.example.mysubmission_intermediate.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: ViewModelFactory
    private val signInViewModel: SignInViewModel by viewModels { viewModelFactory  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        setActionSignUp()
        setupViewModel()
    }

    private fun setupAction() {
        binding.apply{
            btnBack.setOnClickListener{
                val intent = Intent(this@SignInFragment.requireContext(), MainActivity::class.java)
                startActivity(intent)
            }

            btnRegister.setOnClickListener{
                val signUpFragment = SignUpFragment()
                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.signInIn, signUpFragment, SignUpFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
                binding.btnRegister.visibility = View.GONE
                binding.btnForgetPassword.visibility = View.GONE
                binding.btnSignIn.visibility = View.GONE

            }
        }
    }

    private fun setActionSignUp() {
        binding.btnSignIn.setOnClickListener{
            val email = binding.etEmailEdit.text.toString()
            val password = binding.etPasswordEdit.text.toString()

            when {
                email.isEmpty() -> {
                    binding.etEmailEdit.error = getString(R.string.message_name_error)
                }
                password.isEmpty() -> {
                    binding.etPasswordEdit.error = getString(R.string.message_password_error)
                }
                email != email -> {
                    binding.etEmailEdit.error = getString(R.string.message_email_novalid)
                }
                password != password -> {
                    binding.etPasswordEdit.error = getString(R.string.message_password_novalid)
                }

                else -> {
                    signInViewModel.login()
                    post()
                    showToast()
                    intentFragment()
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModelFactory = ViewModelFactory.getInstance(requireActivity())
    }

    private fun showToast() {
        signInViewModel.toastText.observe(viewLifecycleOwner) { toastText ->
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show()
        }
    }

    private fun post() {
        binding.apply {
            signInViewModel.loginAccount(
                etEmailEdit.text.toString(),
                etPasswordEdit.text.toString()
            )
        }

        signInViewModel.loginResult.observe(viewLifecycleOwner) { response ->
          if (response != null) {
              saveUser(
                UserModel(
                    response.name,
                    AUTH_KEY + (response.token),
                    true
                )
              )
          }
        }
    }

    private fun saveUser(session: UserModel){
        signInViewModel.saveUser(session)
    }

    companion object {
        private const val AUTH_KEY = "Bearer"
    }

    private fun intentFragment() {
        signInViewModel.loginResponse.observe( viewLifecycleOwner ) { response ->
            if (!response.error) {
                val intent = Intent(this@SignInFragment.requireContext(), StoryActivity::class.java)
                startActivity(intent)
            }
        }
    }

}