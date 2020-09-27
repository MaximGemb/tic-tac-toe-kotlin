package tictactoe

import kotlin.math.abs

var turn = 1
fun main() {
    val chars: CharArray = readLine()!!.toCharArray()
//    val chars: String = "_________"
    movement(chars)
}

fun setka(str: CharArray) {
    var x = 0
    println("---------")
    for (i in 0..2) {
        print("| ")
        for (j in 0..2) {
            print("${if (str[x] == '_') ' ' else str[x]} ")
            ++x
        }
        print("|\n")
    }
    println("---------")
}

fun analis(charArray: CharArray) {
    val str = String(charArray)
    val line1 = str.substring(0..2)
    val line2 = str.substring(3..5)
    val line3 = str.substring(6..8)
    val diag1: String = "${str[0]}${str[4]}${str[8]}"
    val diag2: String = "${str[2]}${str[4]}${str[6]}"
    val row1: String = "${str[0]}${str[3]}${str[6]}"
    val row2: String = "${str[1]}${str[4]}${str[7]}"
    val row3: String = "${str[2]}${str[5]}${str[8]}"
    val have: (String, String) -> Boolean = { str1, str2 -> str1.contains(str2) }
    val howMachX: Int = str.replace("O", "").replace("_", "").length
    val howMachO: Int = str.replace("X", "").replace("_", "").length
    val howMachAll: Int = abs(howMachX - howMachO)
    val howMach_: Boolean = have(str, "_")
    val xxx = "XXX"
    val ooo = "OOO"
    val arrStr = arrayOf(line1, line2, line3, diag1, diag2, row1, row2, row3)
    var playerX: Boolean = false
    var playerO: Boolean = false
    for (x in 0..7) {
        if (have(arrStr[x], xxx)) playerX = true
        if (have(arrStr[x], ooo)) playerO = true
    }
    //who wins
    when {
        !playerX && !playerO && !howMach_ -> println("Draw")
        playerX && playerO && !howMach_ -> println("Impossible")
        !playerX && !playerO && howMachAll >= 2 -> println("Impossible")
        !playerX && !playerO && howMach_ -> println("Game not finished")
        playerX -> println("X wins")
        playerO -> println("O wins")
        howMach_ -> workCase(charArray, getValue())
        else -> println("Impossible")
    }
}

fun movement(charArray: CharArray) {
    setka(charArray)
    workCase(charArray, getValue())
}

fun getValue(): String {
    print("Enter the coordinates: ")
    val cell: String = readLine()!!.toString()
    return cell
}

fun workCase(charArray: CharArray, cell: String) {
    try {
        val regex = "...".toRegex()
        if (cell.get(0).isDigit() && cell.get(2).isDigit() && cell.get(1) == ' ' && cell.contains(regex)) {
            have1to3(charArray, cell)
            havePole(charArray, cell)
        } else throw StringIndexOutOfBoundsException()
    } catch (e: StringIndexOutOfBoundsException) {
        println("You should enter numbers!")
        workCase(charArray, getValue())
    }
}

fun have1to3(charArray: CharArray, cell: String) {
    val x1 = cell[0].toString().toInt() in 1..3
    val x2 = cell[2].toString().toInt() in 1..3
    if (!x1 || !x2) {
        println("Coordinates should be from 1 to 3!")
        workCase(charArray, getValue())
    }
}


fun havePole(charArray: CharArray, cell: String) {
    val pole = arrayOf(
            "1 1", "1 2", "1 3",
            "2 1", "2 2", "2 3",
            "3 1", "3 2", "3 3")
    for (a in 0..pole.size - 1) {
        if (cell == pole[a] && charArray[a] != '_') {
            println("This cell is occupied! Choose another one!")
            workCase(charArray, getValue())
        } else if (cell == pole[a] && charArray[a] == '_') {
            charArray[a] = if (turn++ % 2 != 0) 'X' else 'O'
            setka(charArray)
            analis(charArray)
            break
        }
    }
}
