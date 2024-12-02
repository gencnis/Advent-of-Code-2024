import java.io.*;
import java.util.*;

public class SolvePuzzle {

    // Method to read the file and store the data
    public static List<int[]> readFile(String fileName) {
        List<int[]> pairs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    int left = Integer.parseInt(parts[0]);
                    int right = Integer.parseInt(parts[1]);
                    pairs.add(new int[]{left, right});
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return pairs;
    }

    public static void main(String[] args) {
        // File name
        String fileName = "list.txt";

        // Read the file and store the data
        List<int[]> pairs = readFile(fileName);

        // Separate left and right numbers into two lists
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();
        for (int[] pair : pairs) {
            leftList.add(pair[0]);
            rightList.add(pair[1]);
        }

        // Sort both lists
        Collections.sort(leftList);
        Collections.sort(rightList);

        // Calculate total distance
        int totalDistance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            int distance = Math.abs(leftList.get(i) - rightList.get(i));
            totalDistance += distance;
        }

        // Print the total distance
        System.out.println("Total Distance: " + totalDistance);

        // Create a frequency map for the right list
        Map<Integer, Integer> rightFrequencyMap = new HashMap<>();
        for (int num : rightList) {
            rightFrequencyMap.put(num, rightFrequencyMap.getOrDefault(num, 0) + 1);
        }

        // Calculate similarity score
        int similarityScore = 0;
        for (int num : leftList) {
            int frequency = rightFrequencyMap.getOrDefault(num, 0);
            similarityScore += num * frequency;
        }

        // Print the similarity score
        System.out.println("Similarity Score: " + similarityScore);
    }
}
