package me.swosh.android.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.swosh.android.R
import me.swosh.android.data.Preference
import me.swosh.android.fragments.CreateFragment
import me.swosh.android.fragments.HomeFragment
import me.swosh.android.fragments.ResponseFragment
import me.swosh.android.models.Swosh

class MainActivity : AppCompatActivity() {
    lateinit var homeFragment : HomeFragment
    lateinit var createFragment : CreateFragment
    lateinit var responseFragment : ResponseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        val preference = Preference(getPreferences(Context.MODE_PRIVATE))
        val transaction = supportFragmentManager.beginTransaction()
        homeFragment = HomeFragment()
        createFragment = CreateFragment()
        responseFragment = ResponseFragment()

        createFragment.setPreference(preference)

        createFragment.setResponseListener(object: CreateFragment.ResponseListener {
            override fun sendResponse(swosh: Swosh) {
                responseFragment.setResponse(swosh)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, responseFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })

        homeFragment.setHomeListener(object: HomeFragment.HomeListener {
            override fun sendResponse() {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, createFragment)
                        .addToBackStack(null)
                        .commit()
            }
        })

        transaction.add(R.id.fragment_container, homeFragment).commit()
    }
}
