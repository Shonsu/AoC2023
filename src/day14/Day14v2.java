package day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14v2 {
    public static void main(String[] args) throws IOException {

        List<String> lines = Files.readAllLines(Path.of("src/day14/input.txt"));
        Platform platform = new Platform();
        Map<String, Integer> resultsMemo = new HashMap<>();
        List<Integer> weights = new ArrayList<>();
        platform.parse(lines);
        platform.tiltNorth();
        System.out.println("Total load from part1: " + platform.calculateLoad());

        platform.parse(lines);

        int times = 300;
        int i = 0;
        for (; i < times; i++) {
            platform.cycleNTimes(1);
            String key = platform.toString();
            int value = platform.calculateLoad();
            if (resultsMemo.containsKey(key) && resultsMemo.get(key) == value) {
                break;
            }
            weights.add(value);
            resultsMemo.put(key, value);
        }
        int i1 = (1_000_000_000 % (resultsMemo.size()));
        System.out.println("Total load from part2: " + weights.get(i1));
    }
}
