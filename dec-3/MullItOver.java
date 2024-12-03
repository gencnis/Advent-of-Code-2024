import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOver {

    public static String readFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line); // Append each line
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return content.toString();
    }

    // Handle enable/disable logic
    public static int processMemory(String memory) {
        // Regex for instructions
        String regex = "(mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(memory);

        // Sum of enabled multiplications
        int sum = 0;

        // Tracking if `mul` instructions are enabled
        boolean isEnabled = true;

        // Process all matched instructions sequentially
        while (matcher.find()) {
            String instruction = matcher.group(); 

            if (instruction.startsWith("mul")) {
                // Process a valid mul instruction if enabled
                if (isEnabled) {
                    int x = Integer.parseInt(matcher.group(2)); // First number
                    int y = Integer.parseInt(matcher.group(3)); // Second number
                    sum += x * y; // Multiply and add to the sum
                }
            } else if (instruction.equals("do()")) {
                // Enable future mul instructions
                isEnabled = true;
            } else if (instruction.equals("don't()")) {
                // Disable future mul instructions
                isEnabled = false;
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        // File name
        String fileName = "memory.txt";

        String memory = readFile(fileName);

        /*
        // Part One:
        int sumOfProductsPartOne = calculateSumOfProducts(memory);
        System.out.println("Sum of products: " + sumOfProductsPartOne);
        */

        // Part Two: Handle enable/disable logic for mul(X,Y)
        int sumOfProductsPartTwo = processMemory(memory);
        System.out.println("Sum of enabled products: " + sumOfProductsPartTwo);
    }
}
