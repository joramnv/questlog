//package com.sparetimedevs.questlog
//
//import com.fasterxml.jackson.module.kotlin.KotlinModule
//import org.springframework.boot.SpringApplication
//import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.ComponentScan
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
//
//@SpringBootApplication
//@ComponentScan(basePackages = ["com.sparetimedevs.questlog"])
//class WeeklyAchievementPointsApplication
//
//fun main(args: Array<String>) {
//	SpringApplication.run(WeeklyAchievementPointsApplication::class.java, *args)
//}
//
//@Bean
//fun objectMapperBuilder(): Jackson2ObjectMapperBuilder
//		= Jackson2ObjectMapperBuilder().modulesToInstall(KotlinModule())
