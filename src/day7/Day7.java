package day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Day7 {

    //private final static String ORDER = "AKQJT98765432";
    private final static String ORDER = "AKQT98765432J"; // with Joker

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("src/day7/input.txt"));

        TreeSet<Hand> hands = new TreeSet<>();
        Map<Character, Integer> hand = new HashMap<>();


        String line;
        while ((line = br.readLine()) != null) {
            hand.clear();

            String[] split = line.trim().split("\\s+");

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
            }
            hands.add(new Hand(split[0], Long.parseLong(split[1].trim()), kind));
        }

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
            if (i != 0) {
                return i;
            } else {
                int pos1 = 0;
                int pos2 = 0;
                for (int j = 0; j < card.length() && pos1 == pos2; j++) {
                    pos1 = ORDER.indexOf(this.card.charAt(j));
                    pos2 = ORDER.indexOf(o.card.charAt(j));
                }
                return pos2 - pos1;
            }
        }
    }

    enum Kind {
        five, four, full, three, two, one, high, none;
    }
}
