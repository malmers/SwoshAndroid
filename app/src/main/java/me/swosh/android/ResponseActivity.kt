package me.swosh.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import me.swosh.android.data.Swosh
import me.swosh.android.data.SwoshResponse
import android.content.Intent
import android.widget.Button

class ResponseActivity : AppCompatActivity() {

    lateinit var swosh : Swosh
    lateinit var response : SwoshResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        val mapper = ObjectMapper().registerKotlinModule()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response)
        swosh = mapper.readValue(intent.getStringExtra("swosh"), Swosh::class.java)
        response = mapper.readValue(intent.getStringExtra("response"), SwoshResponse::class.java)
        updateFields()
    }

    private fun updateFields() {
        val id : TextView = findViewById(R.id.response_id)
        val phone : TextView = findViewById(R.id.response_phone)
        val amount : TextView = findViewById(R.id.response_amount)
        val expiration : TextView = findViewById(R.id.response_expiration)
        val message : TextView = findViewById(R.id.response_message)
        val share_button : Button = findViewById(R.id.response_share_button)
        id.setText(response.id)
        phone.setText(swosh.phone)
        amount.setText(swosh.amount)
        expiration.setText(swosh.expireAfterSeconds)
        message.setText(swosh.message)
        share_button.setOnClickListener {
            share()
        }
    }

    private fun share() {
        val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, response.url)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }
}
