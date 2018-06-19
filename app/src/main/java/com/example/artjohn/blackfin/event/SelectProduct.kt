package com.example.artjohn.blackfin.event

class SelectProduct {
    var groupID : Int
    var name : String
    constructor(groupID : Int, name : String) {
        this.groupID = groupID
        this.name = name
    }
}