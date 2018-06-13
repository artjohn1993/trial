package com.example.artjohn.blackfin.model

object Benefit {
    data class Result(
            var data : Data
    )
    data class Data(
            var benefits : List<BenefitsList>
    )
    data class BenefitsList(
            var benefitId : Int,
            var name : String
    )
}