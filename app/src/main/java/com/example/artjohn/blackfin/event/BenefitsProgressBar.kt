package com.example.artjohn.blackfin.event

class BenefitsProgressBar {
    //region - Variables
    var visible : Boolean
    var buttonVisible : Boolean
    //endregion

    constructor(flag : Boolean, buttonVisible : Boolean) {
        this.visible = flag
        this.buttonVisible = buttonVisible
    }

}