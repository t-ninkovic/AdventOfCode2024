package day1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {

    record Input(List<Integer> listA, List<Integer> listB) {}

    private Input readInput(String filePath) {
        List<Integer> listA = new ArrayList<>();
        List<Integer> listB = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream(filePath);
             Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] numbers = line.split("\\s+");
                listA.add(Integer.valueOf(numbers[0]));
                listB.add(Integer.valueOf(numbers[1]));
            }
        } catch (IOException e) {
            return null;
        }
        return new Input(listA, listB);
    }

    public int part1(List<Integer> listA, List<Integer> listB) {
        final List<Integer> sortedA = listA.stream().sorted().toList();
        final List<Integer> sortedB = listB.stream().sorted().toList();
        return IntStream.range(0, listA.size())
                .map(i -> Math.abs(sortedA.get(i) - sortedB.get(i)))
                .reduce(Integer::sum)
                .orElse(0);
    }

    public int part2(List<Integer> listA, List<Integer> listB) {
        return listA.stream()
                .map(elA -> listB.stream().filter(elB -> Objects.equals(elB, elA)).count() * elA)
                .mapToInt(num -> Integer.parseInt(String.valueOf(num)))
                .reduce(Integer::sum)
                .orElse(0);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        Input inputLists = solution.readInput("input.txt");
        assert inputLists != null;
        int distance = solution.part1(inputLists.listA, inputLists.listB);
        int similarity = solution.part2(inputLists.listA, inputLists.listB);
        System.out.println(distance);
        System.out.println(similarity);
    }
}
