package com.geekhaven.venuebookingsystem.models.data

import com.geekhaven.venuebookingsystem.api.models.BuildingResponse

data class Building(
  val id: String?,
  val name: String,
)

fun Building.toBuildingResponse() = BuildingResponse(
  id = id,
  name = name,
)

fun BuildingResponse.toBuilding() = Building(
  id = id,
  name = name,
)
