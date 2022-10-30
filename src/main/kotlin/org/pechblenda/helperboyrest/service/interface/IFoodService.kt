package org.pechblenda.helperboyrest.service.`interface`

import org.springframework.http.ResponseEntity

import org.pechblenda.service.Request

import java.util.UUID

interface IFoodService {
	fun findAllFoodsByUuid(foodUuid: UUID?, has: Boolean?): ResponseEntity<Any>
	fun createFood(request: Request): ResponseEntity<Any>
	fun updateFood(request: Request): ResponseEntity<Any>
	fun setHasAndPriceFood(uuid: UUID, has: Boolean, price: Double): ResponseEntity<Any>
	fun deleteFood(foodUuid: UUID): ResponseEntity<Any>
}
