package com.example.artjohn.blackfin.event

/**
 * Created by User on 30/04/2018.
 */
class ProductPremium {

    var clientInfo : String
    var qoute : String
    var position : Int

    constructor(clientInfo : String,
                qoute : String,
                position : Int) {
        this.clientInfo = clientInfo
        this.qoute = qoute
        this.position = position
    }

}