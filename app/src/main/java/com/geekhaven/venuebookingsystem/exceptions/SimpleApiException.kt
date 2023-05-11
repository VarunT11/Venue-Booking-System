package com.geekhaven.venuebookingsystem.exceptions

import com.google.gson.annotations.SerializedName

data class SimpleApiException(
  @SerializedName("response_message")
  override val message: String
): Exception()
