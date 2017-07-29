package me.swosh.android.data

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import me.swosh.android.models.Swosh
import java.io.File
import java.util.*

class HistoryStorage(directory: File) {
    private val filename = "file.txt"
    private val file = File(directory, filename)
    private var swoshList = LinkedList<Swosh>()

    fun addSwosh(swosh: Swosh) {
        val mapper = ObjectMapper().registerKotlinModule()
        swoshList.add(swosh)
        file.appendText(mapper.writeValueAsString(swosh))
    }

    fun getSwoshList(): List<Swosh> {
        return swoshList
    }

    init {
        file.createNewFile()
        val mapper = jacksonObjectMapper().registerKotlinModule()
        val lines = file.readLines()
        lines.forEach {
            swoshList.add(mapper.readValue(it, Swosh::class.java))
        }
    }
}
