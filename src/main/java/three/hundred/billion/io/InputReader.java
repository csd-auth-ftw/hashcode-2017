package three.hundred.billion.io;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by nikos on 24/2/2017.
 */
public class InputReader {
    private File file;
    private BufferedReader reader;

    public InputReader(String filename) {
        file = new File(filename);

        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    public ArrayList<Integer> readNextLine() {
        try {
            String line = reader.readLine();

            if (line == null)
                return null;

            String[] parts = line.split(" ");
            ArrayList<Integer> nums = new ArrayList<Integer>();

            for (String part: parts)
                nums.add(Integer.parseInt(part));

            return nums;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
