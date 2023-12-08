package adventOfCode.day3

import adventOfCode.Utils
import org.junit.jupiter.api.Test
import java.nio.file.Paths

import kotlin.test.assertEquals

class Solution {
    @Test
    fun partTwoTest() {
//                val rows = "467..114..\n" +
//                "...*......\n" +
//                "..35..633.\n" +
//                "......#...\n" +
//                "617*......\n" +
//                ".....+.58.\n" +
//                "..592.....\n" +
//                "......755.\n" +
//                "...\$.*....\n" +
//                ".664.598.."
        val fileName = Paths.get("src/test/kotlin/adventOfCode/").toAbsolutePath().toString()+"/day3/day3.input"
        val rows = Utils.readFileAsTextUsingInputStream(fileName);
        val result = partTwo(rows);
        assertEquals(467835, result);
    }
    @Test
    fun partOneTest (){
//        val rows = "467..114..\n" +
//                "...*......\n" +
//                "..35..633.\n" +
//                "......#...\n" +
//                "617*......\n" +
//                ".....+.58.\n" +
//                "..592.....\n" +
//                "......755.\n" +
//                "...\$.*....\n" +
//                ".664.598.."

        val fileName = Paths.get("src/test/kotlin/adventOfCode/").toAbsolutePath().toString()+"/day3/day3.input"
        val rows = Utils.readFileAsTextUsingInputStream(fileName);

       val result = partOne(rows);
        assertEquals(4361, result);

    }
    fun partOne(rows: String):Int {
        val array = makeArray(rows);
        val sum = traverse(array);
        return sum;
    }
    fun partTwo(rows: String):Int {
        val array = makeArray(rows);
        val sum = traverseTwoGears(array);
        return sum;
    }
    fun traverse(array: List<StringBuilder>):Int {
        var sum = 0;
        for (r in array.indices) {
            for (c in 0 until array[r].length){
                if (!array[r][c].isDigit() && array[r][c] != '.') {
                   sum += dfs(array,r, c)
                }
            }
        }
        return sum;
    }
    fun traverseTwoGears(array: List<StringBuilder>):Int {
        var sum = 0;
        for (r in array.indices) {
            for (c in 0 until array[r].length){
                if (!array[r][c].isDigit() && array[r][c] != '.') {
                    sum += dfs2(array,r, c)
                }
            }
        }
        return sum;
    }
    fun dfs2(array: List<StringBuilder>, r: Int, c: Int): Int {
        var sum = 0;
        val n1 = parseNumber(array, r-1, c-1);
        val n3 =parseNumber(array, r, c-1);

        val n4 =parseNumber(array, r-1, c+1);

        val n5 =parseNumber(array, r, c+1);

        val n6 =parseNumber(array, r+1, c+1);

        val n8 =parseNumber(array, r+1, c-1);

        val up =parseNumber(array, r-1, c);

        val down =parseNumber(array, r+1, c);


        val numbers = listOf(n1, n3, n4, n5, n6, n8, up, down);
        val notZero = numbers.filter { n-> n!=0 };
        if (notZero.size == 2) {
          sum+= notZero.reduce{ acc, i -> acc*i }
        }
        return sum;
    }
    fun dfs(array: List<StringBuilder>, r: Int, c: Int): Int {
        var sum = 0;
        val n1 = parseNumber(array, r-1, c-1);
        val n3 =parseNumber(array, r, c-1);
        val n4 =parseNumber(array, r-1, c+1);
        val n5 =parseNumber(array, r, c+1);
        val n6 =parseNumber(array, r+1, c+1);
        val n8 =parseNumber(array, r+1, c-1);
        val up =parseNumber(array, r-1, c);
        val down =parseNumber(array, r+1, c);
        sum += n1 + n4 + n3 + n5 + n6 + n8 +up+down;
        return sum;
    }
    fun parseNumber(array: List<StringBuilder>, r: Int, c: Int): Int{
        if (r < 0 || r > array.size || c< 0|| c > array[r].length) return 0;
        if (!array[r][c].isDigit()) return 0;
        val num = StringBuilder()
        var col = c;
        while (col < array[r].length && array[r][col].isDigit()) {
            num.append(array[r][col])
            array[r][col] = '.'
            col++;
        }
        col = c-1;
        while (col>=0 && array[r][col].isDigit()) {
            num.insert(0,array[r][col]);
            array[r][col] = '.'
            col--;
        }
        return num.toString().toInt();
    }
    fun makeArray(rows: String) : List<StringBuilder> {
        return rows.split("\n").map { row-> StringBuilder(row) }.toList();
    }
}