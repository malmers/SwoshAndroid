package me.swosh.android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import me.swosh.android.data.Swosh
import me.swosh.android.domain.SwoshHTTP
import android.content.Intent
import android.widget.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

class CreateActivity : AppCompatActivity() {

    lateinit var expiration : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val dropdown : Spinner = findViewById(R.id.expiration)
        val phone_field : EditText = findViewById(R.id.phone)
        val amount_field : EditText = findViewById(R.id.amount)
        val message_field : EditText = findViewById(R.id.message)
        val button : Button = findViewById(R.id.button)

        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                expiration = resources.getStringArray(R.array.amount_value).get(position)
            }
        }

        button.setOnClickListener {
            val mapper = ObjectMapper().registerKotlinModule()
            val swosh = Swosh(phone_field.text.toString(), amount_field.text.toString(), message_field.text.toString(), expiration)
            val transport = SwoshHTTP()
            transport.sendRequest(swosh) { response ->
                response?.let {
                    val responseAction = Intent(this, ResponseActivity::class.java)
                    responseAction.putExtra("swosh", mapper.writeValueAsString(swosh))
                    responseAction.putExtra("response", mapper.writeValueAsString(response))
                    startActivity(responseAction)
                }
            }
        }
    }
}
