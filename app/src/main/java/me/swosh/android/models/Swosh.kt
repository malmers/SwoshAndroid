package me.swosh.android.models

import org.json.JSONObject

data class Swosh(val phone: String, val amount: Int, val message: String, val expireAfterSeconds: Int, val id: String, val url: String)

