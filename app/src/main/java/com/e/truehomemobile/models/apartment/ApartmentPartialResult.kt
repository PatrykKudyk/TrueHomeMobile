package com.e.truehomemobile.models.apartment

import com.e.truehomemobile.models.user.User

class ApartmentPartialResult(
    var apartmentId: Long,
    var apartmentName: String,
    var apartmentCity: String,
    var apartmentStreet: String,
    var apartmentStreetNumber: String,
    var apartmentZipCode: String,
    var apartmentPrice: Double,
    var apartmentDescription: String,
    var apartmentImage: String,
    var apartmentImages: Array<String>,
    var user: User
) {
}