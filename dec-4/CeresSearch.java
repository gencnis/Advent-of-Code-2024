import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CeresSearch {

    public static void main(String[] args) {
        String fileName = "grid.txt";

        // Read the grid
        char[][] grid = readGrid(fileName);

        if (grid == null) {
            System.out.println("Grid could not be loaded. Exiting.");
            return;
        }

        System.out.println("Grid dimensions: " + grid.length + " x " + grid[0].length);

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
        // Check the main pattern
        boolean pattern1 = grid[startRow][startCol] == 'M' &&
                           grid[startRow][startCol + 2] == 'S' &&
                           grid[startRow + 1][startCol + 1] == 'A' &&
                           grid[startRow + 2][startCol] == 'M' &&
                           grid[startRow + 2][startCol + 2] == 'S';

        // Check the flipped pattern
        boolean pattern2 = grid[startRow][startCol] == 'S' &&
                           grid[startRow][startCol + 2] == 'M' &&
                           grid[startRow + 1][startCol + 1] == 'A' &&
                           grid[startRow + 2][startCol] == 'S' &&
                           grid[startRow + 2][startCol + 2] == 'M';

        // Check diagonal top-left to bottom-right
        boolean diagonal1 = grid[startRow][startCol] == 'M' &&
                            grid[startRow + 2][startCol + 2] == 'S' &&
                            grid[startRow + 1][startCol + 1] == 'A' &&
                            grid[startRow + 2][startCol] == 'M' &&
                            grid[startRow][startCol + 2] == 'S';

        // Check diagonal bottom-left to top-right
        boolean diagonal2 = grid[startRow][startCol] == 'S' &&
                            grid[startRow + 2][startCol + 2] == 'M' &&
                            grid[startRow + 1][startCol + 1] == 'A' &&
                            grid[startRow + 2][startCol] == 'S' &&
                            grid[startRow][startCol + 2] == 'M';

        return pattern1 || pattern2 || diagonal1 || diagonal2;
    }
}
