package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 {


    public static void main(String[] args) throws IOException {

        Map<Coordinates, Tile> tmp = new HashMap<>();
        Map<Direction, Direction> directionsMatch = new HashMap<>();
        directionsMatch.put(Direction.EAST, Direction.WEST);
        directionsMatch.put(Direction.WEST, Direction.EAST);
        directionsMatch.put(Direction.NORTH, Direction.SOUTH);
        directionsMatch.put(Direction.SOUTH, Direction.NORTH);
        BufferedReader br = new BufferedReader(new FileReader("src/day10/inputexample.txt"));
        Coordinates start = null;
        String line;
        int row = 0;
        while ((line = br.readLine()) != null) {
            char[] charArray = line.toCharArray();
            System.out.println("---------- new row " + row + " ----------");
            for (int i = 0; i < charArray.length; i++) {
                char current = charArray[i];
                System.out.println("Current char: " + current);
                ArrayList<Direction> currentDirections = resolveDirections(current);
                Tile currentTile = new Tile(current, resolveDirections(current), null, null);

                switch (current) {
                    case '|', 'L': {
                        Coordinates up = new Coordinates(row - 1, i);
                        if (row != 0 && tmp.containsKey(up)) {

                            Tile upTile = tmp.get(up);
                            if (checkDirectionMatch(currentTile, upTile, directionsMatch) || tmp.get(up).c()=='S') {
                                System.out.println("match direction true");
                                Tile newCurrentTile = new Tile(currentTile.c(), currentTile.directions(), upTile, null);
                                Tile newUp = new Tile(upTile.c(), upTile.directions(), upTile.previous(), newCurrentTile);
                                tmp.remove(up);
                                tmp.put(up, newUp);
                                tmp.put(new Coordinates(row, i), currentTile);
                                System.out.println(newUp);
                                System.out.println(newCurrentTile);
                            }
                        } else if (!(row == 0 && currentDirections.contains(Direction.NORTH))) {
                            tmp.put(new Coordinates(row, i), new Tile(current, currentDirections, null, null));
                        }
                        break;
                    }
                    case '-', '7': {
                        Coordinates left = new Coordinates(row, i - 1);
                        if (i > 0 && tmp.containsKey(left)) {
                            Tile leftTile = tmp.get(left);
                            if (checkDirectionMatch(currentTile, leftTile, directionsMatch) || tmp.get(left).c()=='S') {
                                System.out.println("match direction true");
                                Tile newCurrentTile = new Tile(currentTile.c(), currentTile.directions(), leftTile, null);
                                Tile newLeft = new Tile(leftTile.c(), leftTile.directions(), leftTile.previous(), newCurrentTile);
                                tmp.remove(left);
                                tmp.put(left, newLeft);
                                tmp.put(new Coordinates(row, i), newCurrentTile);
                                System.out.println(leftTile);
                                System.out.println(newCurrentTile);
                            }
                        }
                        break;
                    }
//                    case 'L':{
//                        Cord up = new Cord(row - 1, i);
//                        if (row != 0 && tmp.containsKey(up)) {
//
//                            Tile upTile = tmp.get(up);
//                            if (checkDirectionMatch(currentTile, upTile, directionsMatch)) {
//                                System.out.println("match direction true");
//                                Tile newCurrentTile = new Tile(currentTile.c(), currentTile.directions(), upTile, null);
//                                Tile newUp = new Tile(upTile.c(), upTile.directions(), upTile.previous(), newCurrentTile);
//                                tmp.remove(up);
//                                tmp.put(up, newUp);
//                                tmp.put(new Cord(row, i), currentTile);
//                                System.out.println(currentTile);
//                                System.out.println(newUp);
//                            }
//                        }
//                        break;
//                    }
                    case 'J': {
                        Coordinates left = new Coordinates(row, i - 1);
                        Coordinates up = new Coordinates(row - 1, i);
                        if (i > 0 && tmp.containsKey(left)) {
                            Tile leftTile = tmp.get(left);
                            if (checkDirectionMatch(currentTile, leftTile, directionsMatch) || tmp.get(left).c()=='S') {
                                System.out.println("match direction true");
                                Tile newCurrentTile = new Tile(currentTile.c(), currentTile.directions(), leftTile, null);
                                Tile newLeft = new Tile(leftTile.c(), leftTile.directions(), leftTile.previous(), newCurrentTile);
                                tmp.remove(left);
                                tmp.put(left, newLeft);
                                tmp.put(new Coordinates(row, i), currentTile);
                                System.out.println(leftTile);
                                System.out.println(newCurrentTile);
                            }
                        }
                        if (row != 0 && tmp.containsKey(up)) {

                            Tile upTile = tmp.get(up);
                            if (checkDirectionMatch(currentTile, upTile, directionsMatch) || tmp.get(up).c()=='S') {
                                System.out.println("match direction true");
                                Tile newCurrentTile = new Tile(currentTile.c(), currentTile.directions(), currentTile.previous(), upTile);
                                Tile newUp = new Tile(upTile.c(), upTile.directions(), upTile.previous(), newCurrentTile);
                                tmp.remove(up);
                                tmp.put(up, newUp);
                                tmp.put(new Coordinates(row, i), currentTile);
                                System.out.println(newUp);
                                System.out.println(newCurrentTile);
                            }
                        }
                        break;
                    }
                    case 'F': {
                        tmp.put(new Coordinates(row, i), new Tile(current, currentDirections, null, null));
                        System.out.println(tmp.get(new Coordinates(row, i)));
                        break;
                    }
                    case 'S': {
                        start = new Coordinates(row, i);
                        tmp.put(start, new Tile(current, currentDirections, null, null));
                        System.out.println(tmp.get(start));
                        break;
                    }
                    case '.': {
                        // TODO delete all tiles connected to
                    }
                    default:
                        //tmp.put(new Cord(row, i), new Tile(current, currentDirections, null, null));

                }

            }
            row++;
        }
        for (Coordinates coordinates : tmp.keySet()) {
            System.out.println(coordinates + " " + tmp.get(coordinates).c());
        }
        System.out.println("Start cord: " + start);
        Tile tile = tmp.get(new Coordinates(1,1));
        System.out.println(tile.c());
        while(tile.next.c() !='S'){
            Tile tmpTile = tile.next();
            System.out.println(tmpTile.c());
            tile=tmpTile;
        }

    }


    record Coordinates(int x, int y) {
    }

    // private record Directions(){}
    private record Tile(char c, List<Direction> directions, Tile previous, Tile next) {
    }



    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    private static ArrayList<Direction> resolveDirections(char c) {
        List tmp = switch (c) {
            case '|' -> List.of(Direction.NORTH, Direction.SOUTH);
            case '-' -> List.of(Direction.EAST, Direction.WEST);
            case 'L' -> List.of(Direction.NORTH, Direction.EAST);
            case 'J' -> List.of(Direction.NORTH, Direction.WEST);
            case 'F' -> List.of(Direction.SOUTH, Direction.EAST);
            case '7' -> List.of(Direction.SOUTH, Direction.WEST);
            default -> List.of();
        };
        return new ArrayList<Direction>(tmp);
    }

    private static boolean checkDirectionMatch(Tile n1, Tile n2, Map<Direction, Direction> directionsMatch) {
        return n1.directions.stream()
                .anyMatch(directionN1 -> n2.directions().stream()
                        .anyMatch(directionN2 -> directionsMatch.get(directionN2).equals(directionN1)));
    }
}
