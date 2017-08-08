package me.swosh.android.models

class Swosh(request: SwoshRequest, response: SwoshResponse) {
    val phone = request.phone
    val amount = request.amount
    val message = request.message
    val expiration = expireTimeStamp(request.expireAfterSeconds)
    val id = response.id
    val url = response.url

    constructor(phone: String, amount: Int, message: String, expire: Int, id: String, url: String):
            this(SwoshRequest(phone, amount, message, expire), SwoshResponse(id, url)) {
    }

    private fun expireTimeStamp(expire: Int): Long {
        val timestamp = System.currentTimeMillis()/1000
        return timestamp + expire
    }
}

