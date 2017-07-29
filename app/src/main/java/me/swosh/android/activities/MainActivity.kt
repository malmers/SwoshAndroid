package me.swosh.android.activities

import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import me.swosh.android.R
import me.swosh.android.data.Preference
import me.swosh.android.fragments.CreateFragment
import me.swosh.android.fragments.HomeFragment
import me.swosh.android.fragments.ResponseFragment
import me.swosh.android.models.Swosh

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {
    override fun onBackStackChanged() {
        supportFragmentManager.popBackStack();
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {
            android.R.id.home -> {
                val fragmentManager = supportFragmentManager
                if (fragmentManager.backStackEntryCount > 0) {
                    fragmentManager.popBackStack()

                }
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    lateinit var homeFragment : HomeFragment
    lateinit var createFragment : CreateFragment
    lateinit var responseFragment : ResponseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)
        initToolbar()

        val preference = Preference(getPreferences(Context.MODE_PRIVATE))
        homeFragment = HomeFragment()
        createFragment = CreateFragment()
        responseFragment = ResponseFragment()

        createFragment.setPreference(preference)

        createFragment.setResponseListener(object: CreateFragment.ResponseListener {
            override fun sendResponse(swosh: Swosh) {

                responseFragment.setResponse(swosh)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, responseFragment)
                    .addToBackStack(getString(R.string.TAG_RESPONSE_FRAGMENT))
                    .commit()
            }
        })

        homeFragment.setHomeListener(object: HomeFragment.HomeListener {
            override fun sendResponse() {

                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, createFragment)
                        .addToBackStack(getString(R.string.TAG_CREATE_FRAGMENT))
                        .commit()
            }
        })

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .commit()
    }

    fun initToolbar() {
        val toolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }
}
