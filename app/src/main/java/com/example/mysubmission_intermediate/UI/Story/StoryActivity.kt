package com.example.mysubmission_intermediate.UI.Story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mysubmission_intermediate.databinding.ActivityStoryBinding

class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}