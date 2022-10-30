package org.pechblenda.helperboyrest.config

import org.pechblenda.auth.AuthController
import org.pechblenda.doc.Documentation
import org.pechblenda.helperboyrest.controller.FoodController
import org.pechblenda.helperboyrest.entity.Food
import org.pechblenda.helperboyrest.entity.User

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("org.pechblenda.bean")
class BeanConfig {

	@Bean
	fun documentation(): Documentation {
		return Documentation(
			mutableListOf(
				User::class,
				Food::class
			),
			AuthController::class,
			FoodController::class
		)
	}

}