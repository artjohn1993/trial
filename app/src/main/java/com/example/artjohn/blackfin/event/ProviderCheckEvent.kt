package com.example.artjohn.blackfin.event

class ProviderCheckEvent {
    var providerId : Int
    var providerStatus : Boolean

    constructor(providerId : Int, providerStatus : Boolean) {
        this.providerId = providerId
        this.providerStatus = providerStatus
    }
}