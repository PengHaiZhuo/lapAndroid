package com.phz.dev.feature.practice.dialog.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phz.dev.databinding.ActivityDialogSampleBinding

/**
 * @author phz
 * @description
 */
class DialogSampleActivity : AppCompatActivity() {
    lateinit var binding: ActivityDialogSampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogSampleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}