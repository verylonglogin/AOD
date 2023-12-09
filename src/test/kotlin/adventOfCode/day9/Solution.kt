package adventOfCode.day9

import adventOfCode.Utils
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import java.util.*
import kotlin.test.assertEquals

class Solution {

    @Test
    fun partOneTest() {
        assertEquals(114, partOne());
    }

    @Test
    fun partOneTest3() {
        val input = "10 13 16 21 30 45".split(" ").map { ch -> ch.toLong() };
        assertEquals(68L, calcNextPartOne(input))
    }
    @Test
    fun partOneTest2() {
        val input = "1 3 6 10 15 21".split(" ").map { ch -> ch.toLong() };
        assertEquals(28L, calcNextPartOne(input))
    }
    @Test
    fun partOneTest1() {
        val input = "0 3 6 9 12 15".split(" ").map { ch -> ch.toLong() };
        assertEquals(18L, calcNextPartOne(input))
    }

    fun partOne(): Long{
        val fileName =
            Paths.get("src/test/kotlin/adventOfCode/").toAbsolutePath().toString() + "/day9/day9.input"
        val historyRecords = Utils.readFileAsTextUsingInputStream(fileName).split("\n")
        return historyRecords.fold(0L) {acc, history -> acc+calcNextPartOne(history) }

    }
     @Test
    fun partTwoTest3() {
        val input = "10 13 16 21 30 45".split(" ").map { ch -> ch.toLong() };
        assertEquals(5L, calcNextPartTwo(input))
    }
    @Test
    fun partTwoTest() {
        assertEquals(114, partTwo());
    }
    fun partTwo(): Long {
        val fileName =
            Paths.get("src/test/kotlin/adventOfCode/").toAbsolutePath().toString() + "/day9/day9.input"
        val historyRecords = Utils.readFileAsTextUsingInputStream(fileName).split("\n")
        return historyRecords.fold(0L) {acc, history -> acc+calcNextPartTwo(history) }
    }
    fun calcNextPartTwo(history: String): Long {
        val input = history.split(" ").map { ch -> ch.toLong() };
        return calcNextPartTwo(input)
    }
    fun calcNextPartTwo(input: List<Long>): Long {
        var current: LinkedList<Long> = LinkedList(input);
        var firstNumbers: LinkedList<Long> = LinkedList();
        var countZero = 0;
        while (current.size != countZero) {
            countZero=0;
            firstNumbers.add(current.first)
            for (i in 0 until current.size-1) {
                current [i] = current[i+1] -current[i]
                if (current[i] == 0L) countZero++
            }
            current.removeLast();
        }
//        println(lastNumbers)
        var a = 0L;
        while (firstNumbers.size!=0) {
            a = firstNumbers.last -a
            firstNumbers.removeLast();
        }
        return a;
    }
    fun calcNextPartOne(history: String): Long {
        val input = history.split(" ").map { ch -> ch.toLong() };
        return calcNextPartOne(input)
    }
    fun calcNextPartOne(input: List<Long>): Long {
        var start = input.size / 2 - 1;
//        var current: LinkedList<Long> = LinkedList(input.subList(start, input.size));
        var current: LinkedList<Long> = LinkedList(input);
        var lastNumbers: LinkedList<Long> = LinkedList();
        var countZero = 0;
        while (current.size != countZero) {
            countZero=0;
            for (i in 0 until current.size-1) {
                current [i] = current[i+1] -current[i]
                if (current[i] == 0L) countZero++
            }
            lastNumbers.add(current.last)
            current.removeLast();
        }

//        println(lastNumbers)
        var a = 0L;
        while (lastNumbers.size!=0) {
            a += lastNumbers.last
            lastNumbers.removeLast();
        }
        return a;
    }
}

