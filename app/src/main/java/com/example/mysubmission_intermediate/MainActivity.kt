package com.example.mysubmission_intermediate

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mysubmission_intermediate.UI.SignInFragment
import com.example.mysubmission_intermediate.UI.SignUpFragment
import com.example.mysubmission_intermediate.UI.Story.Home.HomeViewModel
import com.example.mysubmission_intermediate.UI.Story.StoryActivity
import com.example.mysubmission_intermediate.UI.ViewModelFactory
import com.example.mysubmission_intermediate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }
        homeViewModel.loadState().observe(this){
           if(it.token!="" && it.isLogin){ // Jika sudah login akan menuju StoryActivity
               startActivity(Intent(this, StoryActivity::class.java))
               finish()
           }
        }
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