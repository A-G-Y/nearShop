package com.example.nearshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.nearshop.view.RecyclerViewFragment
import com.google.android.libraries.places.api.Places

class MainActivity : AppCompatActivity() {

    // Whether the Log Fragment is currently shown
    private var logShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the SDK
        Places.initialize(applicationContext, "YourApiKey")

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)

        /*
        Didn't work because billing not initialize for this API

        val retrofit = Retrofit.Builder()
            .baseUrl( "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=mongolian %20grill&inputtype=textquery&fields=photos,formatted_address,name,opening_ hours,rating&locationbias=circle:2000@47.6918452,-122.2226413&key=YourApiKey")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        println(retrofit)

         */


        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.sample_content_fragment, RecyclerViewFragment())
                commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    companion object {
        val TAG = "MainActivity"
    }
}
