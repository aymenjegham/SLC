package com.aymen.slc.data.usecase.conferee.checkRestaurantAttendantExists

import com.aymen.slc.data.repository.conferee.ConfereeRepository
import javax.inject.Inject

class CheckRestaurantAttendantExistsUseCaseImpl @Inject constructor(
    private val confereeRepository: ConfereeRepository
) : CheckRestaurantAttendantExistsUseCase {

    override suspend fun invoke(confereeId: String) =
        confereeRepository.checkRestaurantAttendantExists(confereeId)

}