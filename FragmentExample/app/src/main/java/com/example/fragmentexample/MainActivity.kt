package com.example.fragmentexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Dynamically add MyFragment to the container
        val myFragment = MyFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, myFragment, "MyFragment")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    // Method to send data from MyFragment to Fragment2
    fun sendDataToFragment2(data: String) {
        // Dynamically replace MyFragment with Fragment2 and pass data
        val fragment2 = Fragment2()
        val bundle = Bundle()
        bundle.putString("data", data)
        fragment2.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment2, "Fragment2")
            .addToBackStack(null) // Add to back stack to allow navigation back to MyFragment
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}
