package com.aymen.slc.data.usecase.conferee.getRestaurantAttendants


import com.aymen.slc.data.repository.conferee.ConfereeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRestaurantAttendantsCountUseCaseImpl @Inject constructor(private val confereeRepository: ConfereeRepository) :
    GetRestaurantAttendantsCountUseCase {

    override fun invoke(): Flow<Int> = confereeRepository.getRestaurantAttendantsCount()
}