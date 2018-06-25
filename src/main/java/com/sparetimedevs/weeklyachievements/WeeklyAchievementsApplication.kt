package com.sparetimedevs.weeklyachievements

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.sparetimedevs.weeklyachievements"])
class WeeklyAchievementsApplication

fun main(args: Array<String>) {
	SpringApplication.run(WeeklyAchievementsApplication::class.java, *args)
}
