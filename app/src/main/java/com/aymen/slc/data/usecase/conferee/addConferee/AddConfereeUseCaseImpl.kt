package com.aymen.slc.data.usecase.conferee.addConferee



import com.aymen.slc.data.model.Conferee
import com.aymen.slc.data.repository.conferee.ConfereeRepository
import javax.inject.Inject

class AddConfereeUseCaseImpl @Inject constructor(
    private val repository: ConfereeRepository
) : AddConfereeUseCase {

    override suspend fun invoke(conferee: Conferee) =
        repository.addConferee(conferee)


}