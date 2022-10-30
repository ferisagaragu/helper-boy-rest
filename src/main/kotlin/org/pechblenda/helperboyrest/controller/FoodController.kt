package org.pechblenda.helperboyrest.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.DeleteMapping

import org.pechblenda.exception.HttpExceptionResponse
import org.pechblenda.service.Request
import org.pechblenda.doc.annotation.ApiDocumentation
import org.pechblenda.helperboyrest.service.`interface`.IFoodService

import java.util.UUID
import javax.websocket.server.PathParam

@CrossOrigin(methods = [
	RequestMethod.GET,
	RequestMethod.POST,
	RequestMethod.PUT,
	RequestMethod.PATCH,
	RequestMethod.DELETE
])
@RestController
@RequestMapping(name = "Food", value = ["/rest/foods"])
class FoodController(
	private val foodService: IFoodService,
	private val httpExceptionResponse: HttpExceptionResponse
) {

	@GetMapping(value = ["/{foodUuid}", ""])
	@ApiDocumentation(path = "doc/food/find-all-foods-by-uuid.json")
	fun findAllFoodsByUuid(
		@PathVariable foodUuid: UUID?,
		@PathParam("has") has: Boolean?
	): ResponseEntity<Any> {
		return try {
			foodService.findAllFoodsByUuid(foodUuid, has)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@PostMapping
	@ApiDocumentation(path = "doc/food/create-food.json")
	fun createFood(@RequestBody request: Request): ResponseEntity<Any> {
		return try {
			foodService.createFood(request)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@PutMapping
	@ApiDocumentation(path = "doc/food/update-food.json")
	fun updateFood(@RequestBody request: Request): ResponseEntity<Any> {
		return try {
			foodService.updateFood(request)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@PatchMapping("/{foodUuid}/{has}/{price}")
	@ApiDocumentation(path = "doc/food/set-has-and-price-food.json")
	fun setHasAndPriceFood(
		@PathVariable foodUuid: UUID,
		@PathVariable has: Boolean,
		@PathVariable price: Double
	): ResponseEntity<Any> {
		return try {
			foodService.setHasAndPriceFood(foodUuid, has, price)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@DeleteMapping("/{foodUuid}")
	@ApiDocumentation(path = "doc/food/delete-food.json")
	fun deleteFood(
		@PathVariable foodUuid: UUID
	): ResponseEntity<Any> {
		return try {
			foodService.deleteFood(foodUuid)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

}
