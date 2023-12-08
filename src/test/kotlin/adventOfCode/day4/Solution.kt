package adventOfCode.day4

import adventOfCode.Utils
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import java.util.*
import kotlin.test.assertEquals

class Solution {

    fun partOne(cards: List<String>):Int {
       return cards.fold(0) {acc, c ->  acc+ calcPointForCard(c)}
    }
    @Test
    fun partTwoTest() {
        val card1 = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53";
        val card2 = "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19";
        val card3 = "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"
        val card4 ="Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83";
        val card5 ="Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36";
        val card6 ="Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11";
        val cards = listOf(card1, card2, card3, card4, card5, card6);
        assertEquals(30, partTwo(cards))
    }
    fun partTwo(cards: List<String>): Int {
        val cardsScore: Map<Int, Int> = cards.mapIndexed {index, card -> index+1 to calcPointForCard2(card) }.toMap();
        val queue: LinkedList<Int> = LinkedList(cardsScore.entries.map { e-> e.key });
        var result = 0;
        while (!queue.isEmpty()) {
            val cardNumber = queue.poll();
            result++;
            for (c in cardNumber+1  .. cardNumber+cardsScore.getOrDefault(cardNumber, 0))
                queue.offer(c)

        }
        return result;
    }
    fun calcPointForCard2(card: String): Int{
        val cardViewver = card.split("|")
        val winningCards = cardViewver[0].split(" ").filter { it.matches(Regex("\\d+")) }.map { it.toInt() }.toSet();
        val gameCards = cardViewver[1].split(" ").filter { it.matches(Regex("\\d+")) }.map { it.toInt() }.toList()

        var result = 0;

        for (gameCard in gameCards) {
            if (winningCards.contains(gameCard)) result++
        }
        return result;
    }
    fun calcPointForCard(card: String): Int{
        val cardViewver = card.split("|")
        val winningCards = cardViewver[0].split(" ").filter { it.matches(Regex("\\d+")) }.map { it.toInt() }.toSet();
        val gameCards = cardViewver[1].split(" ").filter { it.matches(Regex("\\d+")) }.map { it.toInt() }.toList()
        var result = 0;

        for (gameCard in gameCards) {
            if (!winningCards.contains(gameCard)) continue
            result = if (result == 0) 1 else result*2;
        }
        return result;
    }

    @Test
    fun partOneTest() {
        val card1 = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53";
        val card2 = "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19";
        val card3 = "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"
        val card4 ="Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83";
        val card5 ="Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36";
        val card6 ="Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11";
        val cards = listOf(card1, card2, card3, card4, card5, card6);

        assertEquals(13, partOne(cards))
    }
    @Test
    fun partOneFinalResult() {
        val fileName = Paths.get("src/test/kotlin/adventOfCode/").toAbsolutePath().toString()+"/day4/day4.input"
        val cards = Utils.readFileAsTextUsingInputStream(fileName).split("\n");
        assertEquals(13, partOne(cards))
    }
    @Test
    fun partTwoFinalResult() {
        val fileName = Paths.get("src/test/kotlin/adventOfCode/").toAbsolutePath().toString()+"/day4/day4.input"
        val cards = Utils.readFileAsTextUsingInputStream(fileName).split("\n");
        assertEquals(13, partTwo(cards))
    }

}