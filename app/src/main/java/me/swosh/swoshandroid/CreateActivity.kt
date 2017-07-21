package me.swosh.swoshandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

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
            message_field.setText("Phone: ${phone_field.text}, amount: ${amount_field.text}, expiration: ${expiration}")
        }
    }
}
