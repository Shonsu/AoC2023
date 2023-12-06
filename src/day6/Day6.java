package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day6 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/day6/input.txt"));
        String line;
        long[] time = new long[0];
        long[] distance = new long[0];
        while ((line = br.readLine()) != null) {
            String[] split = line.split(":");
            if (split[0].equals("Time")) {
                time = Arrays.stream(split[1].trim().split("\\s+")).mapToLong(Long::parseLong).toArray();
            } else {
                distance = Arrays.stream(split[1].trim().split("\\s+")).mapToLong(Long::parseLong).toArray();
            }
        }
        System.out.println(Arrays.toString(time));
        System.out.println(Arrays.toString(distance));

        long product = multipledNumberOfWays(time, distance);

        System.out.println("Part 1 answer: " + product);

        String timeString = Arrays.stream(time).mapToObj(Long::toString).collect(Collectors.joining());
        String distanceString = Arrays.stream(distance).mapToObj(Long::toString).collect(Collectors.joining());

        long productPt2 = multipledNumberOfWays(new long[]{Long.parseLong(timeString)}, new long[]{Long.parseLong(distanceString)});

        System.out.println("Part 2 answer: " + productPt2);
        
    }

    private static long multipledNumberOfWays(long[] time, long[] distance) {
        long product = 1;
        for (int j = 0; j < time.length; j++) {
            int count = 0;
            for (int i = 1; i < time[j]-1; i++) {
                long i1 = i * (time[j] - i);
                if (i1 > distance[j]){
              //      System.out.println("time: " + i + ", speed: " + i1 +" > "+(distance[j]));
                    count++;
                }
            }
            product*=count;
        }
        return product;
    }
}
