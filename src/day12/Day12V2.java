package day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day12V2 {
    private static final Map<Input, Long> memoMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/day12/input.txt"));
        String line;
        List<Integer> groups;
        String springs;
        long ans = 0;
        while ((line = br.readLine()) != null) {
            String[] split = line.split("\\s+");
            springs = split[0];
            springs = (springs + '?').repeat(5);
            springs = springs.substring(0, springs.length() - 1);
//            System.out.println(springs);
            groups = Arrays.stream(split[1].trim().split(",")).mapToInt(Integer::parseInt).boxed().toList();
            List<List<Integer>> lists = Collections.nCopies(5, groups);
            groups = lists.stream().flatMap(List::stream).toList();
//            System.out.println(groups);


            long tmp = calculatePermutation(springs, groups);
            ans += tmp;
        }
        System.out.println(ans);
    }

    private static long calculatePermutation(String springs, List<Integer> groups) {
        Input input = new Input(springs, groups);
        if (memoMap.containsKey(input)) {
            System.out.println(springs + " " + groups + " value:" + memoMap.get(input));
            return memoMap.get(input);
        }
        if (springs.isBlank()) {
            return groups.isEmpty() ? 1 : 0;
        }
        long permutation = 0;

        if (springs.charAt(0) == '.') {
            permutation = calculatePermutation(springs.substring(1), groups);
        } else if (springs.charAt(0) == '?') {
            permutation = calculatePermutation('.' + springs.substring(1), groups) +
                    calculatePermutation('#' + springs.substring(1), groups);
        } else {
            if (groups.isEmpty()) {
                permutation = 0;
            } else {
                if (groups.getFirst() <= springs.length()
                        && springs.chars().limit(groups.getFirst()).allMatch(c -> c == '?' || c == '#')) {

                    List<Integer> newGroups = groups.subList(1, groups.size());

                    if (groups.getFirst() == springs.length()) {
                        permutation = newGroups.isEmpty() ? 1 : 0;
                    } else if (springs.charAt(groups.getFirst()) == '?') {
                        permutation = calculatePermutation('.' + springs.substring(groups.getFirst() + 1), newGroups);
                    } else if (springs.charAt(groups.getFirst()) == '.') {
                        permutation = calculatePermutation(springs.substring(groups.getFirst() + 1), newGroups);
                    } else /*if (springs.charAt(groups.getFirst()) == '#')*/ {
                        permutation = 0;
                    }
                } else {
                    permutation = 0;
                }
            }
        }
        memoMap.put(input, permutation);
        return permutation;
    }

    private record Input(String spring, List groups) {
    }
}