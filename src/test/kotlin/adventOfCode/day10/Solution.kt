package adventOfCode.day10

import adventOfCode.Utils
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import kotlin.test.assertEquals

class Solution {

//    | is a vertical pipe connecting north and south.
//    - is a horizontal pipe connecting east and west.
//    L is a 90-degree bend connecting north and east.
//    J is a 90-degree bend connecting north and west.
//    7 is a 90-degree bend connecting south and west.
//    F is a 90-degree bend connecting south and east.
//    . is ground; there is no pipe in this tile.
//    S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.

    val moves = hashMapOf(
        '|' to arrayOf( Pair(-1, 0), Pair(1,0)),
        '-' to arrayOf(Pair(0, -1), Pair(0, 1)),
        'L' to arrayOf(Pair(0,1), Pair(-1, 0)),
        'J' to arrayOf(Pair(-1, 0), Pair(0,-1)),
        '7' to arrayOf(Pair(0,-1), Pair(1,0)),
        'F' to arrayOf(Pair(0, 1), Pair(1, 0)));
    @Test
    fun partOneTest1() {
        val input = ".....\n" +
                ".S-7.\n" +
                ".|.|.\n" +
                ".L-J.\n" +
                ".....";
        val array = makeArray(input);
        val longest = traverse(array);
        assertEquals(4L, longest/2L)
    }

    @Test
    fun partOneTest2() {
        val input = "..F7.\n" +
                ".FJ|.\n" +
                "SJ.L7\n" +
                "|F--J\n" +
                "LJ...";
        val array = makeArray(input);
        val longest = traverse(array);
        assertEquals(8L, longest/2L)
    }

    @Test
    fun partOneFinalTest() {
        val fileName = Paths.get("src/test/kotlin/adventOfCode/").toAbsolutePath().toString()+"/day10/day10.input"
        val input = Utils.readFileAsTextUsingInputStream(fileName);
        val array = makeArray(input);
        val longest = traverse(array);
        assertEquals(8L, longest/2L)

    }

    fun traverse(array: List<StringBuilder>): Long {
        for (r in array.indices) {
            for (c in 0 until array[r].length) {
                if (array[r][c] == 'S') {
                    return go(array, r, c)
                }

            }
        }
        return -1;
    }

    fun go(array: List<StringBuilder>, r: Int, c: Int): Long {
        val paths = mutableListOf<Long>()
        val seen = hashSetOf<Pair<Int, Int>>();
        print("($r,$c)->")

        add(paths, dfs(array, seen, r-1, c, Pair(r,c), 0)); //up
        add(paths,dfs(array, seen, r+1, c, Pair(r,c),0)); //down
        add(paths, dfs(array, seen, r, c-1, Pair(r,c),0)); //left
        add(paths, dfs(array, seen, r, c+1, Pair(r,c),0)); //right

        return paths.max();
    }
    fun add(paths: MutableList<Long>, path: Long) {
        if(path == 0L) return;
        paths.add(path)
    }

    fun dfs(array: List<StringBuilder>, seen : HashSet<Pair<Int, Int>>, r: Int, c: Int, prev: Pair<Int, Int>, currLen: Int): Long {
        if (r < 0 || r > array.size || c< 0|| c > array[r].length) return 0;
        if (array[r][c] == '.') return 0;
        if (array[r][c] == 'S') return currLen+ 1L;
        if(seen.contains(Pair(r,c))) return 0;
        seen.add(Pair(r,c))
        print("($r,$c)->")

        var localMax = 0L;
        for (m in moves.get(array[r][c])!!) {
            if (Pair(r+m.first, c+m.second) == prev) continue
            val option = dfs(array, seen, r+m.first, c+m.second, Pair(r,c), currLen+1);
            localMax = Math.max(localMax,option )
        }

        return localMax;
    }

    fun makeArray(rows: String): List<StringBuilder> {
        return rows.split("\n").map { row -> StringBuilder(row) }.toList();
    }
}