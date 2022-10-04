package com.example.mvvmview.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmview.R
import com.example.mvvmview.databinding.EditPersonalDataBinding
import com.example.mvvmview.databinding.PersonalDataBinding

class PersonalDataActivity: AppCompatActivity() {

    private lateinit var binding: PersonalDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}