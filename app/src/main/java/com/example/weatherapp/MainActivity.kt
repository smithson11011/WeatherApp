package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.view.NavigationFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_main_frame, NavigationFragment())
                .addToBackStack("Main Activity")
                .commit()
        }
    }

}