package com.aymen.slc.data.usecase.session.isConnected

import kotlinx.coroutines.flow.Flow

interface IsUserConnectedUseCase {

     operator fun  invoke(): Flow<Boolean>

}