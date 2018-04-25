package com.example.artjohn.blackfin.model

/**
 * Created by User on 25/04/2018.
 */
class ClientInfo {


    companion object {
        var array : ArrayList<Qoute.ClientsInformation>? = null
    }


    constructor(array : ArrayList<Qoute.ClientsInformation>)
    {
        ClientInfo.array = array
    }



}