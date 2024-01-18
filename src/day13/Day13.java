package day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day13 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/day13/input.txt"));
        List<List<String>> patterns = new ArrayList<>();
        List<String> pattern = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) {
                patterns.add(pattern);
                pattern = new ArrayList<>();
            } else {
                pattern.add(line);
            }
        }
        patterns.add(pattern);

        int sum = 0;
        int sumWithSmudges = 0;
        for (List<String> p : patterns) {

            List<String> rotatedRightPatterns = rotatePatternRight(p);

            sum += calculateRowBefore(rotatedRightPatterns) + 100 * calculateRowBefore(p);
            sumWithSmudges += calculateRowBeforeWithSmudges(rotatedRightPatterns) + 100 * calculateRowBeforeWithSmudges(p);
        }

        System.out.println("Sum: " + sum);
        System.out.println("sumWithSmudges: " + sumWithSmudges);
    }

    private static List<String> rotatePatternRight(List<String> p) {
        List<String> rotatedRightPatterns = new ArrayList<>();
        List<StringBuilder> sbList = new ArrayList<>();
        for (int i = 0; i < p.getFirst().length(); i++) {
            StringBuilder sb = new StringBuilder();
            sbList.add(sb);
        }
        for (int i = 0; i < p.size(); i++) {
            for (int j = 0; j < p.getFirst().length(); j++) {
                sbList.get(j).insert(0, p.get(i).charAt(j));
            }
        }
        for (StringBuilder sb : sbList) {
            rotatedRightPatterns.add(sb.toString());
        }
        return rotatedRightPatterns;
    }

    private static int calculateRowBefore(List<String> p) {
        int rowsBefore = 0;
        boolean matches = false;
        for (int i = 1; i < p.size(); i++) {
            String prev = p.get(i - 1);
            String next = p.get(i);
            if (prev.equals(next)) {
                rowsBefore = i - 1;
                matches = true;
                int elToCheck = Math.min(i - 1, p.size() - i - 1);
                for (int j = 0; j < elToCheck && matches; j++) {
                    prev = p.get(i - 2 - j);
                    next = p.get(i + 1 + j);
                    if (!prev.equals(next)) {
                        matches = false;
                    }
                }
            }
            if (matches) {
                return rowsBefore + 1;
            }
        }
        return 0;
    }

    private static int calculateRowBeforeWithSmudges(List<String> p) {
        for (int i = 1; i < p.size(); i++) {
            String prev = p.get(i - 1);
            String next = p.get(i);

            int smudges = countSmudges(prev, next);

            if (smudges == 0 || smudges == 1) {
                int elToCheck = Math.min(i - 1, p.size() - i - 1);
                boolean matches = true;
                boolean smudgeFound = false;

                for (int j = 0; j < elToCheck; j++) {
                    prev = p.get(i - 2 - j);
                    next = p.get(i + 1 + j);
                    int nrSmudges = countSmudges(prev, next);

                    if (nrSmudges > 1 || (smudges == 1 && nrSmudges == 1) || (nrSmudges == 1 && smudgeFound)) {
                        matches = false;
                        break;
                    } else if (nrSmudges == 1) {
                        smudgeFound = true;
                    }
                }
                if (matches && (smudges == 1 ^ smudgeFound)) {
                    System.out.println(i);
                    return i;
                }
            }
        }
        System.out.println(0);
        return 0;
    }

    private static int countSmudges(String s1, String s2) {
        int smudges = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                smudges++;
            }
        }
        return smudges;
    }
}
