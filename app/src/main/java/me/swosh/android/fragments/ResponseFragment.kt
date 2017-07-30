package me.swosh.android.fragments

import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.swosh.android.R
import me.swosh.android.activities.MainActivity
import me.swosh.android.models.Swosh
import kotlinx.android.synthetic.main.fragment_response.*

class ResponseFragment : Fragment() {

    private lateinit var response : Swosh

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_response, container, false)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is MainActivity) {
            context.supportActionBar!!.setTitle(getString(R.string.ACTIONBAR_TITLE_SWOSH_LINK))
            context.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onDetach() {
        super.onDetach()

        val con = context

        if(con is MainActivity) {
            con.supportActionBar!!.setTitle(getString(R.string.ACTIONBAR_TITLE_YOUR_SWISH_LINKS))
            con.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
    }

    fun setResponse(response: Swosh) {
        this.response = response
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        response_id.text = "#${response.id}"
        response_amount.text = "${response.amount}"
        response_message.text = response.message

        response_share_button.setOnClickListener {
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, response.url)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }
    }
}
