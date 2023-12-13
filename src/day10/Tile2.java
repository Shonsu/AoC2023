package day10;

public class Tile2 {
    private char c;
    private Day10.Coordinates coordinates;

    private Tile2 previous;
    private Tile2 next;

    private boolean visited;

    public Tile2(char c, Day10.Coordinates coordinates, Tile2 previous, Tile2 next, boolean visited) {
        this.c = c;
        this.coordinates = coordinates;
        this.previous = previous;
        this.next = next;
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

    public Tile2 getPrevious() {
        return previous;
    }

    public void setPrevious(Tile2 previous) {
        this.previous = previous;
        previous.setNext(this);
    }

    public Tile2 getNext() {
        return next;
    }

    public void setNext(Tile2 next) {
        this.next = next;
        next.setPrevious(this);
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
                ", previous=" + (previous != null ? previous.getC() : "null") +
                ", next=" + (next != null ? next.getC() : "null") +
                '}';
    }
}