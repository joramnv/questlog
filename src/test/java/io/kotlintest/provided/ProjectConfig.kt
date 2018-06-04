package io.kotlintest.provided

import io.kotlintest.AbstractProjectConfig
import io.kotlintest.spring.SpringListener
import java.lang.System.currentTimeMillis
import java.lang.System.lineSeparator

object ProjectConfig : AbstractProjectConfig() {

	override fun listeners() = listOf(SpringListener)

	private var started: Long = 0

	override fun beforeAll() {
		started = currentTimeMillis()
	}

	override fun afterAll() {
		val timeInMilliseconds = currentTimeMillis() - started
		val timeInSeconds = (timeInMilliseconds / 1000) % 60
		println("overall time in seconds: " + timeInSeconds + lineSeparator())
	}
}
