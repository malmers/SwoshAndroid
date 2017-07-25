package me.swosh.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.swosh.android.data.Swosh

class MainActivity : AppCompatActivity() {
    lateinit var createFragment : CreateFragment
    lateinit var responseFragment : ResponseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        val transaction = supportFragmentManager.beginTransaction()
        createFragment = CreateFragment()
        responseFragment = ResponseFragment()

        createFragment.setResponseListener(object: CreateFragment.ResponseListener {
            override fun sendResponse(swosh: Swosh) {
                responseFragment.updateInfo(swosh)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, responseFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })

        transaction.add(R.id.fragment_container, createFragment).commit()
    }
}
