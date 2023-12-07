package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/day2/input.txt"));

        String line;
        String[] set = new String[0];

//        Map<Color, Integer> cubes = new HashMap<>();
//        cubes.put(Color.red, 12);
//        cubes.put(Color.green, 13);
//        cubes.put(Color.blue, 14);
        List<Integer> possibleGameId = new ArrayList<>();
        int sum = 0;
        while ((line = br.readLine()) != null) {
            Map<Color, Integer> cubes = new HashMap<>();
            cubes.put(Color.red, 0);
            cubes.put(Color.green, 0);
            cubes.put(Color.blue, 0);
            String[] game = line.split(":");
            int gameId = Integer.parseInt(game[0].split("\\s+")[1]);
            String[] handfuls = game[1].trim().split(";");
            // System.out.println(gameId);
            boolean possible = true;
            for (int i = 0; i < handfuls.length /*&& possible*/; i++) {
//                System.out.println(handfuls[i].trim());
                String[] handful = handfuls[i].trim().split(", ");
                for (int j = 0; j < handful.length /*&& possible*/; j++) {
                    String[] tmp = handful[j].trim().split("\\s+");
//                    if (cubes.get(Color.valueOf(tmp[1])) < Integer.parseInt(tmp[0])) {
//                        // System.out.println("Game of ID:" + gameId + " is impossible.");
//                        possible = false;
//                    }
                    //System.out.println(tmp[0]);
                    if (cubes.get(Color.valueOf(tmp[1])) < Integer.parseInt(tmp[0])) {
                        cubes.put(Color.valueOf(tmp[1]), Integer.parseInt(tmp[0]));
                    }
                }
            }
            int result = 1;
            for (Integer v : cubes.values()
            ) {
               // System.out.println(v);
                result*=v;
            }
            System.out.println(result);
//            if (possible) {
//                possibleGameId.add(gameId);
//            }
            sum+=result;
        }
        //possibleGameId.forEach(System.out::println);
//        Integer i = possibleGameId.stream().reduce(Integer::sum).orElse(0);
//        System.out.println("Sum of possible games IDs: " + i);

//        for (Integer v : cubes.values()
//        ) {
//            System.out.println(v);
//        }
        System.out.println(sum);

    }

    enum Color {
        red, green, blue
    }

}
