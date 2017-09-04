package me.swosh.android.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_create.*
import me.swosh.android.R
import me.swosh.android.activities.MainActivity
import me.swosh.android.data.HistoryStorage
import me.swosh.android.data.Preference
import me.swosh.android.domain.SwoshHTTP
import me.swosh.android.models.Swosh
import me.swosh.android.models.SwoshRequest

class CreateFragment : Fragment() {

    private lateinit var expiration : String
    private lateinit var listener : CreateFragmentListener
    private lateinit var preference: Preference
    private lateinit var history: HistoryStorage

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phone_text.setText(preference.phone)

        expiration_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                expiration = resources.getStringArray(R.array.amount_value)[position]
            }
        }

        create_button.setOnClickListener {
            val request = SwoshRequest(phone_text.text.toString(), intFromTextField(amount), message.text.toString(), Integer.parseInt(expiration))
            val transport = SwoshHTTP()
            transport.sendRequest(request) { response ->
                response?.let {
                    preference.phone = request.phone
                    listener.doneClick(Swosh(request, response))
                }
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is MainActivity) {
            context.supportActionBar!!.setTitle(getString(R.string.ACTIONBAR_TITLE_NEW_LINK))
            context.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        if(context is CreateFragmentListener)
            listener = context
    }

    override fun onDetach() {
        super.onDetach()

        val con = context

        if(con is MainActivity) {
            con.supportActionBar!!.setTitle(getString(R.string.ACTIONBAR_TITLE_YOUR_SWISH_LINKS))
            con.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }

        if(con is CreateFragmentListener)
            listener = con
    }

    private fun intFromTextField(field: EditText) : Int {
        return Integer.parseInt(field.text.toString())
    }

    fun setHistory(history: HistoryStorage) {
        this.history = history
    }

    fun setPreference(preference: Preference) {
        this.preference = preference
    }

    interface CreateFragmentListener {
        fun doneClick(swosh: Swosh)
    }
}
