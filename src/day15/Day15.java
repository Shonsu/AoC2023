package day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day15 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("src/day15/input.txt"));
        List<String> sequences = new ArrayList<>();
        int result = 0;

        s.useDelimiter(",");
        while (s.hasNext()) {
            sequences.add(s.next());
        }

        for (String sequence : sequences) {
            result += hash(sequence);
        }
        System.out.println("Result of part 1: " + result);

        List<LinkedHashMap<String, Integer>> boxes = new ArrayList<>();
        for (int i = 0; i <= 256; i++) {
            boxes.add(new LinkedHashMap<>());
        }

        for (String sequence : sequences) {
            String[] split = sequence.split("[=\\-]");
            String label = split[0];
            int boxNumber = hash(label);
            LinkedHashMap<String, Integer> box = boxes.get(boxNumber);
            if (sequence.contains("=")) {
                int focalLength = Integer.parseInt(split[1]);
                if (box.containsKey(label)) {
                    box.replace(label, focalLength);
                } else {
                    box.putLast(label, focalLength);
                }
            } else {
                box.remove(label);
            }
        }

        int focusingPowerOfLensConfiguration = getPowerOfLensConfiguration(boxes);

        System.out.println("focusingPowerOfLensConfiguration of part2: " + focusingPowerOfLensConfiguration);
    }

    private static int getPowerOfLensConfiguration(List<LinkedHashMap<String, Integer>> boxes) {
        int boxIndex = 0;
        int focusingPowerOfLensConfiguration = 0;
        for (LinkedHashMap<String, Integer> box : boxes) {
            if (!box.isEmpty()) {
                int lensesIndex = 0;
                for (Integer focalLength : box.values()) {
                    int i = (boxIndex + 1) * (lensesIndex + 1) * focalLength;
                    lensesIndex++;
                    focusingPowerOfLensConfiguration += i;
                }
            }
            boxIndex++;
        }
        return focusingPowerOfLensConfiguration;
    }

    private static int hash(String str) {
        int result = 0;
        for (int i = 0; i < str.length(); i++) {
            result += str.charAt(i);
            result *= 17;
            result %= 256;
        }
        return result;
    }

}
