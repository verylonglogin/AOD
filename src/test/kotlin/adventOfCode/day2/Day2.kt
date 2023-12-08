package adventOfCode.day2

import adventOfCode.Utils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Day2 {

    val setUp = mapOf("red" to 12, "green" to 13, "blue" to 14)


    fun isGameValid(game: String): Boolean {
       val sets =  game.split(";");
        sets.forEach { set-> if(!isSetPossible(set)) return false }
        return true;
    }

    fun isSetPossible(set: String): Boolean {
        println("for string ${set}")

        val regex = Regex("(\\d+)\\s+(blue|red|green)");
        val matches = regex.findAll(set);
        for (match in matches) {
            val number = match.groupValues[1].toInt();
            val color = match.groupValues[2]
            print("${number} ${color} ")
            if (setUp.getValue(color) < number) return false;
        }
        println()
        return true;
    }
    @Test
    fun isGamePossibleTest() {
        val game1 = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green";
        assertTrue { isGameValid(game1) }

        val game2 = "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue";
        assertTrue { isGameValid(game2) }

        val game3 = "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red";
        assertFalse { isGameValid(game3) }

        val game4 = "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red";
        assertFalse { isGameValid(game4) }
        val game5 = "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
        assertTrue { isGameValid(game5) }

    }
    @Test
    fun regexpr() {
        val game = "Game 1: 3 blue, 4 red; 1 red, 2 green,"
        val sets = game.split(";").map {  };

        val regex = Regex("(\\d+)\\s+(blue|red|green)");
        val matches = regex.findAll(game);
        for (match in matches) {
            val number = match.groupValues[1]
            val color = match.groupValues[2]
            println("Number: $number, Color: $color")
        }
    }


    fun partOne(games:List<String>) : Int {
        var sum = 0;
        games.forEachIndexed {index, game ->
            run {
                if (isGameValid(game)) sum += (index + 1);
            }
        }
        return sum;
    }

    fun partTwo(games:List<String>) : Int{
       return games.fold(0){acc, game-> acc+ getPower(game)}
    }
    @Test
    fun partTwoTest() {
        val games = Utils.readFileAsLinesUsingUseLines("src/test/kotlin/adventOfCode/day2/day2.input")
        assertEquals(2286, partTwo(games));
    }

    private fun getPower(game1: String): Int {
        println("for string ${game1}")

        val regex = Regex("(\\d+)\\s+(blue|red|green)");
        val matches = regex.findAll(game1);
        val  map = hashMapOf<String, Int>()
        for (match in matches) {
            val number = match.groupValues[1].toInt();
            val color = match.groupValues[2]
            print("${number} ${color} ")
            map[color] = Math.max(map.getOrDefault(color, 1), number);
        }
        println()
        return map.values.fold(1){acc, i ->  acc*i};
    }

    @Test
    fun partOneTest() {
        val games = Utils.readFileAsLinesUsingUseLines("src/test/kotlin/adventOfCode/day2/day2.input")
        assertEquals(8, partOne(games))
    }
}