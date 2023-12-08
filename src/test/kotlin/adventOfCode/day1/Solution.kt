package adventOfCode.day1

import adventOfCode.Utils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {

   fun findCalibrationValue2 (lines: List<String>): Int {
       var res = 0;
       lines.forEach { s ->
           run {
               val r = findCalibrationValue2(s)
               res += r
               println(r)
           }
       }
       return res;
   }

    @Test
    fun findCalibrationValue2TestCustom() {
        assertEquals(29, findCalibrationValue2("twonine"));
    }
    fun findCalibrationValue2(str: String): Int {
        var b = 0;
        var e = str.length-1;
        var sum = 0;
        var map = mutableMapOf<String, Int>();
        val digits = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9);
        var min = Int.MAX_VALUE;
        var minChar = -1;
        var max = Int.MIN_VALUE;
        var maxChar = -1;
        digits.keys.forEach { s->  run {
            val indexOf = str.indexOf(s);
            if (indexOf != -1) {
                if (min > indexOf) {
                    min = indexOf;
                    minChar = digits.getValue(s);
                }
                if (max < indexOf){
                    max = indexOf;
                    maxChar = digits.getValue(s);
                }
            }
            map[s] = if (indexOf == -1) Int.MAX_VALUE else indexOf;
        }

        }
        while (b< str.length) {
            if (str[b].isDigit()) {
             val digit = findFirstDigit(str, b, min, minChar);
                sum +=digit*10;
                break;
            } else b++;
        }
        while (e >=0) {
            if (str[e].isDigit()) {
                val digit = findLastDigit(str, e, max, maxChar);
                sum +=digit;
                break;
            } else e--;
        }

        if (sum == 0 && b == str.length && e == -1) {
            sum = minChar*10+ maxChar;
        }

        return sum;
    }

    private fun findLastDigit(str: String, index: Int, max: Int, maxChar: Int): Int {
        if (index > max) return Character.getNumericValue(str[index])
        return maxChar;
    }

    private fun findFirstDigit(substring: String, index: Int, minIndex: Int, minChar: Int): Int {
        if (index < minIndex) {
            return Character.getNumericValue(substring[index])
        }
       return minChar;
    }


    @Test
    fun findCalibrationValue2Test() {
        assertEquals(29, findCalibrationValue2("two1nine"));
        assertEquals(83, findCalibrationValue2("eightwothree"));
        assertEquals(13, findCalibrationValue2("abcone2threexyz"));
        assertEquals(24, findCalibrationValue2("xtwone3four"));

        assertEquals(42, findCalibrationValue2("4nineeightseven2"));
        assertEquals(14, findCalibrationValue2("zoneight234"));
        assertEquals(76, findCalibrationValue2("7pqrstsixteen"));
    }
    @Test
    fun findCalibration2TestAll() {
        val lines = Utils.readFileAsLinesUsingUseLines("src/test/kotlin/adventOfCode/day1.input")
        assertEquals(281, findCalibrationValue2(lines))
    }

}