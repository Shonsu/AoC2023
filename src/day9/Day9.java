package day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day9 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/day9/input.txt"));
        String line;
        long result = 0;
        int count = 0;
        while ((line = br.readLine()) != null) {
            long[] history = Arrays.stream(line.trim().split("\\s+")).mapToLong(Long::parseLong).toArray();
            long diff = differenceBackwards(history);

            result += diff;
            count++;
        }
        System.out.println(result);
        System.out.println(count);
    }

    private static long difference(long[] tab) {
        long[] tmp = new long[tab.length - 1];
        boolean allZeros = true;
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = tab[i + 1] - tab[i];
            allZeros = tmp[i] == 0 && allZeros;
        }
        if (allZeros) {
            return tab[tab.length - 1];
        }
        return tab[tab.length - 1] + difference(tmp);
    }

    private static long differenceBackwards(long[] tab) {
        long[] tmp = new long[tab.length - 1];
        boolean allZeros = true;
        for (int i = tmp.length; i > 0; i--) {
            tmp[i - 1] = tab[i] - tab[i - 1];
            allZeros = tmp[i - 1] == 0 && allZeros;
        }
        if (allZeros) {
            return tab[tab.length - 1];
        }
        return tab[0] - differenceBackwards(tmp);
    }
}
