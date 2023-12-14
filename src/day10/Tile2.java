package day10;

public class Tile2 {
    private char c;
    private Day10.Coordinates coordinates;


    private boolean visited;

    public Tile2(char c, Day10.Coordinates coordinates, boolean visited) {
        this.c = c;
        this.coordinates = coordinates;
        this.visited = visited;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public Day10.Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Day10.Coordinates coordinates) {
        this.coordinates = coordinates;
    }


    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return "Tile2{" +
                "c=" + c +
                ", coordinates=" + coordinates +
                ", visited=" + visited +
                '}';
    }
}