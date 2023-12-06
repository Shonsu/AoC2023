package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day3 {

    public static void main(String[] args) throws IOException {

        char[][] tab = new char[140][140];
        List<Number> numbersRecords = new ArrayList<>();
        Map<Point, Character> symbols = new HashMap<>();
        Map<Point, Number> numbersMap = new HashMap<>();
        int sum = 0;

        BufferedReader br = new BufferedReader(new FileReader("src/day3/input.txt"));
        int row = 0;
        int col = 0;
        String line;
        while ((line = br.readLine()) != null) {
            col = 0;
            for (char ch : line.toCharArray()) {
                tab[row][col] = ch;
                if (ch == '*') {
                    symbols.put(new Point(row, col), ch);
                }
                col++;
            }
            row++;
        }

        char prev = '=';
        int number = 0;
        int numberStart = 0;
        int numberStop = 0;

        int pos = 0;
        for (int i = 0; i < 140; i++) {
            int j = 0;
            for (; j < 140; j++) {
                char ch = tab[i][j];
                if (isDigit(ch) && prev != '.') {
                    if (number == 0) {
                        numberStart = j;
                    }
                    number = number * 10 + Character.getNumericValue(ch);
                    prev = ch;
                } else if (number > 0) {
                    numberStop = j - 1;
                    Number num = new Number(number, i, numberStart, numberStop);
                    for (int k = numberStart; k <= numberStop; k++) {
                        numbersMap.put(new Point(i, k), num);
                    }
                    numbersRecords.add(num);
                    number = 0;
                }
            }
            if (number > 0) {
                numberStop = j - 1;
                Number num = new Number(number, i, numberStart, numberStop);
                for (int k = numberStart; k <= numberStop; k++) {
                    numbersMap.put(new Point(i, k), num);
                }
                numbersRecords.add(num);
            }
            number = 0;
        }
        boolean adjacent = false;
        for (Number num : numbersRecords) {
            int firstRow = num.row() == 0 ? 0 : 1;
            int lastRow = num.row() == 139 ? 0 : 1;
            int firstChar = num.start() == 0 ? 0 : 1;
            int lastChar = num.end() == 139 ? 0 : 1;
            for (int i = num.row() - firstRow; !adjacent && i <= num.row() + lastRow; i++) {
                for (int j = num.start() - firstChar; !adjacent && j <= num.end() + lastChar; j++) {
                    if (isSymbol(tab[i][j])) {
                        adjacent = true;
                        sum += num.number();
                    }
                }
            }
            adjacent = false;
        }
        System.out.println(sum);

        // PART 2
        Map<Point, List<Number>> numberAround = new HashMap<>();
        Integer sumOfMultiply = 0;
        for (Point p : symbols.keySet()) {
            int firstRow = p.x() == 0 ? 0 : 1;
            int lastRow = p.x() == 139 ? 0 : 1;
            int firstChar = p.y() == 0 ? 0 : 1;
            int lastChar = p.y() == 139 ? 0 : 1;
            Number nTmp = null;
            List<Number> numbers = new ArrayList<>();
            for (int i = p.x() - firstRow; i <= p.x() + lastRow; i++) {
                for (int j = p.y() - firstChar; j <= p.y() + lastChar; j++) {
                    Point tmp = new Point(i, j);
                    if (numbersMap.containsKey(tmp)) {
                        Number number1 = numbersMap.get(tmp);
                        if (!number1.equals(nTmp)) {
                            numbers.add(number1);
                        }
                        nTmp = number1;
                    }
                }
            }
            if (numbers.size() == 2) {
                int multiply = numbers.stream().mapToInt(number1 -> number1.number).reduce(1, (a, b) -> a * b);
                sumOfMultiply +=multiply;
            }
        }
        System.out.println(sumOfMultiply);
    }

    record Number(int number, int row, int start, int end) {
    }

    record Point(int x, int y) {
    }

    static boolean isDigit(char ch) {
        return ch >= 48 && ch <= 57;
    }

    static boolean isSymbol(char ch) {
        return (ch != '.' && !isDigit(ch));
    }

}