import java.io.*;
import java.util.*;

public class TestGenerator {
    private static final int size = 10;
    private static TreeSet<Integer> itemBST = new TreeSet<>();
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("HeapData.in")));

        out.println(size);
        for (int i = 0; i < size; i++) {
            int item = (int)(size * 100 * Math.random());
            while (itemBST.contains(item)) {
                item = (int)(size * 100 * Math.random());
            }
            double priority = (size * 100 * Math.random());
            out.println(item + " " + priority);
            itemBST.add(item);
        }
        
        out.close();
    }
}