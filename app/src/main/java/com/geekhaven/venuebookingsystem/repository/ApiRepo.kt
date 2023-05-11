package com.geekhaven.venuebookingsystem.repository

import android.util.Log
import com.geekhaven.venuebookingsystem.api.ApiHandler
import com.geekhaven.venuebookingsystem.api.models.*
import com.geekhaven.venuebookingsystem.exceptions.ServerException
import com.geekhaven.venuebookingsystem.exceptions.SimpleApiException
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response

class ApiRepo(private val apiHandler: ApiHandler) {

  companion object { val LOG_TAG: String? = ApiRepo::class.simpleName }

  private var authToken: String = ""
  fun updateAuthToken(token: String) { authToken = token }



  // User related API Handing

  suspend fun getUserDetails(email: String) =
    apiHandler.getUserDetails(authToken, email)
      .let { getResponse(it) { it.body()?.userResponse } }

  suspend fun getUserDetailsBySearch(name: String) =
    apiHandler.searchUser(authToken, name)
      .let { getResponse(it) { it.body()?.userResponses } }

  suspend fun loginUsingCredentials(authToken: String) =
    apiHandler.loginUsingCredentials(LoginCredentials(authToken))
      .let { getResponse(it) { it.body()?.userResponse } }

  suspend fun addNewUser(userResponse: UserResponse) =
    apiHandler.addNewUser(authToken, userResponse)
      .let { getResponse(it) { it.body()?.userResponse } }

  suspend fun updateUser(userResponse: UserResponse) =
    apiHandler.updateUser(authToken, userResponse)
      .let { getResponse(it) { it.body()?.userResponse } }

  suspend fun removeUser(userResponse: UserResponse) =
    getResponse(apiHandler.removeUser(authToken, userResponse)) { }



  // Building related API Handing

  suspend fun getAllBuildings() =
    apiHandler.getAllBuildings(authToken)
      .let { getResponse(it) { it.body()?.buildingResponses } }

  suspend fun getBuildingDetails(id: String) =
    apiHandler.getBuildingDetails(authToken, id)
      .let { getResponse(it) { it.body()?.buildingResponse } }

  suspend fun searchBuildingsByName(name: String) =
    apiHandler.searchBuilding(authToken, name)
      .let { getResponse(it) { it.body()?.buildingResponses} }

  suspend fun addNewBuilding(buildingResponse: BuildingResponse) =
    apiHandler.addBuilding(authToken, buildingResponse)
      .let { getResponse(it) { it.body()?.buildingResponse } }

  suspend fun updateBuilding(buildingResponse: BuildingResponse) =
    apiHandler.updateBuilding(authToken, buildingResponse)
      .let { getResponse(it) { it.body()?.buildingResponse } }

  suspend fun removeBuilding(buildingResponse: BuildingResponse) =
    getResponse(apiHandler.removeBuilding(authToken, buildingResponse)) { }



  // Venue related API Handling

  suspend fun getVenuesByBuilding(buildingId: String) =
    apiHandler.getVenuesByBuilding(authToken, buildingId)
      .let { getResponse(it) { it.body()?.venueResponseList } }

  suspend fun getVenuesByAuthority(authorityId: String) =
    apiHandler.getVenuesByAuthority(authToken, authorityId)
      .let { getResponse(it) { it.body()?.venueResponseList } }

  suspend fun getVenueDetails(venueId: String) =
    apiHandler.getVenueDetails(authToken, venueId)
      .let { getResponse(it) { it.body()?.venueResponse } }

  suspend fun getVenuesBySearch(name: String) =
    apiHandler.getVenuesBySearch(authToken, name)
      .let { getResponse(it) { it.body()?.venueResponseList } }

  suspend fun addVenue(venueResponse: VenueResponse) =
    apiHandler.addVenue(authToken, venueResponse)
      .let { getResponse(it) { it.body()?.venueResponse } }

  suspend fun updateVenue(venueResponse: VenueResponse) =
    apiHandler.updateVenue(authToken, venueResponse)
      .let { getResponse(it) { it.body()?.venueResponse } }

  suspend fun removeVenue(venueResponse: VenueResponse) =
    getResponse(apiHandler.removeVenue(authToken, venueResponse)) {  }



  // Booking related API Handling

  suspend fun getBookingsByUser(userId: String) =
    apiHandler.getBookingsByUser(authToken, userId)
      .let { getResponse(it) { it.body()?.bookingResponseList } }

  suspend fun getBookingsByVenue(venueId: String) =
    apiHandler.getBookingsByVenue(authToken, venueId)
      .let { getResponse(it) { it.body()?.bookingResponseList } }

  suspend fun getBookingsByVenueDay(venueId: String, queryTime: String) =
    apiHandler.getBookingsByVenueDay(authToken, venueId, queryTime)
      .let { getResponse(it) { it.body()?.bookingResponseList } }

  suspend fun getBooking(bookingId: String) =
    apiHandler.getBooking(authToken, bookingId)
      .let { getResponse(it) { it.body()?.bookingResponse } }

  suspend fun getBookingRequestsByBooking(bookingId: String) =
    apiHandler.getBookingRequestsByBooking(authToken, bookingId)
      .let { getResponse(it) { it.body()?.bookingRequestResponseList } }

  suspend fun getBookingRequestsByReceiver(receiverId: String) =
    apiHandler.getBookingRequestsByReceiver(authToken, receiverId)
      .let { getResponse(it) { it.body()?.bookingRequestResponseList } }

  suspend fun getBookingRequest(bookingRequestId: String) =
    apiHandler.getBookingRequest(authToken, bookingRequestId)
      .let { getResponse(it) { it.body()?.bookingRequestResponse } }

  suspend fun addBooking(bookingResponse: BookingResponse) =
    apiHandler.addBooking(authToken, bookingResponse)
      .let { getResponse(it) { it.body()?.bookingResponse } }

  suspend fun updateBookingTime(bookingResponse: BookingResponse) =
    apiHandler.updateBookingTime(authToken, bookingResponse)
      .let { getResponse(it) { it.body()?.bookingResponse } }

  suspend fun updateBookingDetails(bookingResponse: BookingResponse) =
    apiHandler.updateBookingDetails(authToken, bookingResponse)
      .let { getResponse(it) { it.body()?.bookingResponse } }

  suspend fun cancelBooking(bookingResponse: BookingResponse) =
    apiHandler.cancelBooking(authToken, bookingResponse)
      .let { getResponse(it) { it.body()?.bookingResponse } }

  suspend fun updateBookingRequest(bookingRequestResponse: BookingRequestResponse) =
    apiHandler.updateBookingRequest(authToken, bookingRequestResponse)
      .let { getResponse(it) { it.body()?.bookingRequestResponse } }



  // Comment related API Handling

  suspend fun getCommentsByUser(userId: String) =
    apiHandler.getCommentsByUser(authToken, userId)
      .let { getResponse(it) { it.body()?.commentsList } }

  suspend fun getCommentsByBooking(bookingId: String) =
    apiHandler.getCommentsByBooking(authToken, bookingId)
      .let { getResponse(it) { it.body()?.commentsList } }

  suspend fun getComment(commentId: String) =
    apiHandler.getComment(authToken, commentId)
      .let { getResponse(it) { it.body()?.commentResponse } }

  suspend fun addComment(commentResponse: CommentResponse) =
    apiHandler.addComment(authToken, commentResponse)
      .let { getResponse(it) { it.body()?.commentResponse } }



  private fun <T, P> getResponse(response: Response<P>, onSuccess: () -> T): T {
    try {
      if (!response.isSuccessful) {
        val apiException = getResponseException(response.errorBody())
        throw apiException
      }
      return onSuccess()
    } catch (e: Exception){
      if(e is SimpleApiException) throw  e
      Log.d(LOG_TAG, "Server Error: ${e.message}")
      throw ServerException()
    }
  }

  private fun getResponseException(responseBody: ResponseBody?) =
    Gson().fromJson(responseBody?.charStream(), SimpleApiException::class.java)

}
