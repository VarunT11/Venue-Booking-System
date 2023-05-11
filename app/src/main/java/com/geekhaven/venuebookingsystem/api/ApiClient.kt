package com.geekhaven.venuebookingsystem.api

import com.geekhaven.venuebookingsystem.config.AppConfig.BACKEND_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
  private val retrofit = Retrofit.Builder()
      .baseUrl(BACKEND_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  val apiHandler: ApiHandler = retrofit.create(ApiHandler::class.java)
}
