package me.swosh.android.domain

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.NoCache
import com.fasterxml.jackson.databind.ObjectMapper
import me.swosh.android.models.SwoshRequest
import org.json.JSONObject
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.BasicNetwork
import me.swosh.android.models.SwoshResponse
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

class SwoshHTTP() : SwoshTransport {
    private val url = "https://swosh.me/api/create"

    companion object {
        val queue: RequestQueue = RequestQueue(NoCache(), BasicNetwork(HurlStack()))
    }

    override fun sendRequest(swosh: SwoshRequest, completionHandler: (response: SwoshResponse?) -> Unit ) {
        val mapper = ObjectMapper().registerKotlinModule()
        val values = mapper.writeValueAsString(swosh)

        val request = JsonObjectRequest(Request.Method.POST, url, JSONObject(values),
                Response.Listener<JSONObject> {
                    response -> completionHandler(mapper.readValue(response.toString(), SwoshResponse::class.java))
                },
                Response.ErrorListener {
                    response -> completionHandler(null)
                })
        request.setShouldCache(false)
        queue.add(request)
    }

    init {
        queue.start()
    }
}
