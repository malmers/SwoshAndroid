package me.swosh.android.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import me.swosh.android.R
import me.swosh.android.activities.MainActivity

class HomeFragment : Fragment() {

    private lateinit var listener: HomeFragmentListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        updateFields(view)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is MainActivity) {
            context.supportActionBar!!.setTitle(getString(R.string.ACTIONBAR_TITLE_YOUR_SWISH_LINKS))
            context.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }

        if(context is HomeFragmentListener)
            listener = context
    }

    private fun updateFields(view: View) {
        view.findViewById<Button>(R.id.home_button).setOnClickListener {
            listener.addClick()
        }
    }

    interface HomeFragmentListener {
        fun addClick()
    }
}
