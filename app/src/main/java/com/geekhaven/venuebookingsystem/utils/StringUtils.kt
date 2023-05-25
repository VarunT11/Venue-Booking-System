package com.geekhaven.venuebookingsystem.utils

import android.util.Patterns

fun getRolesText(isAdmin: Boolean, isAuthority: Boolean): String {
    val rolesList = ArrayList<String>(listOf("User"))
    if(isAdmin) rolesList.add("Admin")
    if(isAuthority) rolesList.add("Authority")
    return rolesList.joinToString(", ")
}

fun isValidEmail(email: String): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}
