package com.example.artjohn.blackfin.model

/**
 * Created by User on 26/04/2018.
 */
class UserInformation {
    var token : String = ""
    var id : String = ""

    constructor(token : String,id : String)
    {
        this.id = id
        this.token = token
    }

    constructor()

    fun returnToken() : String {
        return this.token
    }
    fun returnId() : String{
        return this.id
    }

}
