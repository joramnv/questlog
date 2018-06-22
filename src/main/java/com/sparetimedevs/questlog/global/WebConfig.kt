package com.sparetimedevs.questlog.global

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.ShallowEtagHeaderFilter
import javax.servlet.Filter

@Configuration
class WebConfig {

	@Bean
	fun shallowEtagHeaderFilter(): Filter {
		return ShallowEtagHeaderFilter()
	}
}