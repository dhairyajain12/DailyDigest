package com.dailydigest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dailydigest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //    // var declaration
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
    }
}