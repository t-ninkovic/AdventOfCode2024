package day2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {

    private List<List<Integer>> readInput(String filePath) {
        List<List<Integer>> allReports = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream(filePath);
             Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] numbers = line.split("\\s+");
                List<Integer> report = Arrays.stream(numbers).map(Integer::valueOf).toList();
                allReports.add(report);
            }
        } catch (IOException e) {
            return null;
        }
        return allReports;
    }

    public long part1(List<List<Integer>> reports) {
        return reports.stream().filter(this::isSafe).count();
    }

    boolean isSafe(List<Integer> report) {
        if (report.size() == 1) return true;

        int firstDiff = report.get(0) - report.get(1);
        if (firstDiff == 0) return false;
        int sign = firstDiff/Math.abs(firstDiff); // sign = is the list decreasing or increasing

        for (int i=0; i<report.size()-1; i++) {
            int diff = report.get(i)-report.get(i+1);
            int absDiff = Math.abs(diff);
            if (absDiff < 1 || absDiff > 3) return false;
            if (diff/absDiff != sign) return false;
        }
        return true;
    }

    public long part2(List<List<Integer>> reports) {
        return reports.stream().filter(this::isSafeMinusOneLevel).count();
    }

    // Determines if a report is "safe" after removing one level by keeping track of how many "strikes" a report has.
    // Each "strike" is one unsafe level. Therefore, a list is safe if it has 1 or 0 strikes.
    boolean isSafeMinusOneLevel(List<Integer> report) {
        int strikes = 0;
        if (report.size() == 1) return true;

        // Initial difference and sign (increasing/decreasing) between the first two levels
        int firstDiff = report.get(0) - report.get(1);
        int absFirstDiff = Math.abs(firstDiff);
        if (absFirstDiff < 1 || absFirstDiff > 3) strikes++;
        int sign = absFirstDiff == 0 ? 0 : firstDiff/Math.abs(firstDiff);

        for (int i=1; i<report.size()-1; i++) {
            int diff = report.get(i)-report.get(i+1);
            int absDiff = Math.abs(diff);
            if (absDiff < 1 || absDiff > 3) {
                strikes++;
                continue;
            }
            // If the first two levels were equal (initial sign = 0), we update it here
            if (sign == 0) {
                sign = diff/absDiff;
            } else if (diff/absDiff != sign) {
                strikes++;
                sign = diff/absDiff;
            }
        }
        return strikes < 2;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        List<List<Integer>> reports = solution.readInput("input.txt");
        assert reports != null;
        long safeReports = solution.part1(reports);
        long safeReportsMinusLevel = solution.part2(reports);
        System.out.println(safeReports);
        System.out.println(safeReportsMinusLevel);
    }
}
