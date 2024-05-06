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
                } else if ( readInputs) {
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
            case 2: return simulateCFG2(inputs);
            case 3: return simulateCFG3(inputs);
            case 4: return simulateCFG4(inputs);
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
        // Problem 1: Write a CFG for accepting an equal number of a's and b's.
        Set<String> terminals = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("S"));
        Map<String, List<String>> rules = new HashMap<>();

        rules.put("S", Arrays.asList("aSbS", "bSaS", ""));
        CFG cfg = new CFG("S", terminals, nonTerminals, rules);
        List<String> outputs = new ArrayList<>();
        outputs.add("1");
        for (String test : inputs) {
            outputs.add(cfg.accept(test)? "accepted" : "not accepted");
        }

        outputs.add("end");

        return outputs;
    }
    private static List<String> simulateCFG2(List<String> inputs) {
        // Problem 1:Write a CFG for accepting a number of a's is twice the number of b's.
        Set<String> terminals = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("S"));
        Map<String, List<String>> rules = new HashMap<>();

        rules.put("S", Arrays.asList("aSabS","aSbaS","","bSaaS"));

        CFG cfg = new CFG("S", terminals, nonTerminals, rules);
        List<String> outputs = new ArrayList<>();
        outputs.add("2");
        for (String test : inputs) {
            outputs.add(cfg.accept(test)? "accepted" : "not accepted");
        }

        outputs.add("end");

        return outputs;
    }
    private static List<String> simulateCFG3(List<String> inputs) {
        // Problem 1:Write a CFG for accepting a number of a's is twice the number of b's.
        Set<String> terminals = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("S"));
        Map<String, List<String>> rules = new HashMap<>();

        rules.put("S", Arrays.asList("aSa","bSb","","a","b"));

        CFG cfg = new CFG("S", terminals, nonTerminals, rules);
        List<String> outputs = new ArrayList<>();
        outputs.add("3");
        for (String test : inputs) {
            outputs.add(cfg.accept(test)? "accepted" : "not accepted");
        }

        outputs.add("end");

        return outputs;
    }

    private static List<String> simulateCFG4(List<String> inputs) {
        // Problem 1:Write a CFG for accepting a number of a's is twice the number of b's.
        Set<String> terminals = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("S","T"));
        Map<String, List<String>> rules = new HashMap<>();


        rules.put("S", Arrays.asList("aaaT","Taaa","aTaa","aaTa"));
        rules.put("T", Arrays.asList("aaTb","aabT","abaT",""));


        CFG cfg = new CFG("S", terminals, nonTerminals, rules);
        List<String> outputs = new ArrayList<>();
        outputs.add("4");
        for (String test : inputs) {
            outputs.add(cfg.accept(test)? "accepted" : "not accepted");
        }
        //3 0
        //5 1
        //7 2
        //9 3
        outputs.add("end");

        return outputs;
    }



    public static class CFG {
        private String startSymbol;
        private Set<String> terminals;
        private Set<String> nonTerminals;
        private Map<String, List<String>> productionRules;

        public CFG(String startSymbol, Set<String> terminals, Set<String> nonTerminals, Map<String, List<String>> productionRules) {
            this.startSymbol = startSymbol;
            this.terminals = terminals;
            this.nonTerminals = nonTerminals;
            this.productionRules = productionRules;
        }

        public boolean accept(String input) {
            return check(input, startSymbol);
        }

        private boolean check(String input, String currentSymbol) {
            if (nonTerminals.contains(currentSymbol)) {
                List<String> productions = productionRules.get(currentSymbol);
                for (String production : productions) {
                    if (production.equals(" ") && input.isEmpty()) {
                        return true; // Handle empty string production
                    }
                    if (simulate(input, production)) {
                        return true;
                    }
                }
            } else {
                return input.equals(currentSymbol);
            }
            return false;
        }

        private boolean simulate(String input, String production) {
            if (production.isEmpty()) {
                return input.isEmpty();
            }

            // Recursive descent through the production
            if (production.length() == 1) {
                String symbol = String.valueOf(production.charAt(0));
                return check(input, symbol);
            } else {
                for (int split = 0; split <= input.length(); split++) {
//                    System.out.println("split "+split);

                    String leftPart = split == 0 ? "" : input.substring(0, split);
                    String rightPart = split == input.length() ? "" : input.substring(split);

                    String firstSymbol = String.valueOf(production.charAt(0));
                    String restProduction = production.substring(1);
//                    System.out.println("leftPart "+leftPart);
//                    System.out.println("rightPart "+rightPart);
//                    System.out.println("firstSymbol "+firstSymbol);
//                    System.out.println("restProduction "+restProduction);

                    if (check(leftPart, firstSymbol) && simulate(rightPart, restProduction)) {
                        return true;
                    }
                }
            }
            return false;
        }


//        public static void main(String[] args) {
//            Map<Character, List<String>> rules = new HashMap<>();
//            rules.put('S', Arrays.asList("SS", "(S)", ""));
//
//            CFG cfg = new CFG('S', rules);
//            String testInput = "((()))";
//            System.out.println("Does the CFG generate the string '" + testInput + "'? " + cfg.generates(testInput));
//        }
    }

}