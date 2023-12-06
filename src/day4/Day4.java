package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day4 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/day4/input.txt"));
        List<Scratch> scratchcards = new ArrayList<>();
        Map<Integer, Scratch> scratchcardsMap = new HashMap<>();
        String line;
        int lineCount = 1;
        while ((line = br.readLine()) != null) {
            String[] split = line.split("[:|]");
            String[] tmp = split[1].strip().split("\\s+");
            List<Integer> winNumbers = Arrays.stream(tmp).mapToInt(Integer::parseInt).boxed().toList();
            tmp = split[2].strip().split("\\s+");
            List<Integer> yourNumbers = Arrays.stream(tmp).mapToInt(Integer::parseInt).boxed().toList();

            Scratch scratch = new Scratch(winNumbers, yourNumbers);
            scratchcards.add(scratch);
            scratchcardsMap.put(lineCount++, scratch);
        }
        double sum = 0;
        for (Scratch p : scratchcards) {
            double count = 0;
            for (Integer i : p.your()) {
                if (p.win().contains(i)) {
                    count++;
                }
            }
            if (count > 0) {
                count = Math.pow(2, count - 1);
            }
            sum += count;
        }
        System.out.println(sum);
        int subSum = 0;

        for (Integer i : scratchcardsMap.keySet()) {
            subSum += 1 + subScratching(i, scratchcardsMap);
        }
        System.out.println(subSum);

    }

    private static int subScratching(Integer i, Map<Integer, Scratch> scratchcardsMap) {
        int count = 0;
        int sum = 0;
        Scratch scratch = scratchcardsMap.get(i);
        for (Integer k : scratch.your()) {
            if (scratch.win().contains(k)) {
                count++;
                sum += 1 + subScratching(i + count, scratchcardsMap);
            }
        }
        return sum;
    }

    record Scratch(List<Integer> win, List<Integer> your) {
    }
}
