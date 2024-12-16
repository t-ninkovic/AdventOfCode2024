package day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class Solution {

    private char[][] readInput(String filePath) {
        try (InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filePath)));
             BufferedReader reader = new BufferedReader(isr)) {
            List<String> lines = reader.lines().toList();
            int rows = lines.size();
            int columns = lines.size(); // assuming that every line has the same number of elements
            char[][] grid = new char[rows][columns];
            for (int i = 0; i < rows; i++) {
                char[] chars = lines.get(i).toCharArray();
                System.arraycopy(chars, 0, grid[i], 0, columns);
            }
            return grid;
        } catch (IOException e) {
            return null;
        }
    }

    private int part1(char[][] grid) {
        int counter = 0;
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[i].length; j++) {
                if (grid[i][j] == 'X') {
                    counter += countXmas(i, j, grid);
                }
            }
        }
        return counter;
    }

    private int countXmas(int row, int col, char[][] grid) {
        return fetchMas(row, col, -1, -1, grid) + // move left & up
                fetchMas(row, col, -1, 0, grid) + // move left
                fetchMas(row, col, -1, 1, grid) + // move left & down
                fetchMas(row, col, 0, -1, grid) + // move up
                fetchMas(row, col, 0, 1, grid) + // move down
                fetchMas(row, col, 1, -1, grid) + // move right & up
                fetchMas(row, col, 1, 0, grid) + // move right
                fetchMas(row, col, 1, 1, grid); // move right & down
    }

    private int fetchMas(int startRow, int startCol, int rowMove, int colMove, char[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int moves=0; moves<4; moves++) {
            int row = startRow + moves * rowMove;
            int col = startCol + moves * colMove;
            if (row < 0 || row >= grid.length
                || col < 0 || col >= grid.length) {
                break;
            }
            char c = grid[row][col];
            sb.append(c);
        }
        return sb.toString().equals("XMAS") ? 1 : 0;
    }

    private int part2(char[][] grid) {
        int counter = 0;
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[i].length; j++) {
                if (grid[i][j] == 'A') {
                    counter += countMasDiag(i, j, grid);
                }
            }
        }
        return counter;
    }

    private boolean findMS(int row1, int col1, int row2, int col2, char[][] grid) {
        StringBuilder sb = new StringBuilder();
        if (row1 < 0 || row1 >= grid.length
                || row2 < 0 || row2 >= grid.length
                || col1 < 0 || col1 >= grid.length
                || col2 < 0 || col2 >= grid.length) {
            return false;
        }
        sb.append(grid[row1][col1]);
        sb.append(grid[row2][col2]);
        return (sb.toString().equals("MS") || sb.toString().equals("SM"));
    }

    private int countMasDiag(int row, int col, char[][] grid) {
        return (findMS(row-1, col-1, row+1, col+1, grid) &&
                findMS(row-1, col+1, row+1, col-1, grid))
                ? 1 : 0;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        char[][] grid = solution.readInput("input.txt");
        assert grid != null;
        System.out.println(solution.part1(grid));
        System.out.println(solution.part2(grid));
    }
}
