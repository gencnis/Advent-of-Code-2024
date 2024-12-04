import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class XmasFinder {

    public static void main(String[] args) {
        String fileName = "grid.txt";

        // Read the grid
        char[][] grid = readGrid(fileName);

        // PART 1: Count linear "XMAS" patterns (commented out for Part 2)
        /*
        int countLinearXmas = countLinearXmas(grid, "XMAS");
        System.out.println("Total occurrences of linear XMAS: " + countLinearXmas);
        */

        // PART 2: Count "X-MAS" patterns
        int countXmasPatterns = countXmasPattern(grid);
        System.out.println("Total occurrences of X-MAS patterns: " + countXmasPatterns);
    }

    // Method to read the grid from a file
    public static char[][] readGrid(String fileName) {
        char[][] grid = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int rowCount = 0;

            // Calculate dimensions of the grid
            while ((line = br.readLine()) != null) {
                rowCount++;
            }

            br.close();

            BufferedReader br2 = new BufferedReader(new FileReader(fileName));
            String firstLine = br2.readLine();
            int colCount = firstLine.length();

            grid = new char[rowCount][colCount];
            grid[0] = firstLine.toCharArray();
            int currentRow = 1;

            while ((line = br2.readLine()) != null) {
                grid[currentRow++] = line.toCharArray();
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return grid;
    }

    // PART 1: Count linear XMAS patterns (if needed for reference)
    public static int countLinearXmas(char[][] grid, String pattern) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        int patternLength = pattern.length();

        // Search horizontally and vertically for the pattern
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <= cols - patternLength; j++) {
                if (matchesPattern(grid, i, j, 0, 1, pattern)) {
                    count++;
                }
            }
        }

        for (int j = 0; j < cols; j++) {
            for (int i = 0; i <= rows - patternLength; i++) {
                if (matchesPattern(grid, i, j, 1, 0, pattern)) {
                    count++;
                }
            }
        }

        return count;
    }

    // Helper for PART 1: Check for a linear pattern in a specific direction
    public static boolean matchesPattern(char[][] grid, int row, int col, int dRow, int dCol, String pattern) {
        for (int k = 0; k < pattern.length(); k++) {
            if (grid[row + k * dRow][col + k * dCol] != pattern.charAt(k)) {
                return false;
            }
        }
        return true;
    }

    // PART 2: Count occurrences of "X-MAS" patterns
    public static int countXmasPattern(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        // Loop through all possible 3x3 sub-grids
        for (int i = 0; i <= rows - 3; i++) {
            for (int j = 0; j <= cols - 3; j++) {
                // Check if the 3x3 sub-grid matches an X-MAS pattern
                if (matchesXmasPattern(grid, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    // Helper for PART 2: Check if a 3x3 sub-grid matches the X-MAS pattern
    public static boolean matchesXmasPattern(char[][] grid, int startRow, int startCol) {
        // Original pattern
        boolean pattern1 = grid[startRow][startCol] == 'M' &&
                           grid[startRow][startCol + 2] == 'S' &&
                           grid[startRow + 1][startCol + 1] == 'A' &&
                           grid[startRow + 2][startCol] == 'M' &&
                           grid[startRow + 2][startCol + 2] == 'S';
    
        // Flipped pattern
        boolean pattern2 = grid[startRow][startCol] == 'S' &&
                           grid[startRow][startCol + 2] == 'M' &&
                           grid[startRow + 1][startCol + 1] == 'A' &&
                           grid[startRow + 2][startCol] == 'S' &&
                           grid[startRow + 2][startCol + 2] == 'M';
    
        // 90° rotation
        boolean pattern3 = grid[startRow][startCol + 1] == 'M' &&
                           grid[startRow + 2][startCol + 1] == 'S' &&
                           grid[startRow + 1][startCol + 1] == 'A' &&
                           grid[startRow + 1][startCol] == 'M' &&
                           grid[startRow + 1][startCol + 2] == 'S';
    
        // 180° rotation
        boolean pattern4 = grid[startRow + 2][startCol] == 'S' &&
                           grid[startRow + 2][startCol + 2] == 'M' &&
                           grid[startRow + 1][startCol + 1] == 'A' &&
                           grid[startRow][startCol] == 'S' &&
                           grid[startRow][startCol + 2] == 'M';
    
        // 270° rotation
        boolean pattern5 = grid[startRow][startCol + 1] == 'S' &&
                           grid[startRow + 2][startCol + 1] == 'M' &&
                           grid[startRow + 1][startCol + 1] == 'A' &&
                           grid[startRow + 1][startCol] == 'S' &&
                           grid[startRow + 1][startCol + 2] == 'M';
    
        return pattern1 || pattern2 || pattern3 || pattern4 || pattern5;
    }
    
}
