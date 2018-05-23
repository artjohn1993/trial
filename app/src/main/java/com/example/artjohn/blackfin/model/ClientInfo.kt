package com.example.artjohn.blackfin.model

/**
 * Created by User on 25/04/2018.
 */
class ClientInfo {
    companion object {
        var array : ArrayList<ClientsInformation> = ArrayList()
    }

    constructor(array : ClientsInformation) {
        if(ClientInfo.array.isEmpty()) {
            ClientInfo.array.add(array)
        }
        else {
            ClientInfo.array.set(0,array)
        }
    }
}