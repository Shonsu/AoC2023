package day14;

import java.util.*;
import java.util.stream.IntStream;

public class Platform {
    private List<Rock> rocks;
    private int rows;
    private int cols;


    public void parse(List<String> lines) {
        rows = lines.size();
        cols = lines.getFirst().length();
        rocks = IntStream.range(0, cols)
                .mapToObj(x -> IntStream.range(0, rows)
                        .mapToObj(y -> new Rock(x, y, lines.get(y).charAt(x)))
                        .filter(rock -> rock.type() == 'O' || rock.type() == '#').toList())
                .flatMap(List::stream).sorted().toList();
//        lines.stream().map(s -> IntStream.range(0,s.length()).map(i->))
    }


    public void tiltNorth() {
        Map<Integer, Integer> colMax = new HashMap<>();
        List<Rock> result = new ArrayList<>();
        for (Rock rock : rocks) {
            int max = -1;
            if (colMax.containsKey(rock.x())) {
                max = colMax.get(rock.x());
            }
//            else {
//               max = result.stream().filter(r -> r.x() == rock.x()).mapToInt(Rock::y).max().orElse(-1);
//                System.out.println(max);
//            }

            if (rock.type() == 'O') {
                result.add(new Rock(rock.x(), max + 1, rock.type()));
                colMax.put(rock.x(), max + 1);
            } else {
                result.add(rock);
                colMax.put(rock.x(), rock.y());
            }

        }
        //Collections.sort(result);
        rocks = result;
    }

    public void tiltWest() {
        Collections.sort(rocks);
        Map<Integer, Integer> rowMax = new HashMap<>();
        List<Rock> result = new ArrayList<>();
        for (Rock rock : rocks) {
            int max = -1;
            if (rowMax.containsKey(rock.y())) {
                max = rowMax.get(rock.y());
            }
            if (rock.type() == 'O') {
                result.add(new Rock(max + 1, rock.y(), rock.type()));
                rowMax.put(rock.y(), max + 1);
            } else {
                result.add(rock);
                rowMax.put(rock.y(), rock.x());
            }
        }
        rocks = result;
    }

    public void tiltSouth() {
        List<Rock> reversed = rocks.reversed();
        Map<Integer, Integer> colMax = new HashMap<>();
        List<Rock> result = new ArrayList<>();
        for (Rock rock : reversed) {
            int max = rows;
            if (colMax.containsKey(rock.x())) {
                max = colMax.get(rock.x());
            }
//            else {
//               max = result.stream().filter(r -> r.x() == rock.x()).mapToInt(Rock::y).max().orElse(-1);
//                System.out.println(max);
//            }

            if (rock.type() == 'O') {
                result.add(new Rock(rock.x(), max - 1, rock.type()));
                colMax.put(rock.x(), max - 1);
            } else {
                result.add(rock);
                colMax.put(rock.x(), rock.y());
            }

        }
        //  Collections.sort(result);
        // System.out.println(result);
        rocks = result;

    }

    public void tiltEast() {
        //  System.out.println(rocks);
        Comparator<Rock> rockYThenX = Comparator.comparing(Rock::y)
                .thenComparing(Comparator.comparing(Rock::x).reversed());
        // .thenComparing((o1, o2) -> o2.x() - o1.x());
        //Collections.sort(rocks, rockYThenX);
        List<Rock> sorted = rocks.stream().sorted(rockYThenX).toList();
        // System.out.println(sorted);

        Map<Integer, Integer> rowMax = new HashMap<>();
        List<Rock> result = new ArrayList<>();
        for (Rock rock : sorted) {
            int max = cols;
            if (rowMax.containsKey(rock.y())) {
                max = rowMax.get(rock.y());
            }

            if (rock.type() == 'O') {
                result.add(new Rock(max - 1, rock.y(), rock.type()));
                rowMax.put(rock.y(), max - 1);
            } else {
                result.add(rock);
                rowMax.put(rock.y(), rock.x());
            }

        }
        rocks = result;
        // System.out.println(rocks);
    }

    void cycleNTimes(int n) {
        for (int i = 0; i < n; i++) {
            tiltNorth();
            tiltWest();
            tiltSouth();
            tiltEast();
        }
    }

    int calculateLoad() {
        return rocks.stream().filter(r -> r.type() == 'O').mapToInt(r -> (rows - r.y())).sum();
    }

    @Override
    public String toString() {
        return "Platform{" +
                "rocks=" + rocks +
                '}';
    }

    public void dump() {
        char[][] dump = new char[rows][cols];
        for (char[] chars : dump) {
            Arrays.fill(chars, '.');
        }
        for (Rock rock : rocks) {
            dump[rock.y()][rock.x()] = rock.type();
        }

        for (char[] chars : dump) {
            for (int j = 0; j < dump[0].length; j++) {
                System.out.print(chars[j]);
            }
            System.out.println();
        }

    }
}
