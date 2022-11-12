package org.pechblenda.helperboyrest.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Table

import java.util.Date
import java.util.UUID

@Entity
@Table(name = "foods")
class Food(
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var uuid: UUID,
	var name: String,
	var has: Boolean,
	var price: Double,
	var createDate: Date?,
) {

	constructor(): this(
		uuid = UUID.randomUUID(),
		name = "",
		has = false,
		price = 0.0,
		createDate = null
	)

	@PrePersist
	fun onPrePersist() {
		this.createDate = Date()
	}

}