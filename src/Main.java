import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String inputCFG = "src\\input_cfg.txt";
        String outputCFG = "src\\output_cfg.txt";
        String inputPDA = "src\\input_pda.txt";
        String outputPDA = "src\\output_pda.txt";

        try {
            List<String> results = processInput(inputCFG);
            writeOutput(outputCFG, results);
//            List<String> results2 = processInput(inputPDA);
//            writeOutput(outputPDA, results2);
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }
    private static void writeOutput(String filePath, List<String> results) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String result : results) {
                writer.write(result);
                writer.newLine();
            }
        }
    }

    private static List<String> processInput(String filePath) throws IOException {
        List<String> results = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int problemNumber = 0;
            List<String> inputs = new ArrayList<>();
            boolean readInputs = false; // A flag to indicate if we are within a valid input block.

            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trim any leading or trailing whitespaces.

                // Check if the line is a problem number
                if (line.matches("\\d+")) {
                    if (!inputs.isEmpty()) {
                        System.out.println("hhhhhhhhhhh"+inputs);

                        // If we have collected inputs, simulate and clear before starting new problem
                        results.addAll(simulateAutomaton1(problemNumber, inputs));
                        System.out.println("hhhhhhhhhhh"+results);
                        inputs.clear();
                    }
                    problemNumber = Integer.parseInt(line);
                    readInputs = true; // Start reading inputs for this problem
                } else if (line.equalsIgnoreCase("end")) {
                    // If 'end' is encountered, process the inputs and reset for next problem
                    if (!inputs.isEmpty() && readInputs) {
                        System.out.println("hhhhhhhhhhh"+inputs);
                        results.addAll(simulateAutomaton1(problemNumber, inputs));
                        System.out.println("hhhhhhhhhhh"+results);
                        inputs.clear();
                    }
                    readInputs = false; // Stop reading inputs until next problem number is found
                } else if (!line.isEmpty() && readInputs) {
                    // Collect inputs if we are within a valid input block
                    inputs.add(line);
                }
            }
        }
        return results;
    }
    private static List<String> simulateAutomaton1(int problemNumber, List<String> inputs) {
        switch (problemNumber) {
            case 1: return simulateCFG1(inputs);
//            case 2: return simulateDFA2(inputs);
//            case 3: return simulateDFA3(inputs);
//            case 4: return simulateDFA4(inputs);
            default: return List.of("Unsupported problem number: " + problemNumber);
        }
    }
//    private static List<String> simulateAutomaton2(int problemNumber, List<String> inputs) {
//        switch (problemNumber) {
//            case 1: return simulateDFA1(inputs);
//            case 2: return simulateDFA2(inputs);
//            case 3: return simulateDFA3(inputs);
//            case 4: return simulateDFA4(inputs);
//            default: return List.of("Unsupported problem number: " + problemNumber);
//        }
//    }

    private static List<String> simulateCFG1(List<String> inputs) {
        // Problem 1: DFA that accepts strings not containing "ba"
        Map<String, List<String>> rules = new HashMap<>();
        rules.put("S", Arrays.asList("ASB","BSA", ""));
        rules.put("A", Arrays.asList("aB"));
        rules.put("B", Arrays.asList("bA"));


        CFG cfg = new CFG(rules);
        List<String> outputs = new ArrayList<>();
        outputs.add("1");
        for (String test : inputs) {
            outputs.add(cfg.belongsToCFG(test)? "True" : "False");
        }

        outputs.add("end");

        return outputs;
    }



    public static class CFG {
        private Map<String, List<String>> rules = new HashMap<>();

        public CFG( Map<String, List<String>> r) {
            this.rules=r;

        }

        private boolean belongsToCFG(String s) {
            return checkDerivation("S", s);
        }

        private boolean checkDerivation(String nonTerminal, String s) {
            if (!this.rules.containsKey(nonTerminal)) {
                return false;
            }

            for (String production : rules.get(nonTerminal)) {
                if (matchProduction(production, s)) {
                    return true;
                }
            }
            return false;
        }

        private boolean matchProduction(String production, String s) {
            if (production.isEmpty()) {
                return s.isEmpty();
            }

            List<String> parts = splitProduction(production);
            return matchParts(parts, s, 0, new StringBuilder());
        }

        private boolean matchParts(List<String> parts, String s, int index, StringBuilder accumulated) {
            if (index == parts.size()) {
                return accumulated.toString().equals(s);
            }

            String part = parts.get(index);
            if (rules.containsKey(part)) {
                for (String prod : rules.get(part)) {
                    StringBuilder newAccumulated = new StringBuilder(accumulated);
                    newAccumulated.append(prod);
                    if (matchParts(parts, s, index + 1, newAccumulated)) {
                        return true;
                    }
                }
            } else {
                accumulated.append(part);
                return matchParts(parts, s, index + 1, accumulated);
            }

            return false;
        }

        private List<String> splitProduction(String production) {
            List<String> parts = new ArrayList<>();
            for (char c : production.toCharArray()) {
                parts.add(String.valueOf(c));
            }
            return parts;
        }
    }

}