package org.pechblenda.helperboyrest.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.PrePersist
import javax.persistence.Table

import org.pechblenda.service.annotation.Key
import org.pechblenda.service.enum.DefaultValue

import java.util.Date
import java.util.Calendar
import java.util.UUID

@Entity
@Table(name = "foods")
class Food(
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var uuid: UUID,
	var name: String,
	var brand: String?,
	var buyDate: Date?,
	var expirationDay: Int,
	var count: Int?,
	var has: Boolean,
	var price: Double,
	var createDate: Date?,

	@Lob
	var icon: String?
) {

	constructor(): this(
		uuid = UUID.randomUUID(),
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

	@PrePersist
	fun onPrePersist() {
		this.createDate = Date()
	}

	@Key(name = "expirationDate", autoCall = true, defaultNullValue = DefaultValue.NULL)
	fun calculateExpirationDate(): Date? {
		return if (expirationDay != 0) {
			val calendar = Calendar.getInstance()
			calendar.time = buyDate
			calendar.add(Calendar.DAY_OF_MONTH, expirationDay!!)

			calendar.time
		} else {
			null
		}
	}

}