package me.swosh.android.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import me.swosh.android.models.Swosh
import me.swosh.android.models.SwoshRequest
import me.swosh.android.models.SwoshResponse
import me.swosh.android.domain.SwoshHTTP
import com.fasterxml.jackson.databind.node.ObjectNode
import me.swosh.android.R
import me.swosh.android.activities.MainActivity
import me.swosh.android.data.Preference

class CreateFragment : Fragment() {

    private lateinit var expiration : String
    private lateinit var responseListener : ResponseListener
    private lateinit var preference: Preference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        val dropdown : Spinner = view.findViewById(R.id.expiration)
        val phone_field : EditText = view.findViewById(R.id.phone)
        val amount_field : EditText = view.findViewById(R.id.amount)
        val message_field : EditText = view.findViewById(R.id.message)
        val button : Button = view.findViewById(R.id.create_button)

        phone_field.setText(preference.phone)

        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                expiration = resources.getStringArray(R.array.amount_value)[position]
            }
        }

        button.setOnClickListener {
            val request = SwoshRequest(phone_field.text.toString(), intFromTextField(amount_field), message_field.text.toString(), Integer.parseInt(expiration))
            val transport = SwoshHTTP()
            transport.sendRequest(request) { response ->
                response?.let {
                    preference.phone = request.phone
                    responseListener.sendResponse(combineSwoshData(request, response))
                }
            }
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        var con = context

        if(con is MainActivity) {
            con.supportActionBar!!.setTitle(getString(R.string.ACTIONBAR_TITLE_NEW_LINK))
            con.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onDetach() {
        super.onDetach()

        var con = context

        if(con is MainActivity) {
            con.supportActionBar!!.setTitle(getString(R.string.ACTIONBAR_TITLE_YOUR_SWISH_LINKS))
            con.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun combineSwoshData(request: SwoshRequest, response: SwoshResponse): Swosh {
        val mapper = ObjectMapper().registerKotlinModule()
        val node = mapper.convertValue(request, ObjectNode::class.java)
        node.put("id", response.id)
        node.put("url", response.url)
        return mapper.convertValue(node, Swosh::class.java)
    }

    private fun intFromTextField(field: EditText) : Int {
        return Integer.parseInt(field.text.toString())
    }

    fun setPreference(preference: Preference) {
        this.preference = preference
    }

    fun setResponseListener(listener : ResponseListener) {
        responseListener = listener
    }

    interface ResponseListener {
        fun sendResponse(swosh: Swosh)
    }
}
