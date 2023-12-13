package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day10 {


    public static void main(String[] args) throws IOException {

        Map<Coordinates, Tile2> tmp = new HashMap<>();
//        Map<Direction, Direction> directionsMatch = new HashMap<>();
//        directionsMatch.put(Direction.EAST, Direction.WEST);
//        directionsMatch.put(Direction.WEST, Direction.EAST);
//        directionsMatch.put(Direction.NORTH, Direction.SOUTH);
//        directionsMatch.put(Direction.SOUTH, Direction.NORTH);
        BufferedReader br = new BufferedReader(new FileReader("src/day10/input.txt"));
        Coordinates start = null;
        String line;
        int lineLength = 0;
        int row = 0;
        while ((line = br.readLine()) != null) {
            lineLength = line.length();
            char[] charArray = line.toCharArray();
//            System.out.println("---------- new row " + row + " ----------");
            for (int i = 0; i < charArray.length; i++) {
                char current = charArray[i];
//                System.out.println("Current char: " + current);
                Coordinates currentCoordinates = new Coordinates(i, row);
                Tile2 currentTile = new Tile2(current, currentCoordinates, null, null, false);
                switch (current) {
                    case '|', 'L': {
                        Coordinates up = new Coordinates(i, row - 1);
                        if (row != 0 && tmp.containsKey(up)) {
                            tmp.put(currentCoordinates, currentTile);
//                            System.out.println("Add: " + tmp.get(currentCoordinates));
                        }
                        break;
                    }
                    case '-', '7': {
                        Coordinates left = new Coordinates(i - 1, row);
                        if (i > 0 && tmp.containsKey(left)) {
                            tmp.put(currentCoordinates, currentTile);
//                            System.out.println("Add: " + tmp.get(currentCoordinates));
                        }
                        break;
                    }
//                    case 'L':{
//                        Cord up = new Cord(row - 1, i);
//                        if (row != 0 && tmp.containsKey(up)) {
//
//                            Tile upTile = tmp.get(up);
//                            if (checkDirectionMatch(currentTile, upTile, directionsMatch)) {
////                                System.out.println("match direction true");
//                                Tile newCurrentTile = new Tile(currentTile.c(), currentTile.directions(), upTile, null);
//                                Tile newUp = new Tile(upTile.c(), upTile.directions(), upTile.previous(), newCurrentTile);
//                                tmp.remove(up);
//                                tmp.put(up, newUp);
//                                tmp.put(new Cord(row, i), currentTile);
////                                System.out.println(currentTile);
////                                System.out.println(newUp);
//                            }
//                        }
//                        break;
//                    }
                    case 'J': {
                        Coordinates left = new Coordinates(i - 1, row);
                        Coordinates up = new Coordinates(i, row - 1);
                        if (i != 0 && tmp.containsKey(up)) {
                            tmp.put(currentCoordinates, currentTile);
//                            System.out.println("Add: " + tmp.get(currentCoordinates));
                        }
                        if (i > 0 && tmp.containsKey(left)) {
                            tmp.put(currentCoordinates, currentTile);
//                            System.out.println("Add: " + tmp.get(currentCoordinates));
                        }
                        break;
                    }
                    case 'F': {
                        tmp.put(currentCoordinates, currentTile);
//                        System.out.println("Add: " + tmp.get(currentCoordinates));
                        break;
                    }
                    case 'S': {
                        start = new Coordinates(i, row);
                        tmp.put(currentCoordinates, currentTile);
//                        System.out.println(tmp.get(start));
                        break;
                    }
                    case '.': {
                        // TODO delete all tiles connected to
                    }
                    default:

                }

            }
            row++;
        }
//        for (Coordinates coordinates : tmp.keySet()) {
//            System.out.println(coordinates + " " + tmp.get(coordinates).getC());
//        }
        System.out.println(tmp.size());
        Tile2 startTile = tmp.get(start);
        System.out.println("start tile: " + startTile);
        List<Tile2> startTiles = new LinkedList<>();
        // Looking for tiles connected to startTile

        Coordinates startTileCoordinates = startTile.getCoordinates();
        Coordinates tmpCoords = null;
        List<Direction> nextDirections = new LinkedList<>();

        // if (startTileCoordinates.x() > 0 && startTileCoordinates.x() < lineLength) {
        tmpCoords = new Coordinates(startTileCoordinates.x() - 1, startTileCoordinates.y());
        if (tmp.containsKey(tmpCoords)) {
            Tile2 leftTile = tmp.get(tmpCoords);
            char c = leftTile.getC();
            if (c == 'F' || c == 'L' || c == '-') {
                startTiles.add(new Tile2(c,
                        tmpCoords,
                        startTile,
                        null,
                        false)
                );
                nextDirections.add(Direction.WEST);
            }
        }
        tmpCoords = new Coordinates(startTileCoordinates.x() + 1, startTileCoordinates.y());
        if (tmp.containsKey(tmpCoords)) {
            Tile2 rightTile = tmp.get(tmpCoords);
            char c = rightTile.getC();
            if (c == 'J' || c == '7' || c == '-') {
                startTiles.add(new Tile2(c,
                        tmpCoords,
                        startTile,
                        null,
                        false)
                );
                nextDirections.add(Direction.EAST);
            }
        }

        // }

        //if(startTileCoordinates.y() > 0 && startTileCoordinates.y() < row){
        tmpCoords = new Coordinates(startTileCoordinates.x(), startTileCoordinates.y() - 1);
        if (tmp.containsKey(tmpCoords)) {
            Tile2 upTile = tmp.get(tmpCoords);
            char c = upTile.getC();
            if (c == 'F' || c == '7' || c == '|') {
                startTiles.add(new Tile2(c,
                        tmpCoords,
                        startTile,
                        null,
                        false)
                );
                nextDirections.add(Direction.NORTH);

            }
        }

        tmpCoords = new Coordinates(startTileCoordinates.x(), startTileCoordinates.y() + 1);
        if (tmp.containsKey(tmpCoords)) {
            Tile2 bottomTile = tmp.get(tmpCoords);
            char c = bottomTile.getC();
            if (c == 'L' || c == 'J' || c == '|') {
                startTiles.add(new Tile2(c,
                        tmpCoords,
                        startTile,
                        null,
                        false)
                );
                nextDirections.add(Direction.SOUTH);
            }
        }
        //}
        System.out.println(startTiles);
        char current = '0';
        Direction nextDirection = nextDirections.getFirst();
        System.out.println(nextDirection);
        Coordinates nextCoordinate =startTileCoordinates;
        int count = 0;
        while (current != 'S') {
            count++;
            nextCoordinate = switch (nextDirection) {
                case EAST -> new Coordinates(nextCoordinate.x() +1, nextCoordinate.y());
                case WEST -> new Coordinates(nextCoordinate.x() - 1, nextCoordinate.y());
                case NORTH -> new Coordinates(nextCoordinate.x(), nextCoordinate.y() - 1);
                case SOUTH -> new Coordinates(nextCoordinate.x(), nextCoordinate.y() + 1);
            };
//            System.out.println(nextCoordinate);
            current = tmp.get(nextCoordinate).getC();
            nextDirection = switch (current) {
                case '|' -> nextDirection == Direction.SOUTH || nextDirection.equals(Direction.NORTH) ? nextDirection : null;
                case '-' -> nextDirection.equals(Direction.EAST) || nextDirection.equals(Direction.WEST) ? nextDirection : null;
                case 'L' ->
                        nextDirection.equals(Direction.SOUTH) ? Direction.EAST : (nextDirection.equals(Direction.WEST) ? Direction.NORTH : null);
                case 'J' ->
                        nextDirection.equals(Direction.SOUTH) ? Direction.WEST : (nextDirection.equals(Direction.EAST) ? Direction.NORTH : null);
                case 'F' ->
                        nextDirection.equals(Direction.NORTH) ? Direction.EAST : (nextDirection.equals(Direction.WEST) ? Direction.SOUTH : null);
                case '7' ->
                        nextDirection.equals(Direction.NORTH) ? Direction.WEST : (nextDirection.equals(Direction.EAST) ? Direction.SOUTH : null);
                default -> null;

            };
//            System.out.println("current: " + current + " " + nextDirection);
        }
            System.out.println("count: " + count/2);

    }


    record Coordinates(int x, int y) {
    }


    enum Direction {
        NORTH, SOUTH, EAST, WEST

    }
//    record Directions(Direction in, Direction out) {

//    }

//    private static Directions resolveDirections(char c) {
//        return switch (c) {
//            case '|' -> new Directions(Direction.NORTH, Direction.SOUTH);
//            case '-' -> new Directions(Direction.WEST, Direction.EAST);
//            case 'L' -> new Directions(Direction.NORTH, Direction.EAST);
//            case 'J' -> new Directions(Direction.WEST, Direction.NORTH);
//            case 'F' -> new Directions(Direction.SOUTH, Direction.EAST);
//            case '7' -> new Directions(Direction.WEST, Direction.SOUTH);
//            default -> null;
//        };
//    }

}
