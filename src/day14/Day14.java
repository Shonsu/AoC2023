package day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day14 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/day14/input.txt"));
        List<String> platform = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            platform.add(line);
        }

        char[][] platform2 = new char[platform.size()][platform.getFirst().length()];

        int j = 0;
        for (String s : platform) {
            for (int i = 0; i < s.length(); i++) {
                platform2[j][i] = s.charAt(i);
            }
            j++;
        }
        for (int col = 0; col < platform2[0].length; col++) {
            int emptySpaces = 0;
            for (int i = 0; i < platform2.length; i++) {
                char c = platform2[i][col];
                if (c == 'O') {
                    if (emptySpaces > 0) {
                        platform2[i - emptySpaces][col] = 'O';
                        platform2[i][col] = '.';
                    }
                } else if (c == '.') {
                    emptySpaces++;
                } else {
                    emptySpaces = 0;
                }
            }
        }
        long sum = 0;
        for (int i = 0; i < platform2.length; i++) {
            long rockRowNumber = 0;
            for (int k = 0; k < platform2[0].length; k++) {
                if (platform2[i][k] == 'O') {
                    rockRowNumber++;
                }
            }
            sum += rockRowNumber * (platform2.length - i);
        }
        System.out.println(sum);
    }
}
