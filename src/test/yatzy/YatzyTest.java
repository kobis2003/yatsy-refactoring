package yatzy;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class YatzyTest {

    @ParameterizedTest
    @CsvSource({
        "0, 2, 3, 4, 5",
        "1, 2, 3, 4, 7",
        "1, 2, 8, 4, 5"
    })
    public void testInvalidDiceValues(int d1, int d2, int d3, int d4, int d5) {
        assertThrows(IllegalArgumentException.class, () -> new Yatzy(d1, d2, d3, d4, d5));
    }

    @ParameterizedTest
    @CsvSource({
        "2, 3, 4, 5, 1, 15",
        "3, 3, 4, 5, 1, 16"
    })
    public void testChance(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).chance());
    }


    @ParameterizedTest
    @CsvSource({
        "4, 4, 4, 4, 4, 50",
        "6, 6, 6, 6, 6, 50",
        "6, 6, 6, 6, 3, 0"
    })
    public void testYatzy(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).yatzy());
    }

    @ParameterizedTest
    @CsvSource({
        "1, 2, 3, 4, 5, 1",
        "1, 2, 1, 4, 5, 2",
        "6, 2, 2, 4, 5, 0",
        "1, 2, 1, 1, 1, 4"
    })
    public void testOnes(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).ones());
    }

    @ParameterizedTest
    @CsvSource({
        "1, 2, 3, 2, 6, 4",
        "2, 2, 2, 2, 2, 10"
    })
    public void testTwos(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).twos());
    }

    @ParameterizedTest
    @CsvSource({
        "1, 2, 3, 2, 3, 6",
        "2, 3, 3, 3, 3, 12"
    })
    public void testThrees(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).threes());
    }

    @ParameterizedTest
    @CsvSource({
        "4, 4, 4, 5, 5, 12",
        "4, 4, 5, 5, 5, 8",
        "4, 5, 5, 5, 5, 4"
    })
    public void testFours(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).fours());
    }

    @ParameterizedTest
    @CsvSource({
        "4, 4, 4, 5, 5, 10",
        "4, 4, 5, 5, 5, 15",
        "4, 5, 5, 5, 5, 20"
    })
    public void testFives(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).fives());
    }

    @ParameterizedTest
    @CsvSource({
        "4, 4, 4, 5, 5, 0",
        "4, 4, 6, 5, 5, 6",
        "6, 5, 6, 6, 5, 18"
    })
    public void testSixes(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).sixes());
    }

    @ParameterizedTest
    @CsvSource({
        "3, 4, 3, 5, 6, 6",
        "5, 3, 3, 3, 5, 10",
        "5, 3, 6, 6, 5, 12"
    })
    public void testScorePair(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).onePair());
    }

    @ParameterizedTest
    @CsvSource({
        "3, 3, 5, 4, 5, 16",
        "3, 3, 5, 5, 5, 16",
        "3, 3, 5, 2, 4, 0"
    })
    public void testTwoPair(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).twoPair());
    }

    @ParameterizedTest
    @CsvSource({
        "3, 3, 3, 4, 5, 9",
        "5, 3, 5, 4, 5, 15",
        "3, 3, 3, 3, 5, 9"
    })
    public void testThreeOfAKind(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).threeOfAKind());
    }

    @ParameterizedTest
    @CsvSource({
        "3, 3, 3, 3, 5, 12",
        "5, 5, 5, 4, 5, 20",
        "3, 3, 3, 3, 3, 12"
    })
    public void testFourOfAKind(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).fourOfAKind());
    }

    @ParameterizedTest
    @CsvSource({
        "1, 2, 3, 4, 5, 15",
        "2, 3, 4, 5, 1, 15",
        "1, 2, 2, 4, 5, 0",
        "6, 2, 3, 4, 5, 0"
    })
    public void testSmallStraight(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).smallStraight());
    }

    @ParameterizedTest
    @CsvSource({
        "1, 2, 3, 4, 5, 0",
        "6, 2, 3, 4, 5, 20",
        "2, 3, 4, 5, 6, 20",
        "1, 2, 2, 4, 5, 0"
    })
    public void testLargeStraight(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).largeStraight());
    }

    @ParameterizedTest
    @CsvSource({
        "6, 2, 2, 2, 6, 18",
        "2, 3, 4, 5, 6, 0"
    })
    public void fullHouse(int d1, int d2, int d3, int d4, int d5, int expectedResult) {
        assertEquals(expectedResult, new Yatzy(d1, d2, d3, d4, d5).fullHouse());
    }
}
