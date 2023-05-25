package com.geekhaven.venuebookingsystem.api

import com.geekhaven.venuebookingsystem.api.models.*
import com.geekhaven.venuebookingsystem.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiHandler {

  // User related API Requests

  @POST("users/login/")
  suspend fun loginUsingCredentials(@Body loginCredentials: LoginCredentials): Response<UserApiResponse>

  @GET("users/details/all/")
  suspend fun getAllUsers(@Header("Auth-Token") authToken: String): Response<UserListApiResponse>

  @GET("users/details/{email}/")
  suspend fun getUserDetails(@Header("Auth-Token") authToken: String, @Path(value = "email", encoded = true) email: String): Response<UserApiResponse>

  @GET("users/search/")
  suspend fun searchUser(@Header("Auth-Token") authToken: String, @Query("name") name:String): Response<UserListApiResponse>

  @POST("users/add/")
  suspend fun addNewUser(@Header("Auth-Token") authToken: String, @Body userResponse: UserResponse): Response<UserApiResponse>

  @POST("users/update/")
  suspend fun updateUser(@Header("Auth-Token") authToken: String, @Body userResponse: UserResponse): Response<UserApiResponse>

  @POST("users/remove/")
  suspend fun removeUser(@Header("Auth-Token") authToken: String, @Body userResponse: UserResponse): Response<Unit>



  // Building related API Requests

  @GET("buildings/details/all/")
  suspend fun getAllBuildings(@Header("Auth-Token") authToken: String): Response<BuildingListApiResponse>

  @GET("buildings/details/{id}/")
  suspend fun getBuildingDetails(@Header("Auth-Token") authToken: String, @Path(value = "id", encoded = true) id:String): Response<BuildingApiResponse>

  @GET("buildings/search/")
  suspend fun searchBuilding(@Header("Auth-Token") authToken: String, @Query("name") name:String): Response<BuildingListApiResponse>

  @POST("buildings/add/")
  suspend fun addBuilding(@Header("Auth-Token") authToken: String, @Body buildingResponse: BuildingResponse): Response<BuildingApiResponse>

  @POST("buildings/update/")
  suspend fun updateBuilding(@Header("Auth-Token") authToken: String, @Body buildingResponse: BuildingResponse): Response<BuildingApiResponse>

  @POST("buildings/remove/")
  suspend fun removeBuilding(@Header("Auth-Token") authToken: String, @Body buildingResponse: BuildingResponse): Response<Unit>



  // Venue related API Requests

  @GET("venues/details/all/")
  suspend fun getAllVenues(@Header("Auth-Token") authToken: String): Response<VenueListApiResponse>

  @GET("venues/details/byBuilding/{building_id}/")
  suspend fun getVenuesByBuilding(@Header("Auth-Token") authToken: String, @Path(value="building_id", encoded = true) buildingId: String): Response<VenueListApiResponse>

  @GET("venues/details/byAuthority/{authority_id}/")
  suspend fun getVenuesByAuthority(@Header("Auth-Token") authToken: String, @Path(value="authority_id", encoded = true) authorityId: String): Response<VenueListApiResponse>

  @GET("venues/details/{venue_id}/")
  suspend fun getVenueDetails(@Header("Auth-Token") authToken: String, @Path(value = "venue_id", encoded = true) venueId: String): Response<VenueApiResponse>

  @GET("venues/search/")
  suspend fun getVenuesBySearch(@Header("Auth-Token") authToken: String, @Query("name") name: String): Response<VenueListApiResponse>

  @POST("venues/add/")
  suspend fun addVenue(@Header("Auth-Token") authToken: String, @Body venueResponse: VenueResponse): Response<VenueApiResponse>

  @POST("venues/update/")
  suspend fun updateVenue(@Header("Auth-Token") authToken: String, @Body venueResponse: VenueResponse): Response<VenueApiResponse>

  @POST("venues/remove/")
  suspend fun removeVenue(@Header("Auth-Token") authToken: String, @Body venueResponse: VenueResponse): Response<Unit>



  // Booking related API Requests

  @GET("bookings/details/byUser/{user_id}")
  suspend fun getBookingsByUser(@Header("Auth-Token") authToken: String, @Path(value = "user_id", encoded = true) userId: String): Response<BookingListApiResponse>

  @GET("bookings/details/byVenue/{venue_id}")
  suspend fun getBookingsByVenue(@Header("Auth-Token") authToken: String, @Path(value = "venue_id", encoded = true) venueId: String): Response<BookingListApiResponse>

  @GET("bookings/details/byVenue/{venue_id}/byDay")
  suspend fun getBookingsByVenueDay(@Header("Auth-Token") authToken: String, @Path(value = "venue_id", encoded = true) venueId: String, @Query("query_time") queryTime: String): Response<BookingListApiResponse>

  @GET("bookings/details/{booking_id}/")
  suspend fun getBooking(@Header("Auth-Token") authToken: String, @Path(value = "booking_id", encoded = true) bookingId: String): Response<BookingApiResponse>

  @GET("bookings/bookingRequests/byBooking/{booking_id}")
  suspend fun getBookingRequestsByBooking(@Header("Auth-Token") authToken: String, @Path(value = "booking_id", encoded = true) bookingId: String): Response<BookingRequestListApiResponse>

  @GET("bookings/bookingRequests/byReceiver/{receiver_id}")
  suspend fun getBookingRequestsByReceiver(@Header("Auth-Token") authToken: String, @Path(value = "receiver_id", encoded = true) receiverId: String): Response<BookingRequestListApiResponse>

  @GET("bookings/bookingRequests/details/{booking_request_id}")
  suspend fun getBookingRequest(@Header("Auth-Token") authToken: String, @Path(value = "booking_request_id", encoded = true) bookingRequestId: String): Response<BookingRequestApiResponse>

  @POST("bookings/add/")
  suspend fun addBooking(@Header("Auth-Token") authToken: String, @Body bookingResponse: BookingResponse): Response<BookingApiResponse>

  @POST("bookings/update/time/")
  suspend fun updateBookingTime(@Header("Auth-Token") authToken: String, @Body bookingResponse: BookingResponse): Response<BookingApiResponse>

  @POST("bookings/update/details/")
  suspend fun updateBookingDetails(@Header("Auth-Token") authToken: String, @Body bookingResponse: BookingResponse): Response<BookingApiResponse>

  @POST("bookings/cancel/")
  suspend fun cancelBooking(@Header("Auth-Token") authToken: String, @Body bookingResponse: BookingResponse): Response<BookingApiResponse>

  @POST("bookings/bookingRequests/update/")
  suspend fun updateBookingRequest(@Header("Auth-Token") authToken: String, @Body bookingRequestResponse: BookingRequestResponse): Response<BookingRequestApiResponse>



  // Comment related API Requests

  @GET("comments/byUser/{user_id}/")
  suspend fun getCommentsByUser(@Header("Auth-Token") authToken: String, @Path(value = "user_id", encoded = true) userId: String): Response<CommentListApiResponse>

  @GET("comments/byBooking/{booking_id}/")
  suspend fun getCommentsByBooking(@Header("Auth-Token") authToken: String, @Path(value = "booking_id", encoded = true) bookingId: String): Response<CommentListApiResponse>

  @GET("comments/{comment_id}")
  suspend fun getComment(@Header("Auth-Token") authToken: String, @Path(value = "comment_id", encoded = true) commentId: String): Response<CommentApiResponse>

  @POST("comments/add/")
  suspend fun addComment(@Header("Auth-Token") authToken: String, @Body commentResponse: CommentResponse): Response<CommentApiResponse>
}
