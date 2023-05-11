package com.geekhaven.venuebookingsystem.api.models

import com.google.gson.annotations.SerializedName

data class BuildingResponse(
  @SerializedName("id")
  val id: String? = null,

  @SerializedName("name")
  val name: String,
)

data class BuildingApiResponse(
  @SerializedName("response_data")
  val buildingResponse: BuildingResponse
)

data class BuildingListApiResponse(
  @SerializedName("response_data")
  val buildingResponses: List<BuildingResponse>
)
