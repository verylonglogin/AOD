package adventOfCode.day8

import adventOfCode.Utils
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import kotlin.test.assertEquals

class Solution {

    @Test
    fun partTwo() {
        val instructions = parseFile();
        val commands = instructions.first
        val map = instructions.second;
        val listOfCurr = map.keys.filter { k -> k.endsWith('A') }.toMutableList()
        val allResults = listOfCurr.map { s -> getCounter(s, commands, map) };
        val finalResult = allResults.fold(1L) { acc, rez -> lcm(acc, rez) }

        assertEquals(22103062509257, finalResult);
    }

    fun lcm(a: Long, b: Long): Long {
        if (a == 0L || b == 0L) throw Error("a=$a b=$b")
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }


    @Test
    fun partOne() {
        val instructions = parseFile();
        val commands = instructions.first
        val map = instructions.second;
        val destination = "ZZZ";
        var counter = 0L;
        var current = "AAA";
        var c = 0;
        while (current != destination) {
            if (commands[c % commands.length] == 'L') {
                current = map.get(current)!!.first;
            } else {
                current = map.get(current)!!.second
            };
            c++;
            counter++;
        }
        assertEquals(20093, counter)

    }

    fun parseFile(): Pair<String, Map<String, Pair<String, String>>> {
        val fileName =
            Paths.get("src/test/kotlin/adventOfCode/").toAbsolutePath().toString() + "/day8/day8.input"
        val instructions = Utils.readFileAsTextUsingInputStream(fileName)
        val commandMaps = instructions.split("\n");
        val commands = commandMaps[0];
        val regex = Regex("([A-Z0-9]+)\\s*=\\s*\\(([A-Z0-9]+),\\s*([A-Z0-9]+)\\)")
        val map = hashMapOf<String, Pair<String, String>>()

        for (i in 2 until commandMaps.size) {

            val input = commandMaps[i];
            val matchResult = regex.find(input)

            if (matchResult != null) {
                val (first, second, third) = matchResult.destructured
                map.put(first, Pair(second, third));
            } else {
                println("No match found in the input string $input")
            }
        }

        return Pair(commands, map)
    }

    fun getCounter(curr: String, commands: String, map: Map<String, Pair<String, String>>): Long {
        var counter = 0L;
        var c = 0;
        var current = curr;
        while (!current.endsWith('Z')) {
            if (commands[c % commands.length] == 'L') {
                current = map.get(current)!!.first;
            } else {
                current = map.get(current)!!.second
            };
            c++;
            counter++;
        }

        return counter;
    }


}