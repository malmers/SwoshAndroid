package me.swosh.android.models

import com.fasterxml.jackson.annotation.JsonIgnore

class Swosh(val amount: Int, val expiration: Long, val id: String, val message: String, val phone: String, val url: String) {

    constructor(request: SwoshRequest, response: SwoshResponse):
            this(request.amount,
                    System.currentTimeMillis()
                            + request.expireAfterSeconds*1000,
                    response.id,
                    request.message,
                    request.phone,
                    response.url)

    @JsonIgnore
    fun isExpired(): Boolean {
        return expiration < System.currentTimeMillis()
    }
}
