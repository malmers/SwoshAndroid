package me.swosh.android.domain

import me.swosh.android.data.Swosh
import me.swosh.android.data.SwoshResponse
import org.json.JSONObject

interface SwoshTransport {
    fun sendRequest(swosh : Swosh, completionHandler: (response: SwoshResponse?) -> Unit)
}
