package com.example.artjohn.blackfin.model

import okhttp3.Response

/**
 * Created by User on 27/04/2018.
 */
object QouteRequest {
    data class Result(
            var data : Data
    )

    data class Data(
            var response : Response
    )

    data class Response(
            var status : String
    )
}

