package me.swosh.android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class HomeFragment : Fragment() {

    private lateinit var homeListener : HomeListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        updateFields(view)
        return view
    }

    private fun updateFields(view: View) {
        view.findViewById<Button>(R.id.home_button).setOnClickListener {
            homeListener.sendResponse()
        }
    }

    fun setHomeListener(listener: HomeListener) {
        this.homeListener = listener
    }

    interface HomeListener {
        fun sendResponse()
    }
}
