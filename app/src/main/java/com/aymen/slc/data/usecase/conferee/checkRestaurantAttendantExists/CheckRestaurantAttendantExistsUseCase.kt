package com.aymen.slc.data.usecase.conferee.checkRestaurantAttendantExists


interface CheckRestaurantAttendantExistsUseCase {

    suspend operator fun invoke(confereeId: String): Boolean
}