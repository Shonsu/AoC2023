package day14;

import java.util.Objects;

public record Rock(int x, int y, char type) implements Comparable<Rock> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rock rock = (Rock) o;
        return x == rock.x && y == rock.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Rock o) {
        if (this.y() != o.y()) {
            return Integer.compare(this.y(), o.y());
        } else {
            return Integer.compare(this.x(), o.x());
        }
    }

}
