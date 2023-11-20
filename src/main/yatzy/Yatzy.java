package yatzy;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Yatzy {


    private final Integer[] sortedDiceValues;

    public Yatzy(int d1, int d2, int d3, int d4, int d5) {
        Integer[] diceValues = new Integer[]{d1, d2, d3, d4, d5};
        if (Arrays.stream(diceValues).anyMatch(value -> value < 1 || value > 6)) {
            throw new IllegalArgumentException("Dice values must be between 1 and 6.");
        }
        this.sortedDiceValues = Arrays.stream(diceValues)
            .sorted()
            .toArray(Integer[]::new);
    }

    /**
     * @return the total sum of all dices
     */
    public int chance() {
        return Arrays.stream(sortedDiceValues)
            .mapToInt(Integer::intValue)
            .sum();
    }

    /**
     * @return 50 if the five dices have the same values, 0 otherwise
     */
    public int yatzy() {
        return Objects.equals(sortedDiceValues[0], sortedDiceValues[sortedDiceValues.length - 1]) ? 50 : 0;
    }

    /**
     * @param diceValue the value of the dice we want the sum of
     * @return the sum of the dice having the corresponding value
     */
    private int sumDicesWithValue(int diceValue) {
        return (int) Arrays.stream(sortedDiceValues)
            .filter(x -> x == diceValue)
            .count() * diceValue;
    }


    public int ones() {
        return sumDicesWithValue(1);
    }

    public int twos() {
        return sumDicesWithValue(2);
    }

    public int threes() {
        return sumDicesWithValue(3);
    }

    public int fours() {
        return sumDicesWithValue(4);
    }

    public int fives() {
        return sumDicesWithValue(5);
    }

    public int sixes() {
        return sumDicesWithValue(6);
    }

    /**
     * if there are two pair of dice having the same value, it just use the biggest dice value to calculate the result
     *
     * @param numberOfDiceThatShouldHaveTheSameValue The number of dice that should have the same value
     * @return The value * the number of dice that should have the same value if there are enough dice with
     * same values, 0 otherwise
     */
    private int nOfAKind(int numberOfDiceThatShouldHaveTheSameValue) {
        return Arrays.stream(this.sortedDiceValues)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue() >= numberOfDiceThatShouldHaveTheSameValue)
            .max(Comparator.comparingInt(Map.Entry::getKey))
            .map(entry -> entry.getKey() * numberOfDiceThatShouldHaveTheSameValue)
            .orElse(0);
    }


    /**
     * @return 2 * the biggest dice value that is at least in double, 0 if there are no pair
     */
    public int onePair() {
        return nOfAKind(2);
    }

    /**
     * @return 2* the sum of the two pair (if there are two pair), 0 otherwise
     */
    public int twoPair() {
        List<Map.Entry<Integer, Long>> diceValueAndCountStream = Arrays.stream(this.sortedDiceValues)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream().filter(entry -> entry.getValue() >= 2).toList();

        if (diceValueAndCountStream.size() > 1) {
            return diceValueAndCountStream.stream()
                .mapToInt(entry -> entry.getKey() * 2)
                .sum();
        } else {
            return 0;
        }
    }


    public int threeOfAKind() {
        return nOfAKind(3);
    }


    public int fourOfAKind() {
        return nOfAKind(4);
    }

    /**
     *
     * @return 15 if the dice numbers are a small straight (meaning distinct from 1 to 5), 0 otherwise
     */
    public int smallStraight() {
        return Arrays.equals(this.sortedDiceValues, new Integer[]{1, 2, 3, 4, 5})? 15 : 0;
    }

    /**
     *
     * @return 20 if the dice numbers are a large straight (meaning distinct from 2 to 6), 0 otherwise
     */
    public int largeStraight() {
        return Arrays.equals(this.sortedDiceValues, new Integer[]{2, 3, 4, 5, 6})? 20 : 0;
    }

    /**
     *
     * @return the sum of all the dices if it is a fullHouse (2 dice number are the same and three other dice numbers
     *  are also the same)
     */
    public int fullHouse() {
        var diceNumbersCount = Arrays.stream(this.sortedDiceValues)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        boolean containsTwoDiceWithSameValue = diceNumbersCount.values().stream().anyMatch(count -> count == 2);
        boolean containsThreeDiceWithSameValue = diceNumbersCount.values().stream().anyMatch(count -> count == 3);

        return (containsTwoDiceWithSameValue && containsThreeDiceWithSameValue) ? Arrays.stream(this.sortedDiceValues).mapToInt(Integer::intValue).sum() : 0;
    }
}



