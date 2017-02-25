package three.hundred.billion.io;

import three.hundred.billion.knapsack.Item;

import java.io.*;
import java.util.List;

/**
 * Created by nikos on 24/2/2017.
 */
public class OutputWriter {
    private File file;
    BufferedWriter writer;

    public OutputWriter(String filename) {
        file = new File(filename);

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeNumberOfServers(int num) {
        try {
            writer.write(Integer.toString(num));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCache(int id, List<Item> items) {
        StringBuilder str = new StringBuilder(id + " ");

        for (Item item: items) {
            str.append(item.label + " ");
        }

        str.trimToSize();

        try {
            System.out.println(str.toString());
            writer.write(str.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
