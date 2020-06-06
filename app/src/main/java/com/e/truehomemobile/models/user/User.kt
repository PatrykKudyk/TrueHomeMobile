package com.e.truehomemobile.models.user

class User(
    var userId: Long,
    var emailAddress: String,
    var username: String,
    var firstName: String,
    var lastName: String,
    var age: Int,
    var street: String,
    var streetNumber: String,
    var telephoneNumber: String
) {
}