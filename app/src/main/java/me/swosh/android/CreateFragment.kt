package me.swosh.android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import me.swosh.android.data.Swosh
import me.swosh.android.data.SwoshResponse
import me.swosh.android.domain.SwoshHTTP

class CreateFragment : Fragment() {

    private lateinit var expiration : String
    private lateinit var responseListener : ResponseListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        val dropdown : Spinner = view.findViewById(R.id.expiration)
        val phone_field : EditText = view.findViewById(R.id.phone)
        val amount_field : EditText = view.findViewById(R.id.amount)
        val message_field : EditText = view.findViewById(R.id.message)
        val button : Button = view.findViewById(R.id.button)

        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                expiration = resources.getStringArray(R.array.amount_value)[position]
            }
        }

        button.setOnClickListener {
            val swosh = Swosh(phone_field.text.toString(), amount_field.text.toString(), message_field.text.toString(), expiration)
            val transport = SwoshHTTP()
            transport.sendRequest(swosh) { response ->
                response?.let {
                    responseListener.sendResponse(swosh, response)
                }
            }
        }
        return view
    }

    fun setResponseListener(listener : ResponseListener) {
        responseListener = listener
    }

    interface ResponseListener {
        fun sendResponse(swosh: Swosh, response: SwoshResponse)
    }
}
