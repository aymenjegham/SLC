package com.aymen.slc.data.usecase.conferee.getConfereeById

import com.aymen.slc.data.model.Conferee
import com.aymen.slc.data.repository.conferee.ConfereeRepository
import javax.inject.Inject


class GetConfereeByIdUserUseCaseImpl @Inject constructor(
    private val confereeRepository: ConfereeRepository
) : GetConfereeByIdUseCase {

    override suspend fun invoke(id: String): Conferee {

        val requestData = mutableMapOf(
            "inscriptionId" to id
        )

      return  confereeRepository.getConfereeById(requestData)
    }
}