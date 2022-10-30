package org.pechblenda.helperboyrest.entity

import org.junit.jupiter.api.Test

import org.springframework.boot.test.context.SpringBootTest

import java.util.UUID
import java.util.Date

@SpringBootTest
class FoodTest {

	@Test
	fun `instance food entity`() {
		val uuid = UUID.randomUUID()
		val food = Food(
			uuid = uuid,
			name = "",
			brand = null,
			buyDate = null,
			expirationDay = 0,
			count = null,
			has = false,
			price = 0.0,
			createDate = null,
			icon = null
		)

		assert(food.uuid == uuid)
		assert(food.name.isEmpty())
		assert(food.brand == null)
		assert(food.buyDate == null)
		assert(food.expirationDay == 0)
		assert(food.count == null)
		assert(!food.has)
		assert(food.price == 0.0)
		assert(food.createDate == null)
		assert(food.icon == null)
	}

	@Test
	fun `use food on pre persist method`() {
		val food = Food()
		assert(food.onPrePersist() == Unit)
	}

	@Test
	fun `use food sets`() {
		val uuid = UUID.randomUUID()
		val date = Date()
		val food = Food()
		val iconLink = "http://fake-photo"

		food.uuid = uuid
		food.name = "name"
		food.brand = "brand"
		food.buyDate = date
		food.expirationDay = 1
		food.count = 1
		food.has = true
		food.price = 1.0
		food.createDate = date
		food.icon = iconLink

		assert(food.uuid == uuid)
		assert(food.name == "name")
		assert(food.brand == "brand")
		assert(food.buyDate == date)
		assert(food.expirationDay == 1)
		assert(food.count == 1)
		assert(food.has)
		assert(food.price == 1.0)
		assert(food.createDate == date)
		assert(food.icon == iconLink)
	}

	@Test
	fun `validate calculateExpirationDate when expirationDay is zero`() {
		val food = Food()
		food.expirationDay = 0

		assert(food.calculateExpirationDate() == null)
	}

	@Test
	fun `validate calculateExpirationDate when expirationDay is not zero`() {
		val food = Food()
		food.expirationDay = 2
		food.buyDate = Date()

		assert(food.calculateExpirationDate() is Date)
	}

}