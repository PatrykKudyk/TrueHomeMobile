package com.e.truehomemobile.models.authorization

data class RegistrationRequest(val login: String,
                               val password: String,
                               val email: String) {
}