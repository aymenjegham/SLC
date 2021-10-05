package com.aymen.slc.data.usecase.conferee.getRestaurantAttendants

import kotlinx.coroutines.flow.Flow

interface GetRestaurantAttendantsCountUseCase {

    operator fun invoke(): Flow<Int>
}