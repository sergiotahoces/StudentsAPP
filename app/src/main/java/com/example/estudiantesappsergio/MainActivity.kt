package com.example.estudiantesappsergio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // set the layout file
        setSupportActionBar(findViewById(R.id.toolbar)) // add the action bar
    }

    /** This method gets called, when the back button is pressed. */
    override fun onSupportNavigateUp(): Boolean {
        // go back, when back button has been pressed
        findNavController(R.id.nav_host_fragment).navigateUp()
        return true
    }
}