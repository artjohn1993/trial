package com.example.artjohn.blackfin.event

class BenefitsProgressBar
{
    var visible : Boolean
    var buttonVisible : Boolean
    constructor(flag : Boolean, buttonVisible : Boolean)
    {
        this.visible = flag
        this.buttonVisible = buttonVisible
    }
}