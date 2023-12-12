package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/day1/input.txt"));

        String line;
        int sum = 0;
        int count = 0;
        while ((line = br.readLine()) != null) {
            count++;
            char[] textLine = line.trim().toCharArray();
            int i = 0;
            int j = textLine.length - 1;
            int firstDigit = 0;
            int lastDigit = 0;
            StringBuilder sbFirst = new StringBuilder();
            StringBuilder sbLast = new StringBuilder();
            while (!(firstDigit > 0 && lastDigit > 0)) {
                if (isDigit(textLine[i]) && firstDigit == 0) {
                    firstDigit = textLine[i] - 48;
                } else if(firstDigit==0) {
                    sbFirst.append(textLine[i]);
                    for (Number number : Number.values()) {
                        if (sbFirst.indexOf(number.toString()) != -1) {
                            firstDigit = number.ordinal() + 1;
                        }
                    }
                    i++;
                }

                if (isDigit(textLine[j]) && lastDigit == 0) {
                    System.out.println("digit");
                    lastDigit = textLine[j] - 48;
                    System.out.println(lastDigit);
                } else if(lastDigit==0) {
                    sbLast.insert(0, textLine[j]);
                    for (Number number : Number.values()) {
                        if (sbLast.indexOf(number.toString()) != -1) {
                            lastDigit = number.ordinal() + 1;
                        }
                    }
                    j--;
                }

            }
            System.out.println(line + " " + (firstDigit * 10 + lastDigit));
            sum += firstDigit * 10 + lastDigit;
        }
        System.out.println(count);
        System.out.println(sum);
    }

    static boolean isDigit(char ch) {
        return ch >= 48 && ch <= 57;
    }

    enum Number {
        one, two, three, four, five, six, seven, eight, nine,
    }
}
