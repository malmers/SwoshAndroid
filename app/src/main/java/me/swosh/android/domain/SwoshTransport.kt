package me.swosh.android.domain

import me.swosh.android.data.SwoshRequest
import me.swosh.android.data.SwoshResponse

interface SwoshTransport {
    fun sendRequest(swosh : SwoshRequest, completionHandler: (response: SwoshResponse?) -> Unit)
}
