package parking

var parkCapacity = 0
var spotsCreated = false
val parkMap = mutableMapOf<Int, Car>()
val listOfQuery = mutableListOf<String>()


fun main() {

    while (true) {
        val userInput = readln().split(" ")
        val userCommand = userInput[0]

        when (userCommand) {

            "create" -> createPark(userInput[1].toInt())

            "park" -> parkCar(userInput[1], userInput[2], findVacantSpot())

            "leave" -> leavePark(userInput[1].toInt())

            "status" -> printStatus()

            "reg_by_color" -> reg_by_color(userInput[1])

            "spot_by_color" -> spot_by_color(userInput[1])

            "spot_by_reg" -> spot_by_reg(userInput[1])

            "exit" -> break
        }
    }
}

fun findVacantSpot(): Int {
    var result = 0
    if (spotsCreated && parkCapacity > 0) {
        for (i in 1..parkCapacity) {
            if (!parkMap.containsKey(i)) {
                result = i
                break
            }
        }
    }
    return result
}

fun parkCar(carNumber: String, color: String, vacantSpot: Int) {
    if (checkCreated()) {
        if (vacantSpot != 0) {
            parkMap[vacantSpot] = Car(carNumber, color)
            println("$color car parked in spot $vacantSpot.")
        } else println("Sorry, the parking lot is full.")
    }
}

fun leavePark(spot: Int) {
    if (checkCreated()) {
        if (parkMap.containsKey(spot)) {
            parkMap.remove(spot)
            println("Spot $spot is free.")
        } else {
            println("There is no car in spot $spot.")
        }
    }
}

fun checkCreated(): Boolean {
    if (spotsCreated) {
        listOfQuery.clear()
        return true
    } else {
        println("Sorry, a parking lot has not been created.")
        listOfQuery.clear()
        return false
    }
}

fun printStatus() {
    if (checkCreated()) {
        if (!spotsCreated || parkMap.isEmpty()) {
            println("Parking lot is empty.")
        } else {
            for ((key, value) in parkMap) {
                println("$key ${value.number} ${value.color}")
            }
        }
    }
}

fun createPark(capacity: Int) {
    if (!spotsCreated) {
        parkCapacity = capacity
        spotsCreated = true
    } else {
        parkMap.clear()
        parkCapacity = capacity
    }
    println("Created a parking lot with $parkCapacity spots.")

}

fun reg_by_color(color: String) {
    if (checkCreated()) {
        for (cars in parkMap.values) {
            if (cars.color.lowercase() == color.lowercase())
                listOfQuery.add(cars.number)
        }
        if (listOfQuery.isEmpty()) println("No cars with color $color were found.")
        else println(listOfQuery.joinToString(", "))

    }
}

fun spot_by_color(color: String) {
    if (checkCreated()) {
        for ((key, value) in parkMap) {
            if (value.color.lowercase() == color.lowercase())
                listOfQuery.add(key.toString())
        }
        if (listOfQuery.isEmpty()) println("No cars with color $color were found.")
        else println(listOfQuery.joinToString(", "))

    }
}

fun spot_by_reg(carNumber: String) {
    if (checkCreated()) {
        for ((key, value) in parkMap) {
            if (value.number.lowercase() == carNumber.lowercase())
                listOfQuery.add(key.toString())
        }
        if (listOfQuery.isEmpty())
            println("No cars with registration number $carNumber were found.")
        else
            println(listOfQuery.joinToString(", "))
    }
}