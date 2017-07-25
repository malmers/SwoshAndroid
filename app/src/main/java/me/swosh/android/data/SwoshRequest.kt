package me.swosh.android.data

data class SwoshRequest(val phone: String, val amount: Int, val message: String, val expireAfterSeconds: Int)
