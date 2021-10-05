package com.aymen.slc.data.usecase.user.login

import com.aymen.slc.data.repository.user.UserRepository
import javax.inject.Inject


class LoginUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : LoginUserUseCase {

    override suspend fun invoke(email: String, password: String) {

        val requestData = mutableMapOf(
            "email" to email,
            "password" to password
        )

        userRepository.login(requestData).let {
            userRepository.setSessionData(it.accessToken, it.user)

        }
    }

//    private fun ApiJsonMap(): JsonObject? {
//        var gsonObject = JsonObject()
//        try {
//            val jsonObj_ = JSONObject()
//            jsonObj_.put("key", "value")
//            jsonObj_.put("key", "value")
//            jsonObj_.put("key", "value")
//            val jsonParser = JsonParser()
//            gsonObject = jsonParser.parse(jsonObj_.toString()) as JsonObject
//
//            //print parameter
//            DebugLog.e("MY gson.JSON:  ", "AS PARAMETER  $gsonObject")
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        return gsonObject
//    }
}