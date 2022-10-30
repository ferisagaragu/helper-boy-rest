package org.pechblenda.helperboyrest.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import org.pechblenda.service.Request
import org.pechblenda.service.Response
import org.pechblenda.helperboyrest.repository.IFoodRepository
import org.pechblenda.helperboyrest.service.`interface`.IFoodService
import org.pechblenda.exception.NotFoundException
import org.pechblenda.helperboyrest.entity.Food
import org.pechblenda.service.helper.Validation
import org.pechblenda.service.helper.ValidationType
import org.pechblenda.service.helper.Validations
import org.pechblenda.style.Color
import org.pechblenda.style.enums.CategoryColor
import org.pechblenda.service.enum.IdType
import org.pechblenda.service.helper.EntityParse
import org.pechblenda.service.helper.ProtectField
import org.pechblenda.service.helper.ProtectFields

import java.util.UUID
import java.util.Date

import java.net.URLEncoder

@Service
class FoodService(
	val foodRepository: IFoodRepository,
	val response: Response,
	val color: Color
): IFoodService {

	@Transactional(readOnly = true)
	override fun findAllFoodsByUuid(foodUuid: UUID?, has: Boolean?): ResponseEntity<Any> {
		if (foodUuid != null) {
			val food = foodRepository.findById(foodUuid).orElseThrow {
				NotFoundException("No se encontro el alimento")
			}

			return response.toMap(food).ok()
		}

		val detail = mutableMapOf<String, Double?>()
		detail["total"] = foodRepository.sumTotal() ?: 0.0

		if (has != null) {
			return response.toListMap(foodRepository.findByHas(has)).ok(detail)
		}

		return response.toListMap(foodRepository.findAll()).ok(detail)
	}

	@Transactional
	override fun createFood(request: Request): ResponseEntity<Any> {
		request.validate(getValidations(false))
		val food = request.to<Food>(Food::class)
		val materialColor = color.getMaterialColor(CategoryColor.MATERIAL_500)

		if (food.icon == null) {
			food.icon = "http://localhost:8080/rest/auth/generate-profile-image/" +
				"${food.name.substring(0, 2).uppercase()}/" +
				"${URLEncoder.encode(materialColor.color, "UTF-8")}/" +
				"${URLEncoder.encode(materialColor.background, "UTF-8")}"
		}

		food.has = false
		food.brand = food.brand ?: "Mercantil"
		foodRepository.save(food)

		return response.created()
	}

	@Transactional
	override fun updateFood(request: Request): ResponseEntity<Any> {
		request.validate(getValidations(true))
		request.merge<Food>(
			EntityParse(
				"uuid",
				foodRepository,
				IdType.UUID
			),
			ProtectFields(
				ProtectField("has"),
				ProtectField("createDate")
			)
		)

		return response.ok()
	}

	@Transactional
	override fun setHasAndPriceFood(uuid: UUID, has: Boolean, price: Double): ResponseEntity<Any> {
		val food = foodRepository.findById(uuid).orElseThrow {
			NotFoundException("No se encontro el alimento")
		}

		food.has = has

		if (has) {
			food.buyDate = Date()
			food.price = price
		}

		return response.ok()
	}

	@Transactional
	override fun deleteFood(foodUuid: UUID): ResponseEntity<Any> {
		val food = foodRepository.findById(foodUuid).orElseThrow {
			NotFoundException("No se encontro el alimento")
		}

		foodRepository.delete(food)

		return response.ok()
	}

	private fun getValidations(isUpdate: Boolean): Validations {
		if (isUpdate) {
			return Validations(
				Validation(
					"uuid",
					"El 'uuid' es requerido",
					ValidationType.EXIST,
					ValidationType.NOT_NULL,
					ValidationType.NOT_BLANK
				),
				Validation(
					"name",
					"El 'name' es requerido",
					ValidationType.EXIST,
					ValidationType.NOT_NULL,
					ValidationType.NOT_BLANK
				),
				Validation(
					"price",
					"El 'price' es requerido",
					ValidationType.EXIST,
					ValidationType.NOT_NULL,
					ValidationType.NOT_BLANK,
					ValidationType.NUMBER
				)
			)
		}

		return Validations(
			Validation(
				"name",
				"El 'name' es requerido",
				ValidationType.EXIST,
				ValidationType.NOT_NULL,
				ValidationType.NOT_BLANK
			),
			Validation(
				"price",
				"El 'price' es requerido",
				ValidationType.EXIST,
				ValidationType.NOT_NULL,
				ValidationType.NOT_BLANK,
				ValidationType.NUMBER
			)
		)
	}

}
