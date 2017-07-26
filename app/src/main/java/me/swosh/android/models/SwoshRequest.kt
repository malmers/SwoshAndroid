package me.swosh.android.models

data class SwoshRequest(val phone: String, val amount: Int, val message: String, val expireAfterSeconds: Int)
