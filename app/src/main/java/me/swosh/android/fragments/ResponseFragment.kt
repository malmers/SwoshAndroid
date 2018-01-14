package me.swosh.android.fragments

import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.swosh.android.R
import me.swosh.android.activities.MainActivity
import me.swosh.android.models.Swosh
import kotlinx.android.synthetic.main.fragment_response.*
import net.glxn.qrgen.android.QRCode
import net.glxn.qrgen.core.image.ImageType
import java.text.SimpleDateFormat
import java.util.*

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

    fun setResponse(response: Swosh) {
        this.response = response
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        response_qr.setImageBitmap(QRCode.from(response.url)
                .withSize(100,100)
                .withColor(Color.DKGRAY, Color.TRANSPARENT)
                .bitmap())
        //response_id.text = "#${response.id}"
        response_amount.text = "${response.amount}"
        response_message.text = response.message
        response_phone.text = response.phone

        val formatter = SimpleDateFormat("yyyy-MM-dd 'at' HH:mm")

        response_expiration.text = formatter.format(Date(response.expiration))
        response_done_button.setOnClickListener {

            // if user is coming from createFragment, pop an addition fragment
            if(fragmentManager.backStackEntryCount >= 2)
                fragmentManager.popBackStack()

            fragmentManager.popBackStack()
        }

        response_share_button.setOnClickListener {
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, response.url)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }
    }
}
