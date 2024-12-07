package day3;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Solution {
    private String readInput(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream is = getClass().getResourceAsStream(filePath);
             Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            return null;
        }
    }

    private int part1(String inputString) {
        String regex = "(mul\\(\\d+,\\d+\\))";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(inputString)
                .results()
                .map(mr -> mr.group(1))
                .map(this::multiply)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private int multiply(String s) {
        int firstNum = Integer.parseInt(s.substring(s.indexOf("(") + 1, s.indexOf(",")));
        int secondNum = Integer.parseInt(s.substring(s.indexOf(",") + 1, s.indexOf(")")));
        return firstNum * secondNum;
    }

    private int part2(String inputString) {
        String remainingInput = inputString;
        boolean keepLooping = true;
        int result = 0;
        while (keepLooping) {
            int indexOfInvalid = remainingInput.indexOf("don't()");
            if (indexOfInvalid == -1) {
                // No more don't(), the entire remaining input is valid.
                // Calculate value and stop looping.
                result += part1(remainingInput);
                keepLooping = false;
                continue;
            }
            String valid = remainingInput.substring(0, indexOfInvalid);
            result += part1(valid);
            remainingInput = remainingInput.substring(indexOfInvalid + 7);
            int indexOfValid = remainingInput.indexOf("do()");
            if (indexOfValid == -1) {
                // No more do(), the entire remaining input is invalid.
                // Stop looping.
                keepLooping = false;
                continue;
            }
            remainingInput = remainingInput.substring(remainingInput.indexOf("do()") + 4);
        }
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String inputString = solution.readInput("input.txt");
        int sumOfMultiplications = solution.part1(inputString);
        System.out.println(sumOfMultiplications);
        int sumOfMultiplications2 = solution.part2(inputString);
        System.out.println(sumOfMultiplications2);
    }
}
