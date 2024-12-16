package day5;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Solution {

    record Input(List<String> conditions, List<List<Integer>> updates) {}

    private Input readInput(String filePath) {
        List<String> conditions = new ArrayList<>();
        List<String> updates = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream(filePath);
             Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
            String line = null;
            while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty()) {
                conditions.add(line);
            }
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                updates.add(line);
            }
            return new Input(conditions, convertUpdates(updates));
        } catch (IOException e) {
            return null;
        }
    }

    private List<List<Integer>> convertUpdates(List<String> updates) {
        return updates.stream().map(line -> {
            String[] numbers = line.split(",");
            return Arrays.stream(numbers).map(Integer::valueOf).toList();
        }).toList();
    }

    private boolean isUpdateCorrectlyOrdered(List<String> conditions, List<Integer> update) {
        for (int i=0; i<update.size(); i++) {
            for (int j=0; j<i; j++) {
                if (conditions.contains(update.get(i) + "|" + update.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    private int part1(Input input) {
        return input.updates()
                .stream()
                .filter(update -> isUpdateCorrectlyOrdered(input.conditions(), update))
                .map(update -> update.get(update.size() / 2))
                .reduce(Integer::sum)
                .orElse(0);
    }

    private List<Integer> reorderUpdate(List<String> conditions, List<Integer> update) {
        ArrayList<Integer> mutableList = new ArrayList<>(update);
        for (int i=0; i<mutableList.size(); i++) {
            for (int j=0; j<i; j++) {
                if (conditions.contains(mutableList.get(i) + "|" + mutableList.get(j))) {
                   Collections.swap(mutableList, i, j);
                }
            }
        }
        return mutableList;
    }

    private int part2(Input input) {
        return input.updates()
                .stream()
                .filter(update -> !isUpdateCorrectlyOrdered(input.conditions(), update))
                .map(update -> reorderUpdate(input.conditions(), update))
                .map(update -> update.get(update.size() / 2))
                .reduce(Integer::sum)
                .orElse(0);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        Input input = solution.readInput("input.txt");
        assert input != null;
        System.out.println(solution.part1(input));
        System.out.println(solution.part2(input));
    }
}
