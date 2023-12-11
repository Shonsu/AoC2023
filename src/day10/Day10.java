package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 {


    public static void main(String[] args) throws IOException {

        Map<Coordinates, Tile2> tmp = new HashMap<>();
        Map<Direction, Direction> directionsMatch = new HashMap<>();
        directionsMatch.put(Direction.EAST, Direction.WEST);
        directionsMatch.put(Direction.WEST, Direction.EAST);
        directionsMatch.put(Direction.NORTH, Direction.SOUTH);
        directionsMatch.put(Direction.SOUTH, Direction.NORTH);
        BufferedReader br = new BufferedReader(new FileReader("src/day10/inputexample2.txt"));
        Coordinates start = null;
        String line;
        int row = 0;
        while ((line = br.readLine()) != null) {
            char[] charArray = line.toCharArray();
            System.out.println("---------- new row " + row + " ----------");
            for (int i = 0; i < charArray.length; i++) {
                char current = charArray[i];
                System.out.println("Current char: " + current);
                Directions currentDirections = resolveDirections(current);
                Coordinates currentCoordinates = new Coordinates(row, i);
                Tile2 currentTile = new Tile2(current, currentCoordinates, currentDirections, null, null);
                switch (current) {
                    case '|', 'L': {
                        Coordinates up = new Coordinates(row - 1, i);
                        if (row != 0 && tmp.containsKey(up)) {
                            Tile2 upTile = tmp.get(up);
                            if (checkDirectionMatch(currentTile, upTile, directionsMatch)){
                                tmp.put(currentCoordinates, new Tile2(current, currentCoordinates, currentDirections, null, null));
                                System.out.println("Add: " + tmp.get(currentCoordinates));
                            }
                        }
                        break;
                    }
                    case '-', '7': {
                        Coordinates left = new Coordinates(row, i - 1);
                        if (i > 0 && tmp.containsKey(left)) {
                            Tile2 leftTile = tmp.get(left);
                            if (checkDirectionMatch(currentTile, leftTile, directionsMatch)) {
                                tmp.put(currentCoordinates, new Tile2(current, currentCoordinates, currentDirections, null, null));
                                System.out.println("Add: " + tmp.get(currentCoordinates));
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
                        if (i > 0 && tmp.containsKey(left) && tmp.containsKey(left)) {
                            Tile2 leftTile = tmp.get(left);
                            System.out.println(leftTile);
                            if (checkDirectionMatch(currentTile, leftTile, directionsMatch)) {
                                tmp.put(currentCoordinates, new Tile2(current, currentCoordinates, currentDirections, null, null));
                                System.out.println("Add: " + tmp.get(currentCoordinates));

                            }
                        }
                        if (row != 0 && tmp.containsKey(up) && tmp.containsKey(up)) {
                            Tile2 upTile = tmp.get(up);
                            if (checkDirectionMatch(currentTile, upTile, directionsMatch)) {
                                tmp.put(currentCoordinates, new Tile2(current, currentCoordinates, currentDirections, null, null));
                                System.out.println("Add: " + tmp.get(currentCoordinates));

                            }
                        }
                        break;
                    }
                    case 'F': {
                        tmp.put(currentCoordinates, new Tile2(current, currentCoordinates, currentDirections, null, null));
                        System.out.println(tmp.get(new Coordinates(row, i)));
                        break;
                    }
                    case 'S': {
                        start = new Coordinates(row, i);
                        tmp.put(currentCoordinates, new Tile2(current, currentCoordinates, currentDirections, null, null));
                        System.out.println(tmp.get(start));
                        break;
                    }
                    case '.': {
//                        // TODO delete all tiles connected to
//                        Coordinates left = new Coordinates(row, i - 1);
                        Coordinates up = new Coordinates(row - 1, i);
//                        if (i > 0 && tmp.containsKey(left)) {
//                            Tile2 leftTile = tmp.get(left);
//                            if(leftTile.getDirections().out().equals(Direction.WEST)){
//                                tmp.remove(left);
//
//                            }
//                        }
                        if (row != 0 && tmp.containsKey(up)) {
                            Tile2 upTile = tmp.get(up);
                            if(upTile.getDirections().out().equals(Direction.SOUTH)){
                                tmp.remove(up);

                            }
                        }

                    }
                    default:
                        //tmp.put(new Cord(row, i), new Tile(current, currentDirections, null, null));

                }

            }
            row++;
        }
        for (Coordinates coordinates : tmp.keySet()) {
            System.out.println(coordinates + " " + tmp.get(coordinates).getC());
        }
        System.out.println(tmp.size());
        System.out.println("Start cord: " + start);
//        Tile tile = tmp.get(new Coordinates(1,1));
//        System.out.println(tile.c());
//        while(tile.next.c() !='S'){
//            Tile tmpTile = tile.next();
//            System.out.println(tmpTile.c());
//            tile=tmpTile;
//        }

    }


    record Coordinates(int x, int y) {
    }

    // private record Directions(){}
    private record Tile(char c, List<Direction> directions, Tile previous, Tile next) {
    }


    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    record Directions(Direction in, Direction out) {
    }

    private static Directions resolveDirections(char c) {
        return switch (c) {
            case '|' -> new Directions(Direction.NORTH, Direction.SOUTH);
            case '-' -> new Directions(Direction.WEST, Direction.EAST);
            case 'L' -> new Directions(Direction.NORTH, Direction.EAST);
            case 'J' -> new Directions(Direction.WEST, Direction.NORTH);
            case 'F' -> new Directions(Direction.SOUTH, Direction.EAST);
            case '7' -> new Directions(Direction.WEST, Direction.SOUTH);
            default -> null;
        };
    }

    private static boolean checkDirectionMatch(Tile2 n1, Tile2 n2, Map<Direction, Direction> directionsMatch) {
        Directions directionsN1 = n1.getDirections();
        Directions directionsN2 = n2.getDirections();
        if (n2.getC() == 'S') {
            return true;
        }
        if (directionsMatch.get(directionsN1.in()).equals(directionsN2.out())) {

            return true;
        }
        return directionsMatch.get(directionsN1.out()).equals(directionsN2.in());
    }
}
