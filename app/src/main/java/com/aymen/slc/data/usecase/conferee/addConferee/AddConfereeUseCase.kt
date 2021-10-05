package com.aymen.slc.data.usecase.conferee.addConferee

import com.aymen.slc.data.model.Conferee


interface AddConfereeUseCase {

    suspend operator fun invoke(conferee: Conferee)

}