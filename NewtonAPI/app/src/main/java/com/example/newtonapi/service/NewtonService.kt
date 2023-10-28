package com.example.newtonapi.service

import com.example.newtonapi.model.Answer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface NewtonService {

    enum class Operation(val baseUrl: String) {
        SIMPLIFY("https://newton.now.sh/api/v2/simplify/"),
        INTEGRATE("https://newton.now.sh/api/v2/integrate/"),
        TANGENT_LINE("https://newton.now.sh/api/v2/tangent/"),
        SINE("https://newton.now.sh/api/v2/sin/"),
        COSINE("https://newton.now.sh/api/v2/cos/"),
        TANGENT("https://newton.now.sh/api/v2/tan/"),
        ARC_SINUS("https://newton.now.sh/api/v2/arcsin/"),
        ARC_COSINE("https://newton.now.sh/api/v2/arccos/"),
        ARC_TANGENT("https://newton.now.sh/api/v2/arctan/"),
        ABSOLUTE("https://newton.now.sh/api/v2/abs/"),
        LOGARITHM("https://newton.now.sh/api/v2/log/"),
        FACTOR("https://newton.now.sh/api/v2/factor/"),
        DERIVE("https://newton.now.sh/api/v2/derive/"),
        AREA_UNDER_CURVE("https://newton.now.sh/api/v2/area/")
    }

    @GET
    suspend fun getAnswer(@Url url: String?): Response<Answer>?

}
