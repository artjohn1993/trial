package com.example.artjohn.blackfin.model

/**
 * Created by User on 26/04/2018.
 */
class UserInformation {
    companion object {
        var array : ArrayList<Login.Signup> = ArrayList()
    }

    constructor(array : Login.Signup) {
        UserInformation.array.add(array)
    }
}
