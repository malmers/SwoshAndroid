package me.swosh.android.fragments

import android.os.Bundle
import android.content.Intent
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import me.swosh.android.R
import me.swosh.android.models.Swosh

class ResponseFragment : Fragment() {

    private lateinit var response : Swosh

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_response, container, false)
        updateFields(view)
        return view
    }

    fun setResponse(response: Swosh) {
        this.response = response
    }

    private fun updateFields(view: View) {
        val id : TextView = view.findViewById(R.id.response_id)
        val share_button : Button = view.findViewById(R.id.response_share_button)

        id.text = response.id
        share_button.setOnClickListener {
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, response.url)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }
    }
}
