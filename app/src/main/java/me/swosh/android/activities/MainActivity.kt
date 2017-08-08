package me.swosh.android.activities

import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.ArraySet
import android.util.Log
import android.view.MenuItem
import android.view.View
import me.swosh.android.R
import me.swosh.android.data.Preference
import me.swosh.android.fragments.*
import me.swosh.android.models.Swosh
import org.json.JSONObject

class MainActivity : AppCompatActivity(),
        FragmentManager.OnBackStackChangedListener,
        HomeFragment.HomeFragmentListener,
        CreateFragment.CreateFragmentListener,
        SwishAdapter.AdapterListener {

    override fun onBackStackChanged() {
        supportFragmentManager.popBackStack()
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

    var swoshList: ArrayList<Swosh> = ArrayList()

    lateinit var homeFragment : HomeFragment
    lateinit var createFragment : CreateFragment
    lateinit var responseFragment : ResponseFragment
    lateinit var preference: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)
        initToolbar()

        preference = Preference(getPreferences(Context.MODE_PRIVATE))

        homeFragment = HomeFragment()
        createFragment = CreateFragment()
        responseFragment = ResponseFragment()
        createFragment.setPreference(preference)

        loadSwoshes()

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .commit()
    }

    /**
     * Loads all active swoshes from memory
     */
    fun loadSwoshes() {

        val swoshStrings: Set<String> = preference.swoshes

        if(swoshStrings != null)
            swoshStrings.forEach { s -> swoshList.add(buildSwoshFromString(s)) }

        homeFragment.setSwoshList(swoshList)

        Log.d("MainActivity ", "LOAD DONE")
        Log.d("MainActivity ", "Loaded swoshList of size: " + swoshList.size)
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

        swoshList.add(swosh)

        Log.d("MainActivity ", "Added swosh to list of new size: " + swoshList.size)

        val oldSwoshes: Set<String> = preference.swoshes
        var swoshes: Set<String> = ArraySet<String>()

        swoshes = swoshes.plus(oldSwoshes)
        swoshes = swoshes.plus(jsonifySwosh(swosh).toString())


        Log.d("MainActivity ", "Saved swoshes of size: " + swoshes.size)

        preference.swoshes = swoshes

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

    override fun onCardLongClick(swosh: Swosh): Boolean {

        var optionsMenuFragment: OptionsMenuFragment = OptionsMenuFragment()
        optionsMenuFragment.setSwosh(swosh)
        optionsMenuFragment.setEditButtonListener(View.OnClickListener {
            onCardClick(swosh)
        })
        optionsMenuFragment.show(supportFragmentManager.beginTransaction(),
                "dialog")

        return true
    }

    fun jsonifySwosh(swosh : Swosh): JSONObject {
        var jsonObject: JSONObject = JSONObject()
        jsonObject.accumulate(getString(R.string.JSON_TAG_PHONE), swosh.phone)
        jsonObject.accumulate(getString(R.string.JSON_TAG_AMOUNT), swosh.amount)
        jsonObject.accumulate(getString(R.string.JSON_TAG_MESSAGE), swosh.message)
        jsonObject.accumulate(getString(R.string.JSON_TAG_EXPIRATION), swosh.expireAfterSeconds)
        jsonObject.accumulate(getString(R.string.JSON_TAG_ID), swosh.id)
        jsonObject.accumulate(getString(R.string.JSON_TAG_URL), swosh.url)

        return jsonObject
    }


    fun buildSwoshFromString(jsonString: String): Swosh {

        var json: JSONObject = JSONObject(jsonString)
        return Swosh(json.getString(getString(R.string.JSON_TAG_PHONE)),
                json.getInt(getString(R.string.JSON_TAG_AMOUNT)),
                json.getString(getString(R.string.JSON_TAG_MESSAGE)),
                json.getInt(getString(R.string.JSON_TAG_EXPIRATION)),
                json.getString(getString(R.string.JSON_TAG_ID)),
                json.getString(getString(R.string.JSON_TAG_URL)))
    }
}
