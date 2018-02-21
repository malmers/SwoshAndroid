package me.swosh.android.activities

import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import me.swosh.android.R
import me.swosh.android.data.HistoryStorage
import me.swosh.android.data.Preference
import me.swosh.android.fragments.*
import me.swosh.android.models.Swosh

class MainActivity : AppCompatActivity(),
        FragmentManager.OnBackStackChangedListener,
        HomeFragment.HomeFragmentListener,
        CreateFragment.CreateFragmentListener,
        SwishAdapter.AdapterListener {

    override fun onBackStackChanged() {
        supportFragmentManager.popBackStack()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
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
    lateinit var preference: Preference
    lateinit var history: HistoryStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)
        initToolbar()

        preference = Preference(getPreferences(Context.MODE_PRIVATE))
        history = HistoryStorage(filesDir)

        homeFragment = HomeFragment()
        createFragment = CreateFragment()
        responseFragment = ResponseFragment()

        homeFragment.setHistory(history)
        createFragment.setHistory(history)
        createFragment.setPreference(preference)

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .commit()
    }

    fun initToolbar() {
        val toolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }

    override fun addClick() {

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, createFragment)
                .addToBackStack(getString(R.string.TAG_CREATE_FRAGMENT))
                .commit()
    }

    override fun doneClick(swosh: Swosh) {

        history.addSwosh(swosh)

        responseFragment.setResponse(swosh)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, responseFragment)
                .addToBackStack(getString(R.string.TAG_RESPONSE_FRAGMENT))
                .commit()
    }

    override fun onCardClick(swosh: Swosh) {
        responseFragment.setResponse(swosh)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, responseFragment)
                .addToBackStack(getString(R.string.TAG_RESPONSE_FRAGMENT))
                .commit()
    }

    // menu appearing from bottom when user long click a card
    override fun onCardLongClick(swosh: Swosh): Boolean {

        var optionsMenuFragment: OptionsMenuFragment = OptionsMenuFragment()
        optionsMenuFragment.setSwosh(swosh)
        optionsMenuFragment.setEditButtonListener(View.OnClickListener {
            onCardClick(swosh)
        })
        optionsMenuFragment.setDeleteButtonListener(View.OnClickListener {
            // removes the swosh
            history.removeSwosh(swosh)
            homeFragment.setHistory(history)
            homeFragment.refreshSwoshList()

        })
        optionsMenuFragment.show(supportFragmentManager.beginTransaction(),
                "dialog")

        return true
    }
}
