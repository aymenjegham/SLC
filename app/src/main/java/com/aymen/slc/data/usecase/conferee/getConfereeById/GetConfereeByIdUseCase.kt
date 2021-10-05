package com.aymen.slc.data.usecase.conferee.getConfereeById

import com.aymen.slc.data.model.Conferee

interface GetConfereeByIdUseCase {

    suspend operator fun invoke(id: String) : Conferee
}