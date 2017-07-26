package me.swosh.android.domain

import me.swosh.android.models.SwoshRequest
import me.swosh.android.models.SwoshResponse

interface SwoshTransport {
    fun sendRequest(swosh : SwoshRequest, completionHandler: (response: SwoshResponse?) -> Unit)
}
