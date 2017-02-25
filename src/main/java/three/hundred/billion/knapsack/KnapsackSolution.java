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

public class KnapsackSolution {

    String approach;
    public List<Item> items;
    public double weight;
    public double value;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(approach);
        builder.append(": ");
        builder.append(value);
        builder.append(" ");
        builder.append(weight);
        builder.append("\n");

        Collections.sort(items, Item.byLabel());

        for (Item item : items) {
            builder.append(item.label);
            builder.append(" ");
        }

        return builder.toString();
    }
}