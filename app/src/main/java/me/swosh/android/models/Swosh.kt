package me.swosh.android.models

class Swosh(val amount: Int, expiration: Int, val id: String, val message: String, val phone: String, val url: String) {
    val expiration = expireTimeStamp(expiration)

    constructor(request: SwoshRequest, response: SwoshResponse):
            this(request.amount, request.expireAfterSeconds, response.id, request.message, request.phone, response.url) {
    }

    private fun expireTimeStamp(expire: Int): Long {
        val timestamp = System.currentTimeMillis()/1000
        return timestamp + expire
    }
}

