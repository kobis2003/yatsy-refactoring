package yatzy;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class is used for yatsy game score calculation.
 * A yatzy seems to be played by rolling 5 dices.
 * A new class must be instanced every time we roll the dices and then we can
 * use the public function to check the yatsy combination
 */
public class Yatzy {


    private final Integer[] sortedDiceValues;
    private final Set<Map.Entry<Integer, Long>> countDiceByValue;


    public Yatzy(int d1, int d2, int d3, int d4, int d5) {
        Integer[] diceValues = new Integer[]{d1, d2, d3, d4, d5};
        if (Arrays.stream(diceValues).anyMatch(value -> value < 1 || value > 6)) {
            throw new IllegalArgumentException("Dice values must be between 1 and 6.");
        }

        this.sortedDiceValues = Arrays.stream(diceValues).sorted().toArray(Integer[]::new);

        this.countDiceByValue = Arrays.stream(this.sortedDiceValues).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet();
    }

    /**
     * @return the total sum of all dices
     */
    public int chance() {
        return Arrays.stream(sortedDiceValues).mapToInt(Integer::intValue).sum();
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
    private int sumDicesHavingValue(int diceValue) {
        return Arrays.stream(sortedDiceValues).filter(x -> x == diceValue).mapToInt(Integer::intValue).sum();
    }


    public int ones() {
        return sumDicesHavingValue(1);
    }

    public int twos() {
        return sumDicesHavingValue(2);
    }

    public int threes() {
        return sumDicesHavingValue(3);
    }

    public int fours() {
        return sumDicesHavingValue(4);
    }

    public int fives() {
        return sumDicesHavingValue(5);
    }

    public int sixes() {
        return sumDicesHavingValue(6);
    }


    /**
     * @return 2 * the biggest dice value that is at least in double, 0 if there are no pair
     */
    public int onePair() {
        return this.countDiceByValue.stream().filter(entry -> entry.getValue() >= 2).max(Comparator.comparingInt(Map.Entry::getKey)).map(entry -> entry.getKey() * 2).orElse(0);
    }

    /**
     * if there are two pair of dice having the same value, it just use the biggest dice value to calculate the result
     *
     * @param minNumberOfDiceWithTheSameValue The number of dice that should have the same value
     * @return The value * the number of dice that should have the same value if there are enough dice with
     * same values, 0 otherwise
     */
    private int nOfAKind(int minNumberOfDiceWithTheSameValue) {
        return this.countDiceByValue.stream().filter(entry -> entry.getValue() >= minNumberOfDiceWithTheSameValue).mapToInt(entry -> entry.getKey() * minNumberOfDiceWithTheSameValue).sum();
    }


    /**
     * @return 2* the sum of the two pair (if there are two pair), 0 otherwise
     */
    public int twoPair() {
        if (this.countDiceByValue.stream().filter(entry -> entry.getValue() >= 2).count() > 1) {
            return nOfAKind(2);
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
     * @return 15 if the dice numbers are a small straight (meaning distinct from 1 to 5), 0 otherwise
     */
    public int smallStraight() {
        return Arrays.equals(this.sortedDiceValues, new Integer[]{1, 2, 3, 4, 5}) ? 15 : 0;
    }

    /**
     * @return 20 if the dice numbers are a large straight (meaning distinct from 2 to 6), 0 otherwise
     */
    public int largeStraight() {
        return Arrays.equals(this.sortedDiceValues, new Integer[]{2, 3, 4, 5, 6}) ? 20 : 0;
    }

    /**
     * @return the sum of all the dices if it is a fullHouse (2 dice number are the same and three other dice numbers
     * are also the same)
     */
    public int fullHouse() {
        if(this.countDiceByValue.stream().anyMatch(entry -> entry.getValue() == 2) &&
            this.countDiceByValue.stream().anyMatch(entry -> entry.getValue() == 3)) {
            return this.chance();
        }
        return 0;
    }


}



