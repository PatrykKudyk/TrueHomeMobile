package com.e.truehomemobile.models.apartment

class ApartmentWithImages(
    var apartmentId: Long,
    var apartmentName: String,
    var apartmentCity: String,
    var apartmentStreet: String,
    var apartmentStreetNumber: String,
    var apartmentZipCode: String,
    var apartmentPrice: Int,
    var apartmentDescription: String,
    var apartmentImages: Array<String>
) {
}