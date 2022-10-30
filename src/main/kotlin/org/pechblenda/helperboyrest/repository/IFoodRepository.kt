package org.pechblenda.helperboyrest.repository

import org.pechblenda.helperboyrest.entity.Food

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

import java.util.UUID

interface IFoodRepository: JpaRepository<Food, UUID> {

	@Query("select food from Food food order by food.createDate")
	override fun findAll(): MutableList<Food>

	@Query("select food from Food food where food.has = :has")
	fun findByHas(has: Boolean): MutableList<Food>

	@Query("select sum(food.price) from Food food where food.has = true")
	fun sumTotal(): Double?

}
