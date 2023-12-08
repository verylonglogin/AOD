package adventOfCode.day5

import adventOfCode.Utils
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import kotlin.test.assertEquals

class Solution {

    fun getRanges(text: String): List<Triple<Long, Long, Long>>{

        val rows = text.split("\n")
        val result = rows.map { row ->
            row.split(" ").map { it.toLong() }
        }
        return result.map { row -> Triple(row[1],row[0], row[2]) }

    }

    @Test
    fun partTwoTest() {
       val  seeds= "364807853 408612163 302918330 20208251 1499552892 200291842 3284226943 16030044 2593569946 345762334 3692780593 17215731 1207118682 189983080 2231594291 72205975 3817565407 443061598 2313976854 203929368".split(' ');

        val ranges = seeds.chunked(2);

        val fileName = Paths.get("src/test/kotlin/adventOfCode/").toAbsolutePath().toString() + "/day5/day5.input"
        val almanac = Utils.readFileAsTextUsingInputStream(fileName)
        val seeds2SoilMap = getRanges(getTheMap(almanac, "seed-to-soil map:"));
        val soil2FertilizerMap = getRanges(getTheMap(almanac, "soil-to-fertilizer map:"));
        val fertilizer2waterMap = getRanges(getTheMap(almanac, "fertilizer-to-water map:"))
        val water2lightMap = getRanges(getTheMap(almanac, "water-to-light map:"))
        val light2temperatureMap = getRanges(getTheMap(almanac, "light-to-temperature map:"))
        val temperature2humidityMap = getRanges(getTheMap(almanac, "temperature-to-humidity map:"))
        val humidity2locationMap = getRanges(getTheMap(almanac, "humidity-to-location map:"))

        var min = Long.MAX_VALUE;

        for (r in ranges) {
            val rangeBegin = r[0].toLong();
            val rangeLength = r[1].toLong()
            for (s in  rangeBegin until rangeBegin+rangeLength) {
                min = Math.min(min, calcLocation(s, seeds2SoilMap, soil2FertilizerMap, fertilizer2waterMap, water2lightMap, light2temperatureMap, temperature2humidityMap, humidity2locationMap))
            }

        }
        assertEquals(46, min);


    }

    @Test
    fun partOneTest() {
        val fileName = Paths.get("src/test/kotlin/adventOfCode/").toAbsolutePath().toString() + "/day5/day5.input"
        val almanac = Utils.readFileAsTextUsingInputStream(fileName)
        val seeds2SoilTextMap = getTheMap(almanac, "seed-to-soil map:");
        val soil2FertilizerTextMap = getTheMap(almanac, "soil-to-fertilizer map:");

        val seeds2SoilMap = getRanges(seeds2SoilTextMap);
        val soil2FertilizerMap = getRanges(soil2FertilizerTextMap);
        val fertilizer2waterMap = getRanges(getTheMap(almanac, "fertilizer-to-water map:"))
        val water2lightMap = getRanges(getTheMap(almanac, "water-to-light map:"))
        val light2temperatureMap = getRanges(getTheMap(almanac, "light-to-temperature map:"))
        val temperature2humidityMap = getRanges(getTheMap(almanac, "temperature-to-humidity map:"))
        val humidity2locationMap = getRanges(getTheMap(almanac, "humidity-to-location map:"))
        val seeds: List<Long> = listOf(364807853, 408612163, 302918330, 20208251, 1499552892, 200291842, 3284226943, 16030044, 2593569946, 345762334, 3692780593, 17215731, 1207118682, 189983080, 2231594291, 72205975, 3817565407, 443061598, 2313976854, 203929368);
        var min = Long.MAX_VALUE;

        seeds.forEach { s-> min = Math.min(min, calcLocation(s, seeds2SoilMap, soil2FertilizerMap, fertilizer2waterMap, water2lightMap, light2temperatureMap, temperature2humidityMap, humidity2locationMap)) }
        assertEquals(35, min);

    }
    fun calcLocation(
        seed: Long,
        seeds2SoilMap: List<Triple<Long, Long, Long>>,
        soil2FertilizerMap: List<Triple<Long, Long, Long>>,
        fertilizer2waterMap: List<Triple<Long, Long, Long>>,
        water2lightMap: List<Triple<Long, Long, Long>>,
        light2temperatureMap: List<Triple<Long, Long, Long>>,
        temperature2humidityMap:List<Triple<Long, Long, Long>>,
        humidity2locationMap:List<Triple<Long, Long, Long>>,
    ) : Long {
        val soil = getValue(seed, seeds2SoilMap)
        val fertilizer = getValue(soil, soil2FertilizerMap);
        val water = getValue(fertilizer, fertilizer2waterMap)
        val light = getValue(water, water2lightMap);
        val temp = getValue(light, light2temperatureMap);
        val humid = getValue(temp, temperature2humidityMap);
        val loc = getValue(humid, humidity2locationMap);
        return loc;
    }

    fun getValue(seed: Long, ranges: List<Triple<Long, Long, Long>>) : Long {
        for (r in ranges) {
            val seedRange = r.first until r.first+ r.third
            val diff = r.second - r.first ;
            if (seedRange.contains(seed)) return seed+ diff
        }
        return seed;
    }

    fun getTheMap(almanac: String, substringToFind: String): String {
        val startIndex = almanac.indexOf(substringToFind)
        val endIndex = almanac.indexOf("\n\n", startIndex)

        if (startIndex != -1 && endIndex != -1) {
            return almanac.substring(startIndex + 1 + substringToFind.length, endIndex)
        } else {
            throw Error("couldnt parse $substringToFind")
        }
    }


}