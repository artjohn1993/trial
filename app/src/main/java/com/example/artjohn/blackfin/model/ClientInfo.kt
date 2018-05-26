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
            var exist : Boolean = false
            var index : Int = 0
            for (item in 0 until ClientInfo.array.size) {
                if (ClientInfo.array[item].clientId == array.clientId) {
                    exist = true
                    index = item
                }
            }
            if (exist) {
                ClientInfo.array[index] = array
            }
            else {
                ClientInfo.array.add(array)
            }
        }
    }
}