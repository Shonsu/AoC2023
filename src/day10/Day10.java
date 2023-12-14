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
        BufferedReader br = new BufferedReader(new FileReader("src/day10/input.txt"));
        Coordinates start = null;
        String line;
        int lineLength = 0;
        int row = 0;
        while ((line = br.readLine()) != null) {
            lineLength = line.length();
            char[] charArray = line.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char current = charArray[i];
                Coordinates currentCoordinates = new Coordinates(i, row);
                Tile2 currentTile = new Tile2(current, currentCoordinates, false);
                tmp.put(currentCoordinates, currentTile);
                if (current == 'S') {
                    start = currentCoordinates;
                }
            }
            row++;
        }

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
                        false)
                );
                nextDirections.add(Direction.SOUTH);
            }
        }
        String points = "F7LJ-|";
        System.out.println(startTiles);
        List<Coordinates> shoelaceList = new LinkedList<>();
        char current = '0';
        Direction nextDirection = nextDirections.getFirst();
        System.out.println(nextDirection);
        Coordinates nextCoordinate = startTileCoordinates;
        int count = 0;
        while (current != 'S') {
            count++;
            nextCoordinate = switch (nextDirection) {
                case EAST -> new Coordinates(nextCoordinate.x() + 1, nextCoordinate.y());
                case WEST -> new Coordinates(nextCoordinate.x() - 1, nextCoordinate.y());
                case NORTH -> new Coordinates(nextCoordinate.x(), nextCoordinate.y() - 1);
                case SOUTH -> new Coordinates(nextCoordinate.x(), nextCoordinate.y() + 1);
            };
            Tile2 currentTile = tmp.get(nextCoordinate);
            if (points.contains(String.valueOf(currentTile.getC()))) {
                shoelaceList.add(currentTile.getCoordinates());
            }
            currentTile.setVisited(true);

            current = currentTile.getC();
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
        }
        System.out.println("count: " + count / 2);

        shoelaceList.addLast(startTileCoordinates);
        List<Coordinates> reversed = shoelaceList.reversed();
        int sum = 0;
        for (int i = 0; i < reversed.size() - 1; i++) {
            sum += (((reversed.get(i).x() + 1) * (reversed.get(i + 1).y() + 1)) - ((reversed.get(i + 1).x() + 1) * (reversed.get(i).y() + 1)));
        }
        sum += ((reversed.getLast().x()+1) * (reversed.getFirst().y()+1)) - ((reversed.getLast().y()+1) * (reversed.getFirst().x()+1)) ;

        System.out.println(Math.abs(sum/2));
        System.out.println(Math.abs(sum/2) - (reversed.size()/2)+1);
    }


    record Coordinates(int x, int y) {
    }


    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
}
