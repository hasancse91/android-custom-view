package com.hellohasan.androidcustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // image drawable and title are already set from xml.
        // I want to set subtitle programmatically. you can set title and image drawable from here
        sunrise_custom_view.setSubtitle("5:31 AM") // sunrise time set as subtitle
        sunset_custom_view.setSubtitle("5:01 PM") // subset time set as subtitle
    }
}
