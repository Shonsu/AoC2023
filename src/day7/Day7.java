package day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Day7 {

    private final static String ORDER = "AKQJT98765432";

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("src/day7/inputexample.txt"));

        TreeSet<Hand> hands = new TreeSet<>();
        Map<Character, Integer> hand = new HashMap<>();


        String line;
        while ((line = br.readLine()) != null) {
            hand.clear();

            String[] split = line.trim().split("\\s+");
            System.out.println(Arrays.toString(split));
            char[] forSorting = split[0].toCharArray();
            for (char c : forSorting) {
                hand.put(c, hand.containsKey(c) ? hand.get(c) + 1 : 1);
            }
            Kind kind = Kind.none;
            for (Character c : hand.keySet()) {
                kind = switch (hand.get(c)) {
                    case 2 -> (kind.equals(Kind.three)) ? Kind.full : (kind.equals(Kind.one)) ? Kind.two : Kind.one;
                    case 3 -> (kind.equals(Kind.one)) ? Kind.full : Kind.three;
                    case 4 -> Kind.four;
                    case 5 -> Kind.five;
                    default -> (!kind.equals(Kind.none)) ? kind : Kind.high;
                };
                System.out.print(c + " count " + hand.get(c));
                System.out.println();
            }
            System.out.println(kind);
            hands.add(new Hand(split[0], Long.parseLong(split[1].trim()), kind));
        }
        System.out.println(hands);

        int rank = 1;
        long sum = 0;
        for (Hand h : hands) {
            System.out.println(h + " rank " + rank);
            sum += h.bid() * rank;
            rank++;
        }

        System.out.println("Total winnings: " + sum);

    }

    record Hand(String card, long bid, Kind kind) implements Comparable<Hand> {
        @Override
        public int compareTo(Hand o) {
            int i = o.kind.compareTo(this.kind);
            //System.out.println(this.card + " other " + o.card);
            if (i != 0) {
                return i;
            } else {
                int pos1 = 0;
                int pos2 = 0;
                for (int j = 0; j < card.length() && pos1 == pos2; j++) {
                    pos1 = ORDER.indexOf(this.card.charAt(j));
                    pos2 = ORDER.indexOf(o.card.charAt(j));
                }
                //System.out.printf(pos1 + " " + pos2);
                return pos2-pos1;
                //return this.card.compareTo(o.card);
            }
        }
    }

    enum Kind {
        high(7), five(6), four(5), full(4), three(3), two(2), one(1), none(0);

        private final int value;

        Kind(int value) {
            this.value = value;
        }

        private int value() {
            return value;
        }
    }
}
