package me.swosh.android

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import me.swosh.android.data.Swosh
import me.swosh.android.data.SwoshResponse

class MainActivity : FragmentActivity() {
    lateinit var createFragment : CreateFragment
    lateinit var responseFragment : ResponseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        val container : View = findViewById(R.id.fragment_container)
        container?.let {
            val transaction = supportFragmentManager.beginTransaction()
            createFragment = CreateFragment()
            responseFragment = ResponseFragment()

            createFragment.setResponseListener(object: CreateFragment.ResponseListener {
                override fun sendResponse(swosh: Swosh, response: SwoshResponse) {
                    responseFragment.updateInfo(response)
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, responseFragment);
                    transaction.addToBackStack(null);
                    transaction.commit()
                }
            })

            transaction.add(R.id.fragment_container, createFragment).commit()
        }
    }
}
