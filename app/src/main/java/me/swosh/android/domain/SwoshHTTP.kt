package me.swosh.android.domain

import android.util.Log
import com.android.volley.Cache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.NoCache
import com.fasterxml.jackson.databind.ObjectMapper
import me.swosh.android.data.Swosh
import org.json.JSONObject
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.BasicNetwork

class SwoshHTTP() : SwoshTransport {
    private val url = "https://swosh.me/api/create"

    companion object {
        val queue: RequestQueue = RequestQueue(NoCache(), BasicNetwork(HurlStack()))
    }

    override fun sendRequest(swosh: Swosh, completionHandler: (response: JSONObject?) -> Unit ) {
        val mapper = ObjectMapper()
        val values = mapper.writeValueAsString(swosh)

        val request = JsonObjectRequest(Request.Method.POST, url, JSONObject(values),
                Response.Listener<JSONObject> {
                    response -> Log.d("swosh", "Data found")
                    completionHandler(response)
                },
                Response.ErrorListener {
                    response -> Log.d("swosh", "No data")
                    completionHandler(null)
                })
        request.setShouldCache(false)
        queue.add(request)
    }

    init {
        queue.start()
    }
}
