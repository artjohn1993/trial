package com.example.artjohn.blackfin.event

class Conversion {
    companion object {
        fun excess(data : String) : Int {
            var converted : Int = 0
            if(!data.substringBefore(" ").equals("Nil")) {
                converted = data.substringBefore(" ").toInt()
            }
            return converted
        }

        fun loading(data : String) : Double {
            var converted = data.substringBefore("%").toDouble()
            converted = LoadingPercentage(converted).calculate()
            return converted
        }

        fun coverAmount(data : String) : Double {
            var amount : Double = 0.0
            try {
                amount = data.toDouble()
            } catch (e : Exception) {
                amount = 0.0
            }
            return amount
        }

        fun calPeriod(data : String) : Int {
            var cal : Int = 1
            when (data) {
                "Yearly Renewable" -> {
                    cal = 1
                }
                "Level (10 Years)" -> {
                    cal = 10
                }
                "Level (15 Years)" -> {
                    cal = 15
                }
                "Level (To Age 65)" -> {
                    cal = 65
                }
                "Level (To Age 70)" -> {
                    cal = 70
                }
                "Level (To Age 80)" -> {
                    cal = 80
                }
                "Level (To Age 85)" -> {
                    cal = 85
                }
                "Level (To Age 90)" -> {
                    cal = 90
                }
                "Level (To Age 100)" -> {
                    cal = 100
                }
            }
            return cal
        }

        fun yearPeriod(data : String) : Int {
            return data.substringBefore(" Years").toInt()
        }

        fun termPeriod(data : String) : String {
            var converted : String = ""
            when(data) {
                "Fixed Term" -> {
                    converted = "Term"
                }
                "To Age" -> {
                    converted = "Age"
                }
            }
            return converted
        }

        fun occupationType(data : String) : String {
            var converted : String = ""
            when(data) {
                "Any Occupation" -> {
                   converted = "AnyOccupation"
                }
                "Own Occupation" -> {
                    converted = "OwnOccupation"
                }
            }
            return converted
        }

        fun waitPeriod(data : String) : Int {
            return data.substringAfter("week ").toInt()
        }

        fun benefitPeriod(data : String) : Int {
            var converted : Int = 0
            when(data) {
                "year 1" -> {
                    converted = 1
                }
                "year 2" -> {
                    converted = 2
                }
                "year 5" -> {
                    converted = 5
                }
                "age 65" -> {
                    converted = 65
                }
                "age 70" -> {
                    converted = 70
                }
                else -> {
                    converted = 0
                }
            }
            return converted
        }
    }
}

class Position {
    companion object {
        fun excess(data : Int) : Int{
            var position : Int = 0
            when(data) {
                0 -> {
                    position = 0
                }
                250 -> {
                    position = 1
                }
                500 -> {
                    position = 2
                }
                1000 -> {
                    position = 3
                }
                2000 -> {
                    position = 4
                }
                3000 -> {
                    position = 5
                }
                4000 -> {
                    position = 6
                }
                5000 -> {
                    position = 7
                }
                6000 -> {
                    position = 8
                }
                10000 -> {
                    position = 9
                }
            }
            return position
        }

        fun loading(data : Double) : Int {
            var position : Int = 0
            when(data) {
                1.0 -> {
                    position = 0
                }
                1.5 -> {
                    position = 1
                }
                1.75 -> {
                    position = 2
                }
                2.0 -> {
                    position = 3
                }
                2.25 -> {
                    position = 4
                }
                2.5 -> {
                    position = 5
                }
                2.75 -> {
                    position = 6
                }
                3.0 -> {
                    position = 7
                }
                3.5 -> {
                    position = 8
                }
                4.0 -> {
                    position = 9
                }
                1.0 -> {
                    position = 10
                }
                5.0 -> {
                    position = 11
                }
                6.0 -> {
                    position = 12
                }
            }
            return position
        }

        fun calPeriod(data : Int) : Int {
            var position : Int = 0
            when(data) {
                1 -> {
                    position = 0
                }
                10 -> {
                    position = 1
                }
                15 -> {
                    position = 2
                }
                65 -> {
                    position = 3
                }
                70 -> {
                    position = 4
                }
                80 -> {
                    position = 5
                }
                85 -> {
                    position = 6
                }
                90 -> {
                    position = 7
                }
                100 -> {
                    position = 8
                }
            }
            return position
        }

        fun termbenefit(data : String) : Int {
            var position : Int = 0
            when(data) {
                "Term" -> {
                    position = 0
                }
                "Age" -> {
                    position = 1
                }
            }
            return position
        }

        fun yearPeriod(data : Int) : Int {
            var position : Int = 0
            if (data in 1..30) {
                for (x in 1..30) {
                    if (data == x) {
                        position = x
                        break
                    }
                }
            }
            else {
                for (x in 31..70) {
                    if (data == x) {
                        position = position++
                        break
                    }
                }
            }

            return position
        }

        fun occupationType(data : String) : Int {
            var position : Int = 0
            when(data) {
                "OwnOccupation" -> {
                    position = 0
                }
                "AnyOccupation" -> {
                    position = 1
                }
            }
            return position
        }

        fun waitPeriod(data : Int) : Int {
            var position : Int = 0
            when(data) {
                4 -> {
                    position = 0
                }
                8 -> {
                    position = 1
                }
                13 -> {
                    position = 2
                }
                26 -> {
                    position = 3
                }
                52 -> {
                    position = 4
                }
                104 -> {
                    position = 5
                }
            }
            return position
        }

        fun benefitPeriod(data : Int) : Int {
            var position : Int = 0
            when(data) {
                0 -> {
                    position = 0
                }
                1 -> {
                    position = 1
                }
                2 -> {
                    position = 2
                }
                5 -> {
                    position = 3
                }
                65 -> {
                    position = 4
                }
                70 -> {
                    position = 5
                }
            }
            return position
        }
    }
}