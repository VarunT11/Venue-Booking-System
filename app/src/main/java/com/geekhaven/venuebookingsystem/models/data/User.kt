package com.geekhaven.venuebookingsystem.models.data

import com.geekhaven.venuebookingsystem.api.models.UserResponse

data class User(
  val email: String,
  val name: String,
  val parentEmail: String?,
  val requireParentPermission: Boolean,
  val isAdmin: Boolean,
  val isAuthority: Boolean,
  val photoUrl: String? = null,
)

fun UserResponse.toUser() = User(
  email = email,
  name = name,
  parentEmail = parent,
  requireParentPermission = requireParentPermission,
  isAdmin = isAdmin,
  isAuthority = isAuthority,
)

fun User.toUserResponse() = UserResponse(
  email = email,
  name = name,
  parent = parentEmail ?: "",
  requireParentPermission = requireParentPermission,
  isAdmin = isAdmin,
  isAuthority = isAuthority,
)
