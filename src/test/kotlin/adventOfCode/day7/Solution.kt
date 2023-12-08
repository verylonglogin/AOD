package adventOfCode.day7

import adventOfCode.Utils
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import kotlin.test.assertEquals

class Solution {

    @Test
    fun testPairs() {
        compareStringsCharByChar("T", "9")
       val s1 = "TQJA3";
       val comb1 =  getCombinationScore(s1)
        val s2 = "TQ498"
         val comb2= getCombinationScore(s2);
        val rez = compareStringsCharByChar(s1,s2);

        println(rez)
    }



    @Test
    fun partOne() {
        val fileName =
            Paths.get("src/test/kotlin/adventOfCode/").toAbsolutePath().toString() + "/day7/day7Example.input"
        val handsText = Utils.readFileAsTextUsingInputStream(fileName)
        val hands = handsText.split("\n").map { s ->
            Pair(s.split(" ")[0], s.split(" ")[1].toInt())

        }.toList()

        val sortedArray = hands.sortedWith(compareBy<Pair<String, Int>>(
            // First, sort by the amount of each character
            { pair -> getCombinationScore(pair.first) }).then{
            //then char by char
             pair1, pair2 -> compareStringsCharByChar(pair1.first, pair2.first) }
        );
        sortedArray.forEach { p->  println(p.first)  }
        var rank = 1;
        val scores = sortedArray.fold(0){acc, pair: Pair<String, Int> ->  acc+pair.second*(rank++)}

        assertEquals(6440, scores)

    }



    @Test
    fun partTwo() {
        val fileName =
            Paths.get("src/test/kotlin/adventOfCode/").toAbsolutePath().toString() + "/day7/day7Example.input"
        val handsText = Utils.readFileAsTextUsingInputStream(fileName)
        val hands = handsText.split("\n").map { s ->
            Pair(s.split(" ")[0], s.split(" ")[1].toInt())

        }.toList()

        val sortedArray = hands.sortedWith(compareBy<Pair<String, Int>>(
            // First, sort by the amount of each character
            { pair -> getCombinationScorePartTwo(pair.first) }).then{
            //then char by char
                pair1, pair2 -> compareStringsCharByCharPart2(pair1.first, pair2.first) }
        );
        var rank = 1;
        val scores = sortedArray.fold(0){acc, pair: Pair<String, Int> ->  acc+pair.second*(rank++)}

        assertEquals(5905, scores)

    }

    fun compareStringsCharByChar(s1: String, s2: String): Int {
        val minLength = minOf(s1.length, s2.length)

        for (i in 0 until minLength) {
            val score1 = getCharScore(s1[i])
            val score2 = getCharScore(s2[i])

            if (score1 != score2) {
                return score1 - score2
            }
        }

        return s1.length - s2.length
    }
    fun compareStringsCharByCharPart2(s1: String, s2: String): Int {
        val minLength = minOf(s1.length, s2.length)

        for (i in 0 until minLength) {
            val score1 = getCharScorePartTwo(s1[i])
            val score2 = getCharScorePartTwo(s2[i])

            if (score1 != score2) {
                return score1 - score2
            }
        }

        return s1.length - s2.length
    }


    fun getCharScore(char: Char): Int {
        if (char.isDigit()) return Character.getNumericValue(char);
        return when (char.toLowerCase()) {
            'a' -> 14
            'k' -> 13
            'q' -> 12
            'j' -> 11
            't' -> 10
            else -> throw Exception("should never happen")
        }
    }

    fun getCharScorePartTwo(char: Char): Int {
        if (char.isDigit()) return Character.getNumericValue(char);
        return when (char.toLowerCase()) {
            'a' -> 14
            'k' -> 13
            'q' -> 12
            'j' -> 0
            't' -> 10
            else -> throw Exception("should never happen")
        }
    }

    fun getCombinationScore(input: String):  Int {
        val charCount = input.groupingBy { it }.eachCount().entries.groupBy({ it.value }, { it.key })
        if (charCount.contains(5)) return 7;//5 of a kind
        if (charCount.contains(4)) return  6;//4 of a kind
        if (charCount.contains(3) && charCount.contains(2)) return  5;//full house
        if (charCount.contains(3) && charCount.contains(1)) return  4;// 3 of a kind
        if (charCount.contains(2) && (charCount.get(2)!!.size == 2) && charCount.contains(1)) return 3;// 2 pairs
        if (charCount.contains(2) && (charCount.get(2)!!.size == 1) && charCount.contains(1)) return 2;// 1 pairs
        if (charCount.contains(1) && (charCount.get(1)!!.size == 5)) return 1;// High card
         throw Exception("should never happen")
    }



    @Test
    fun t1(){
        getCombinationScorePartTwo("J5Q5Q");
    }
    fun getCombinationScorePartTwo(input: String):  Int {
        val newString = input.replace("J", "");
        if (newString.length == 0) return 7;
        val numOfJ = input.length-newString.length;
        val charCount = newString.groupingBy { it }.eachCount().entries.groupBy({ it.value }, { it.key })
        if (charCount.contains(5)) return 7;//5 of a kind
        if (charCount.contains(4)) {
            if (numOfJ == 1 ) return 7;
            return  6
        };//4 of a kind
        if (charCount.contains(3) && charCount.contains(2)) {
            return  5
        };//full house
        if (charCount.contains(3)) {
            if (numOfJ == 1) return  6; //4 of a kind
            if (numOfJ == 2) return 7;
            return  4
        };// 3 of a kind

        if (charCount.contains(2) && (charCount.get(2)!!.size == 2) ) {
            if (numOfJ == 1) return  5;// full house
            return 3
        };// 2 pairs
        if (charCount.contains(2) && (charCount.get(2)!!.size == 1)) {
            if (numOfJ == 1)  return  4;//3 of a kind
            if (numOfJ == 2)    return  6//for of a kind
            if (numOfJ == 3) return 7;//5 of a kind
            return 2;// 1 pairs
        }
        if (charCount.contains(1) ){
            if (numOfJ == 4)  return 7;//5 of a kind
            if (numOfJ == 3)  return 6;//4 of a kind
            if (numOfJ == 2)  return  4;//3 of a kind
            if (numOfJ ==1 )  return 2;// 1 pairs
            return 1
        };// High card
        throw Exception("should never happen $input")
    }
}
