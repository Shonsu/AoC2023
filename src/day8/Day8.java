package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/day8/input.txt"));
        String line;
        char[] directions = br.readLine().trim().toCharArray();
        Pattern pattern = Pattern.compile("([A-Z0-9]{3}) = \\(([A-Z0-9]{3}), ([A-Z0-9]{3})");
        Map<String, Node> map = new HashMap<>();
        Map<String, Node> startNodes = new HashMap<>();
        while ((line = br.readLine()) != null) {
            Matcher matcher = pattern.matcher(line.trim());
            if (matcher.find()) {
                char c = matcher.group(1).charAt(2);
                if (c == 'A') {
                    startNodes.put(matcher.group(1), new Node(matcher.group(2), matcher.group(3)));
                }
                map.put(matcher.group(1), new Node(matcher.group(2), matcher.group(3)));
            }
        }
        System.out.println("Start nodes: " + startNodes);

        boolean foundZZZ = false;
        //String next = "AAA";
        long steps = 0;

        Map<String, Node> tmpNodes = new HashMap<>();
        List<Long> stepsToZ = new ArrayList<>();
        for (String next : startNodes.keySet()) {
            System.out.println(next);
            String startNode = next;
            for (int i = 0; i < directions.length && !foundZZZ; i++) {
                steps++;

                next = switch (directions[i]) {
                    case 'L' -> map.get(next).left();
                    case 'R' -> map.get(next).right();
                    default -> throw new IllegalStateException("Unexpected value: " + directions[i]);
                };

                if (next.charAt(2) == 'Z') {
                    System.out.println("steps for startNode: " + startNode + " = " + steps);
                    stepsToZ.add(steps);
                    foundZZZ = true;
                } else if (i == directions.length - 1) {
                    i = -1;
                }
            }
            foundZZZ = false;
            steps = 0;
        }
        Optional<Long> reduce = stepsToZ.stream().reduce((aLong, aLong2) -> (aLong * aLong2) / gcdByEuclidsAlgorithm(aLong, aLong2));
        System.out.println(reduce.get());
    }

    record Node(String left, String right) {
    }

    private static long gcdByEuclidsAlgorithm(long n1, long n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcdByEuclidsAlgorithm(n2, n1 % n2);
    }
}
