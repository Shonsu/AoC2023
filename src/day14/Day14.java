package day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day14 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/day14/input.txt"));
        List<String> platformInput = new ArrayList<>();
        List<Rock> rocks = new ArrayList<>();
        String line;
        int lineLength = 0;
        int row = 0;
        while ((line = br.readLine()) != null) {
            platformInput.add(line);
            lineLength = line.length();
            for (int i = 0; i < line.length(); i++) {
                rocks.add(new Rock(row, i, line.charAt(i)));
            }
            row++;
        }

        char[][] platform = new char[platformInput.size()][platformInput.getFirst().length()];

        int j = 0;
        for (String s : platformInput) {
            for (int i = 0; i < s.length(); i++) {
                platform[j][i] = s.charAt(i);
            }
            j++;
        }
        System.out.println("gathering weights");
        List<Integer> weights = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            platform = tiltNorth(platform);

            //west
            platform = rotatePlatformRight(platform);
            platform = tiltNorth(platform);

            //south
            platform = rotatePlatformRight(platform);
            platform = tiltNorth(platform);

            //east
            platform = rotatePlatformRight(platform);
            platform = tiltNorth(platform);

            //north
            platform = rotatePlatformRight(platform);

            int weight = calculateLoad(platform);
//            if(weights.contains(weight)){
//            System.out.println("repeated: " + weight);
//                break;
//            }
            //    System.out.println(i + " " + weight);
            weights.add(weight);


        }
        System.out.println(weights.size());
//        for (int i = 125; i < 145; i++) {
//            System.out.println(i + " " + weights.get(i));
//        }
        List<Integer> pattern = findPattern(weights);
        System.out.println(pattern);
        List<Integer> pattern2 = findPattern2(weights);
        System.out.println(pattern2);

        int indexOfVal = (1000000000 % pattern.size())-1;
        int indexOfVal2 = (1000000000 % pattern2.size())-1;
        System.out.println(indexOfVal);
        System.out.println(indexOfVal2);
        if (indexOfVal < 0) {
            indexOfVal = pattern.size() - 1;
        }
        if (indexOfVal2 < 0) {
            indexOfVal2 = pattern2.size() - 1;
        }

        System.out.println(pattern.get(indexOfVal));
        System.out.println(pattern2.get(indexOfVal2));
    }

    private static List<Integer> findPattern(List<Integer> weights) {
        List<Integer> result = new ArrayList<>();
        int slowPointer = 0;
        int fastPointer = 1;
        int slowValue = weights.get(slowPointer);
        int fastValue = weights.get(fastPointer);
        int power = 1;
        int length = 1;

        while (fastPointer < weights.size() && slowValue != fastValue) {
            System.out.println(slowPointer + " = " + fastPointer);
            System.out.println(slowValue + " " + fastValue);
            if (length == power) {
                System.out.println("Power up");
                power *= 2;
                length = 0;
                slowPointer = fastPointer;
                slowValue = weights.get(fastPointer);
            }
            fastPointer++;
            fastValue = weights.get(fastPointer);
            ++length;
        }
//        System.out.println(slowPointer + " = " + fastPointer);
//        System.out.println(slowValue + " " + fastValue);
        if (fastPointer == weights.size() - 1) {
            throw new IllegalArgumentException("Cycle not found");
        } else {
            System.out.printf("Cycle of length %d found.\n", length);
            System.out.printf("SlowValue %d, fastValue %d.\n", weights.get(slowPointer), weights.get(fastPointer));
            System.out.printf("SlowPointer %d, fastPointer %d.\n", slowPointer, fastPointer);
        }
        System.out.println(slowPointer + " = " + fastPointer);
        System.out.println(weights.get(slowPointer) + " = " + weights.get(fastPointer));

        System.out.println("fastpointer before zero:" + fastPointer);
//        slowPointer = fastPointer = 0;
//        while (length > 0) {
//            fastPointer++;
//            length--;
//        }

        while (!Objects.equals(weights.get(fastPointer), weights.get(slowPointer))) {
            //System.out.println(weights.get(fastPointer) + " = " + weights.get(slowPointer));
            fastPointer++;
            slowPointer++;
        }
        System.out.println(fastPointer + " = " + slowPointer);
        System.out.println(weights.get(fastPointer) + " = " + weights.get(slowPointer));

        for (int i = slowPointer; i < fastPointer; i++) {
            Integer e = weights.get(i);
            System.out.println(e);
            result.add(e);
        }
        return result;
    }

    private static void printPlatform(List<Rock> rocks, int lineLength) {
        for (int i = 1; i <= rocks.size(); i++) {
            System.out.print(rocks.get(i).type());
            if ((i + 1) % lineLength == 0) {
                System.out.println();
            }
        }
    }

    private static char[][] rotatePlatformRight(char[][] platform) {
        char[][] tmpPlatform = new char[platform[0].length][platform.length];

        for (int i = 0; i < tmpPlatform.length; i++) {
            for (int k = tmpPlatform[0].length - 1; k >= 0; k--) {
//                System.out.println("i: " + i);
//                System.out.println("k: " + k);
                tmpPlatform[i][k] = platform[tmpPlatform[0].length - k - 1][i];
            }
//            System.out.println("new column");
        }
        return tmpPlatform;
    }

    private static int calculateLoad(char[][] platform2) {
        int sum = 0;
        for (int i = 0; i < platform2.length; i++) {
            int rockRowNumber = 0;
            for (int k = 0; k < platform2[0].length; k++) {
                if (platform2[i][k] == 'O') {
                    rockRowNumber++;
                }
            }
            sum += rockRowNumber * (platform2.length - i);
        }
        return sum;
    }

    private static char[][] tiltNorth(char[][] platform2) {
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
        return platform2;
    }

    private static List<Integer> findPattern2(final List<Integer> values) {
        int slowIdx = 0;
        int slow = values.get(slowIdx);
        int fastIdx = 0;
        int fast;
        boolean met = false;
        while (fastIdx + 5 < values.size()) {
            slowIdx++;
            slow = values.get(slowIdx);
            fastIdx += 2;
            fast = values.get(fastIdx);
            if (slow == fast) {
                met = true;
                break;
            }
        }
        if (!met) {
            throw new IllegalArgumentException("No cycle found");
        }
        final int startIdx = slowIdx;
        slowIdx++;
        int slow2 = values.get(slowIdx);
        while (slow2 != slow) {
            slowIdx++;
            slow2 = values.get(slowIdx);
        }
        final List<Integer> pattern = new ArrayList<>();
        System.out.println("findPattern2:" + startIdx + " " + slowIdx);
        for (int i = startIdx; i < slowIdx; i++) {
            pattern.add(values.get(i));
        }
        return pattern;
    }
}
