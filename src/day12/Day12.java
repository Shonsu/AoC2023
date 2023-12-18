package day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day12 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/day12/input.txt"));
        String line;

        char[] springs = null;
        int[] groups = null;
        int ans = 0;
        while ((line = br.readLine()) != null) {
            String[] split = line.trim().split("\\s+");
            groups = Arrays.stream(split[1].split(",")).mapToInt(Integer::parseInt).toArray();
            springs = (split[0].trim() + '.').toCharArray();
            ans += generateAlternateSpringBlocks(springs, "", 0, groups);
        }
        System.out.println(ans);
    }

    static int generateAlternateSpringBlocks(char[] springs, String alternative, int pos, int[] groups) {

        if (springs.length == pos) {
            return isValid(alternative, groups) ? 1 : 0;
        } else {
            if (springs[pos] == '?') {
                return generateAlternateSpringBlocks(springs, alternative + '#', pos + 1, groups) +
                        generateAlternateSpringBlocks(springs, alternative + '.', pos + 1, groups);
            } else {
                return generateAlternateSpringBlocks(springs, alternative + springs[pos], pos + 1, groups);
            }
        }
    }

    static boolean isValid(String alternate, int[] groups) {
        List<Integer> tmp = new LinkedList<>();
        int count = 0;
        for (int i = 0; i < alternate.length(); i++) {

            if (alternate.charAt(i) == '.') {
                if (count > 0) {
                    tmp.add(count);
                }
                count = 0;
            } else if (alternate.charAt(i) == '#') {
                count++;
            }
        }
        int[] array = tmp.stream().mapToInt(Integer::intValue).toArray();
        return Arrays.equals(array, groups);
    }
}
