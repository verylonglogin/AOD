package adventOfCode.day6

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {

    @Test
    fun partOneTest1() {
        val time:Long  = 7;
        val dist:Long  = 9;
        val result = calcResultPartOne(time, dist);
        assertEquals(4, result);
    }

    @Test
    fun partOneTest2() {
        val time:Long  = 15;
        val dist:Long  = 40;
        val result = calcResultPartOne(time, dist);
        assertEquals(8, result);
    }

    @Test
    fun partOneTest3() {
        val time:Long  = 30;
        val dist:Long  = 200;
        val result = calcResultPartOne(time, dist);
        assertEquals(9, result);
    }


    fun calcResultPartOne(time:Long, dist: Long) : Long{

        var end = time - 1;

        var upperBound = findUpperBound(1, end, dist, time);
        var lowerBound = findLowerBound(1, end, dist, time);

        return upperBound - lowerBound +1;
    }

    @Test
    fun test2() {
        val totalTime:Long  = 7
        val speed:Long   = 2
//        dist =  9;
       val rez =  calcDist(totalTime, speed);

    }


    @Test
    fun partOneDemoTest() {
        val  time:List<Long>  = listOf(7, 15, 30);
        val distance:List<Long> =  listOf( 9, 40, 200);
        val result = partOne(time, distance);
        assertEquals(288, result);
    }

    @Test
    fun partOneFinalTest() {
       val  time: List<Long> = listOf(48     ,93,     85,     95);
        val distance: List<Long> =  listOf( 296   ,1928  , 1236,   1391);
        val result = partOne(time, distance);
        assertEquals(1, result);
    }

    @Test
    fun partTwoTest() {
        val time: Long = 48938595;
        val distance: Long =  296192812361391;
        assertEquals(71503, calcResultPartOne(time, distance));
    }

    fun partOne(times: List<Long>, distances:List<Long> ) : Long {
        var product = 1L;
        for (i in 0 until times.size) {
            val rez = calcResultPartOne(times[i], distances[i]);
            product*=rez;
        }
        return product;
    }

    fun findLowerBound(begin: Long, end: Long, dist: Long, totalTime: Long):Long {
        var l:Long = begin;
        var r:Long  = end;
        var mid:Long  = -1L;
        var lowerBound:Long  = 0;
        while (l <= r) {
            mid = l + (r - l) / 2;
            val myDist = calcDist(totalTime, mid);
            if (myDist > dist ) {
                lowerBound = mid;
                r = mid-1;
            } else {
                val rightNeighbour = calcDist(totalTime, mid+1)
                val leftNeighbour = calcDist(totalTime, mid-1);
                if (rightNeighbour > myDist || leftNeighbour < myDist) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return lowerBound;
    }
    fun findUpperBound(begin: Long, end: Long, dist: Long, totalTime: Long): Long {
        var l:Long  = begin;
        var r:Long  = end;
        var mid:Long  = -1;
        var upperBound:Long  = 0;
        while (l <= r) {
            mid = l + (r - l) / 2;
            val myDist = calcDist(totalTime, mid);
            if (myDist > dist) {
                upperBound = Math.max(mid, upperBound)
                l = mid + 1;
            } else {
                val rightNeighbour = calcDist(totalTime, mid+1)
                val leftNeighbour = calcDist(totalTime, mid-1);
                if (rightNeighbour > myDist || leftNeighbour < myDist) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return upperBound;
    }

    fun calcDist(totalTime: Long, speed: Long): Long {
        val timeLeft = totalTime - speed;
        return timeLeft * speed;
    }

}