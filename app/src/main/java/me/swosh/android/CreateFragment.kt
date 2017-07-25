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
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import me.swosh.android.data.Swosh
import me.swosh.android.data.SwoshRequest
import me.swosh.android.data.SwoshResponse
import me.swosh.android.domain.SwoshHTTP
import com.fasterxml.jackson.databind.node.ObjectNode


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
            val request = SwoshRequest(phone_field.text.toString(), intFromTextField(amount_field), message_field.text.toString(), Integer.parseInt(expiration))
            val transport = SwoshHTTP()
            transport.sendRequest(request) { response ->
                response?.let {
                    responseListener.sendResponse(combineSwoshData(request, response))
                }
            }
        }
        return view
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

    fun setResponseListener(listener : ResponseListener) {
        responseListener = listener
    }

    interface ResponseListener {
        fun sendResponse(swosh: Swosh)
    }
}
