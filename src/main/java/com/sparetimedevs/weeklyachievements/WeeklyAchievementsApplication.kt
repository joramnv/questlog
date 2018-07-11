package com.sparetimedevs.weeklyachievements

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = ["com.sparetimedevs.weeklyachievements"])
class WeeklyAchievementsApplication

fun main(args: Array<String>) {
	runApplication<WeeklyAchievementsApplication>(*args)
}
