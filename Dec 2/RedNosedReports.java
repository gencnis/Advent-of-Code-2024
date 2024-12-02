import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedNosedReports {

    // Method to read the file and parse reports into a list of integer arrays
    public static List<int[]> readReports(String fileName) {
        List<int[]> reports = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespace
                if (!line.isEmpty()) { // Skip empty lines
                    // Split the line into individual numbers and convert to integers
                    String[] parts = line.split("\\s+");
                    int[] levels = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
                    reports.add(levels); // Add the array of levels to the list of reports
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return reports; // Return parsed reports
    }

    // Method to check if a report is safe
    public static boolean isSafeReport(int[] report) {
        // Flag to track if the report is increasing vs decreasing
        boolean increasing = true;
        boolean decreasing = true;

        // Check adjacent differences
        for (int i = 1; i < report.length; i++) {
            int diff = report[i] - report[i - 1];

            // Check if the difference is within the range [1, 3]
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }

            // Update flags
            if (diff < 0) {
                increasing = false;
            }
            if (diff > 0) {
                decreasing = false;
            }
        }

        // A report must be either all increasing or all decreasing
        return increasing || decreasing;
    }

    // Method to check if a report is safe with the Problem Dampener
    public static boolean isSafeWithDampener(int[] report) {
        if (isSafeReport(report)) {
            return true;
        }

        // Try removing each level and check if the report becomes safe
        for (int i = 0; i < report.length; i++) {
            int[] modifiedReport = new int[report.length - 1];
            int index = 0;

            // Copy all levels except the one being removed
            for (int j = 0; j < report.length; j++) {
                if (j != i) { // Skip the level being removed
                    modifiedReport[index++] = report[j];
                }
            }

            // Check if the modified report is safe
            if (isSafeReport(modifiedReport)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        // File name
        String fileName = "reports.txt";

        List<int[]> reports = readReports(fileName);

        // First solution
        /*
        int safeCountWithoutDampener = 0;
        for (int[] report : reports) {
            if (isSafeReport(report)) {
                safeCountWithoutDampener++;
            }
        }
        System.out.println("Number of safe reports (without Problem Dampener): " + safeCountWithoutDampener);
        */

        // Second solution
        // Check each report for safety with the Problem Dampener
        int safeCount = 0;
        for (int[] report : reports) {
            if (isSafeWithDampener(report)) {
                safeCount++;
            }
        }

        System.out.println("Number of safe reports (with Problem Dampener): " + safeCount);
    }
}
