package com.example.artjohn.blackfin.event

/**
 * Created by User on 01/05/2018.
 */
class ErrorEvent {
    //region - Variables
    var message : String
    //endregion

    constructor(content : String) {
        this.message = content
    }
}