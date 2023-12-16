package day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/day11/input.txt"));
        Map<Integer, Galaxy> outerSpace = new HashMap<>();
        Set<Integer> noGalaxiesHorizontally = new HashSet<>();
        Set<Integer> noGalaxiesVertically = new HashSet<>();
        long sum = 0;
        String line;
        int row = 0;
        int galaxiesCount = 0;
        int spaceWidth = 0;
        while ((line = br.readLine()) != null) {
            spaceWidth = line.length();
            int index = line.indexOf('#');
            if (index == -1) {
                noGalaxiesHorizontally.add(row);
            }
            while (index >= 0) {
                outerSpace.put(galaxiesCount++, new Galaxy(index, row));
                index = line.indexOf('#', index + 1);
            }
            row++;

        }
        noGalaxiesVertically = IntStream.range(0, spaceWidth).filter(i -> outerSpace.values().stream().allMatch(galaxy -> galaxy.x() != i)).boxed().collect(Collectors.toSet());
        System.out.println(outerSpace);
        System.out.println(noGalaxiesVertically);
        System.out.println(noGalaxiesHorizontally);

//        for (Galaxy g : outerSpace.values()) {
//            for (Integer i : outerSpace.keySet()) {
//                int aX = outerSpace.get(i).x();
//                int aY = outerSpace.get(i).y();
//                int bX = g.x();
//                int bY = g.y();
//                sum += Math.abs(aX - bX) + Math.abs(aY - bY);
//                long count = noGalaxiesHorizontally.stream().filter(empty -> empty < bX && empty > aX).count();
//                count += noGalaxiesVertically.stream().filter(empty -> empty < bY && empty > aY).count();
//                sum += 2 * count;
//            }
//        }

        for (Integer i : outerSpace.keySet()) {
            Galaxy galaxy = outerSpace.get(i);
            for (int j = i + 1; j <= outerSpace.size(); j++) {
                Galaxy g = outerSpace.get(j);
                if (g != null) {
                    int aX = galaxy.x();
                    int aY = galaxy.y();
                    int bX = g.x();
                    int bY = g.y();
                    int tmp = Math.abs(aX - bX) + Math.abs(aY - bY);

                    long countX = noGalaxiesVertically.stream().filter(empty -> (aX < bX) ? empty < bX && empty > aX : empty > bX && empty < aX).count();
                    long countY = noGalaxiesHorizontally.stream().filter(empty -> empty < bY && empty > aY).count();
//                    sum += tmp + countX + countY;
                    sum += tmp + countX * 999999 + countY * 999999;
                    System.out.println(galaxy + " " + g + " = " + tmp + " " + countX + " " + countY);
                }
            }
        }

        System.out.println(sum);
    }

    record Galaxy(int x, int y) {
    }
}