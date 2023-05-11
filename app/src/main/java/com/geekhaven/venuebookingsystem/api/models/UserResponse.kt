package com.geekhaven.venuebookingsystem.api.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
  @SerializedName("email")
  val email: String,

  @SerializedName("name")
  val name: String,

  @SerializedName("parent")
  val parent: String,

  @SerializedName("require_parent_permission")
  val requireParentPermission: Boolean,

  @SerializedName("is_admin")
  val isAdmin: Boolean,

  @SerializedName("is_authority")
  val isAuthority: Boolean,
)

data class UserApiResponse(
  @SerializedName("response_data")
  val userResponse: UserResponse
)

data class UserListApiResponse(
  @SerializedName("response_data")
  val userResponses: List<UserResponse>
)

data class LoginCredentials(
  @SerializedName("credential")
  val credential: String
)
