/**
 * patrickherrmann/Knapsack
 * ------------------------
 * 4 implementations of the 0-1 knapsack problem and a comparison of their effectiveness
 *
 * https://github.com/patrickherrmann/Knapsack
 *
 */
package knapsack;

import java.util.*;

public class Item {

    public static Comparator<Item> byLabel() {
        return new Comparator<Item>() {
            public int compare(Item i1, Item i2) {
                return i1.label - i2.label;
            }
        };
    }

    public static Comparator<Item> byRatio() {
        return new Comparator<Item>() {
            public int compare(Item i1, Item i2) {
                return Double.compare(i2.getRatio(), i1.getRatio());
            }
        };
    }

    public int label;
    public double value;
    public double weight;

    public double getRatio() {
        return value / weight;
    }
}