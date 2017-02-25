/**
 * patrickherrmann/Knapsack
 * ------------------------
 * 4 implementations of the 0-1 three.hundrer.billion.knapsack problem and a comparison of their effectiveness
 *
 * https://github.com/patrickherrmann/Knapsack
 *
 */
package three.hundred.billion.knapsack;

import java.util.*;

public abstract class KnapsackSolver {

    protected List<Item> items;
    protected int capacity;

    protected KnapsackSolver(List<Item> items, int capacity) {
        this.items = items;
        this.capacity = capacity;
    }

    public abstract KnapsackSolution solve();

    public double getWeight(List<Item> items) {
        double weight = 0;
        for (Item item : items) {
            weight += item.weight;
        }
        return weight;
    }

    public double getValue(List<Item> items) {
        double value = 0;
        for (Item item : items) {
            value += item.value;
        }
        return value;
    }
}