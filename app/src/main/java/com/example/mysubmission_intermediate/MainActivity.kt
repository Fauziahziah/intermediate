package com.example.mysubmission_intermediate

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.mysubmission_intermediate.databinding.ActivityMainBinding
import com.example.mysubmission_intermediate.UI.SignUpFragment
import com.example.mysubmission_intermediate.UI.SignInFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
       binding.btnGoSignIn.setOnClickListener{
            val signInFragment = SignInFragment()
            val fragment : Fragment? =
                supportFragmentManager.findFragmentByTag(SignInFragment::class.java.simpleName)

            if (fragment !is SignInFragment) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.testing, signInFragment, SignInFragment::class.java.simpleName)
                    .commit()
            }
            binding.btnGoSignIn.visibility = View.GONE
            binding.btnGoSignUp.visibility = View.GONE

        }

        binding.btnGoSignUp.setOnClickListener{
            val signUpFragment = SignUpFragment()
            val fragment : Fragment? =
                supportFragmentManager.findFragmentByTag(SignUpFragment::class.java.simpleName)

            if (fragment !is SignUpFragment) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.testing, signUpFragment, SignUpFragment::class.java.simpleName)
                    .commit()
            }
            binding.btnGoSignIn.visibility = View.GONE
            binding.btnGoSignUp.visibility = View.GONE
        }
    }

}