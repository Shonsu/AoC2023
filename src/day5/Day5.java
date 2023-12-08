package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Day5 {

    public static final String inputFile = "src/day5/input.txt";

    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader(inputFile));
        String line;
        String[] tmp;
        List<Long> seed = new ArrayList<>();
        List<MapRecord> seedToSoil = new ArrayList<>();
        List<MapRecord> soilToFertilizer = new ArrayList<>();
        List<MapRecord> fertilizerToWater = new ArrayList<>();
        List<MapRecord> waterToLight = new ArrayList<>();
        List<MapRecord> lightToTemperature = new ArrayList<>();
        List<MapRecord> temperatureToHumidity = new ArrayList<>();
        List<MapRecord> humidityToLocation = new ArrayList<>();

        while ((line = bf.readLine()) != null) {
            String[] split = line.split(":");

            List pointer = switch (split[0]) {
                case "seed-to-soil map" -> seedToSoil;
                case "soil-to-fertilizer map" -> soilToFertilizer;
                case "fertilizer-to-water map" -> fertilizerToWater;
                case "water-to-light map" -> waterToLight;
                case "light-to-temperature map" -> lightToTemperature;
                case "temperature-to-humidity map" -> temperatureToHumidity;
                case "humidity-to-location map" -> humidityToLocation;
                default -> null;
            };

            while ((line = bf.readLine()) != null && !line.isEmpty()) {
                tmp = line.split("\\s+");
                pointer.add(new MapRecord(Long.parseLong(tmp[0]), Long.parseLong(tmp[1]), Long.parseLong(tmp[2])));
            }
        }
        bf.close();
        bf = new BufferedReader(new FileReader(inputFile));
        line = bf.readLine();
        String[] split = line.split(":");
        if (split[0].equals("seeds")) {
            seed = Arrays.stream(split[1].strip().split("\\s+")).mapToLong(Long::parseLong).boxed().toList();
        }

        List<Long> location = getLocationFromMaps(seed, seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation);

        Optional<Long> min = location.stream().min(Comparator.comparingLong(Long::longValue));
        System.out.println("Min location:");
        min.ifPresent(System.out::println);

        long start = System.currentTimeMillis();
        Instant startI = Instant.now();
        bf.close();
        bf = new BufferedReader(new FileReader(inputFile));
        line = bf.readLine();
        split = line.split(":");
        List<Long> min2 = new ArrayList<>();
        if (split[0].equals("seeds")) {
            List<Long> tmpSeedRanges = Arrays.stream(split[1].strip().split("\\s+")).mapToLong(Long::parseLong).boxed().toList();
            long tmpMin = Long.MAX_VALUE;
            for (int i = 0; i < tmpSeedRanges.size(); ) {
                for (long j = tmpSeedRanges.get(i); j < tmpSeedRanges.get(i) + tmpSeedRanges.get(i + 1); j++) {
                    List<Long> locationFromMaps = getLocationFromMaps(List.of(j), seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation);

                    if (tmpMin > locationFromMaps.get(0)) {
                        tmpMin = locationFromMaps.get(0);
                    }
                }
                min2.add(tmpMin);
                i += 2;

            }
        }
        System.out.println(min2);
        Optional<Long> result = min2.stream().min(Comparator.comparingLong(Long::longValue));
        long end = System.currentTimeMillis();
        Instant endI = Instant.now();
        System.out.println("Time milis: " + (end - start));
        System.out.println("Time instant: " + (Duration.between(startI, endI).toMillis()));
        System.out.println(result);
    }

    private static List<Long> getLocationFromMaps(List<Long> seed, List<MapRecord> seedToSoil, List<MapRecord> soilToFertilizer, List<MapRecord> fertilizerToWater, List<MapRecord> waterToLight, List<MapRecord> lightToTemperature, List<MapRecord> temperatureToHumidity, List<MapRecord> humidityToLocation) {
        //        System.out.println(seed);
        List<Long> soil = calculateMap(seed, seedToSoil);
//        System.out.println(soil);

        List<Long> fertilizer = calculateMap(soil, soilToFertilizer);
//        System.out.println(fertilizer);

        List<Long> water = calculateMap(fertilizer, fertilizerToWater);
//        System.out.println(water);

        List<Long> light = calculateMap(water, waterToLight);
//        System.out.println(light);

        List<Long> temperature = calculateMap(light, lightToTemperature);
//        System.out.println(water);

        List<Long> humidity = calculateMap(temperature, temperatureToHumidity);
//        System.out.println(humidity);

        //        System.out.println(location);
        return calculateMap(humidity, humidityToLocation);
    }

    private static List<Long> calculateMap(List<Long> seed, List<MapRecord> map) {
        List<Long> result = new ArrayList<>();
        for (Long i : seed) {
            boolean found = false;
            for (MapRecord mapRecord : map) {
                if (mapRecord.source() <= i && i < (mapRecord.source() + mapRecord.length())) {
                    result.add(i - mapRecord.source() + mapRecord.destination());
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(i);
            }
        }
        return result;
    }

    record MapRecord(long destination, long source, long length) {
    }
}
