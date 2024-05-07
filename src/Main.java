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
            List<String> results2 = processInput2(inputPDA);
            writeOutput(outputPDA, results2);
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
            boolean readInputs = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.matches("\\d+")) {
                    if (!inputs.isEmpty()) {
                        System.out.println("hhhhhhhhhhh"+inputs);

                        results.addAll(simulateAutomaton2(problemNumber, inputs));
                        System.out.println("hhhhhhhhhhh"+results);
                        inputs.clear();
                    }
                    problemNumber = Integer.parseInt(line);
                    readInputs = true;
                } else if (line.equalsIgnoreCase("end")) {
                    if (!inputs.isEmpty() && readInputs) {
                        System.out.println("hhhhhhhhhhh"+inputs);
                        results.addAll(simulateAutomaton1(problemNumber, inputs));
                        System.out.println("hhhhhhhhhhh"+results);
                        inputs.clear();
                    }
                    readInputs = false;
                } else if ( readInputs) {
                    inputs.add(line);
                }
            }
        }
        return results;
    }
    private static List<String> processInput2(String filePath) throws IOException {
        List<String> results = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int problemNumber = 0;
            List<String> inputs = new ArrayList<>();
            boolean readInputs = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.matches("\\d+")) {
                    if (!inputs.isEmpty()) {
                        System.out.println("hhhhhhhhhhh"+inputs);

                        results.addAll(simulateAutomaton2(problemNumber, inputs));
                        System.out.println("hhhhhhhhhhh"+results);
                        inputs.clear();
                    }
                    problemNumber = Integer.parseInt(line);
                    readInputs = true;
                } else if (line.equalsIgnoreCase("end")) {
                    if (!inputs.isEmpty() && readInputs) {
                        System.out.println("hhhhhhhhhhh"+inputs);
                        results.addAll(simulateAutomaton2(problemNumber, inputs));
                        System.out.println("hhhhhhhhhhh"+results);
                        inputs.clear();
                    }
                    readInputs = false;
                } else if ( readInputs) {
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
    private static List<String> simulateAutomaton2(int problemNumber, List<String> inputs) {
        switch (problemNumber) {
            case 1: return simulatePDA1(inputs);
            case 2: return simulatePDA2(inputs);
            case 3: return simulatePDA3(inputs);
            case 4: return simulatePDA4(inputs);
            default: return List.of("Unsupported problem number: " + problemNumber);
        }
    }

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
        // Problem 2:Write a CFG for accepting a number of a's is twice the number of b's.
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
        // Problem 3: Write a CFG for accepting a palindrome Σ = {a,b}.
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
        // Problem 4: Write a CFG for accepting a language {𝑎2𝑛+3𝑏𝑛 | n>=0}
        Set<String> terminals = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("S","T"));
        Map<String, List<String>> rules = new HashMap<>();

        rules.put("S", Arrays.asList("aaaT",""));
        rules.put("T", Arrays.asList("aaTb",""));

        CFG cfg = new CFG("S", terminals, nonTerminals, rules);
        List<String> outputs = new ArrayList<>();
        outputs.add("4");
        for (String test : inputs) {
            outputs.add(cfg.accept(test)? "accepted" : "not accepted");
        }

        outputs.add("end");

        return outputs;
    }
    private static List<String> simulatePDA1(List<String> inputs) {
        // Problem 1: DFA that accepts strings not containing "ba"
        String[] stateNames = {"q1","q2","q3"};
        Object[][] transitions = {
                // {fromState, readCharacter, popStack, pushStack, toState}
                {"q1", 'a', 'ε', 'a', "q1"},
                {"q1", 'ε', '$', 'ε', "q3"},
                {"q1", 'b', 'a', 'ε', "q2"},
                {"q2", 'b', 'a', 'ε', "q2"},
                {"q2", '$', '$', 'ε', "q3"}
        };
        String[] startStateNames = {"q1"};
        String[] acceptingStateNames = {"q3"};


        PDA pda = new PDA(stateNames, transitions, startStateNames, acceptingStateNames);

        List<String> outputs = new ArrayList<>();
        outputs.add("1");
        for (String input : inputs) {
            input+='$';
            boolean containsBa = pda.accepts(input);
            outputs.add(containsBa ? "accepted" : "not accepted");
        }
        outputs.add("end");

        return outputs;
    }
    private static List<String> simulatePDA2(List<String> inputs) {
        // Problem 1: DFA that accepts strings not containing "ba"
        String[] stateNames = {"q1","q2","q3","q4","q5","q6","q7"};
        Object[][] transitions = {
                // {fromState, readCharacter, popStack, pushStack, toState}
                {"q1", 'a', 'ε', 'a', "q2"},
                {"q1", 'ε', '$', 'ε', "q7"},
                {"q2", 'a', 'ε', 'a', "q3"},
                {"q3", 'b', 'ε', 'ε', "q4"},
                {"q3", 'a', 'ε', 'a', "q2"},
                {"q4", 'b', 'a', 'ε', "q5"},
                {"q5", 'b', 'a', 'ε', "q6"},
                {"q6", 'b', 'ε', 'ε', "q4"},
                {"q6", '$', '$', 'ε', "q7"}
        };
        String[] startStateNames = {"q1"};
        String[] acceptingStateNames = {"q7"};


        PDA pda = new PDA(stateNames, transitions, startStateNames, acceptingStateNames);

        List<String> outputs = new ArrayList<>();
        outputs.add("2");
        for (String input : inputs) {
            input+='$';
            boolean containsBa = pda.accepts(input);
            outputs.add(containsBa ? "accepted" : "not accepted");
        }
        outputs.add("end");

        return outputs;
    }

    private static List<String> simulatePDA3(List<String> inputs) {
        // Problem 1: DFA that accepts strings not containing "ba"
        String[] stateNames = {"q1","q2","q3"};
        Object[][] transitions = {
                // {fromState, readCharacter, popStack, pushStack, toState}
                {"q1", '{', 'ε', '{', "q1"},
                {"q1", '}', '{', 'ε', "q2"},
                {"q2", '{', 'ε', '{', "q1"},
                {"q2", '}', '{', 'ε', "q2"},
                {"q2", '$', '$', 'ε', "q3"}
        };
        String[] startStateNames = {"q1"};
        String[] acceptingStateNames = {"q3"};


        PDA pda = new PDA(stateNames, transitions, startStateNames, acceptingStateNames);

        List<String> outputs = new ArrayList<>();
        outputs.add("3");
        for (String input : inputs) {
            input+='$';
            boolean containsBa = pda.accepts(input);
            outputs.add(containsBa ? "accepted" : "not accepted");
        }
        outputs.add("end");

        return outputs;
    }

    private static List<String> simulatePDA4(List<String> inputs) {
        // Problem 1: DFA that accepts strings not containing "ba"
        String[] stateNames = {"q1","q2","q3","q4"};
        Object[][] transitions = {
                // {fromState, readCharacter, popStack, pushStack, toState}
                {"q1", 'a', 'ε', 'a', "q1"},
                {"q1", 'b', 'a', 'ε', "q2"},
                {"q2", 'b', 'a', 'ε', "q2"},
                {"q2", 'c', 'a', 'ε', "q3"},
                {"q3", 'c', 'a', 'ε', "q3"},
                {"q3", '$', '$', 'ε', "q4"}
        };
        String[] startStateNames = {"q1"};
        String[] acceptingStateNames = {"q4"};


        PDA pda = new PDA(stateNames, transitions, startStateNames, acceptingStateNames);

        List<String> outputs = new ArrayList<>();
        outputs.add("4");
        for (String input : inputs) {
            input+='$';
            boolean containsBa = pda.accepts(input);
            outputs.add(containsBa ? "accepted" : "not accepted");
        }
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

    }
    public static class PDA {
        private static class State {
            private final String name;

            public State(String name) {
                this.name = name;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                State state = (State) obj;
                return Objects.equals(name, state.name);
            }

            @Override
            public int hashCode() {
                return Objects.hash(name);
            }

            @Override
            public String toString() {
                return name;
            }
        }

        private State currentState;
        private Set<State> startStates;
        private Set<State> acceptingStates;
        private final Map<State, Map<Character, List<Transition>>> transitions;
        private Stack<Character> stack;

        private static class Transition {
            Character toPop;
            Character toPush;
            State nextState;

            public Transition(State nextState, Character toPop, Character toPush) {
                this.nextState = nextState;
                this.toPop = toPop;
                this.toPush = toPush;
            }
        }

        public PDA(String[] stateNames, Object[][] transitionRules, String[] startStateNames, String[] acceptingStateNames) {
            Map<String, State> stateMap = new HashMap<>();
            for (String name : stateNames) {
                stateMap.put(name, new State(name));
            }

            startStates = new HashSet<>();
            for (String name : startStateNames) {
                startStates.add(stateMap.get(name));
            }

            acceptingStates = new HashSet<>();
            for (String name : acceptingStateNames) {
                acceptingStates.add(stateMap.get(name));
            }

            transitions = new HashMap<>();
            for (Object[] rule : transitionRules) {
                String fromStateName = (String) rule[0];
                Character readChar = (Character) rule[1];
                Character popChar = (Character) rule[2];
                Character pushChar = (Character) rule[3];
                String toStateName = (String) rule[4];

                State fromState = stateMap.get(fromStateName);
                State toState = stateMap.get(toStateName);
                Transition transition = new Transition(toState, popChar, pushChar);

                transitions.computeIfAbsent(fromState, k -> new HashMap<>())
                        .computeIfAbsent(readChar, k -> new ArrayList<>())
                        .add(transition);
            }
        }

        public boolean accepts(String input) {
//            System.out.println("input "+input);

            stack = new Stack<>();
            stack.push('$');
            Set<State> currentStates = new HashSet<>(startStates);

            for (char c : input.toCharArray()) {
                Set<State> newStates = new HashSet<>();

                for (State state : currentStates) {
//                    System.out.println("state "+state.toString());

                    List<Transition> possibleTransitions = transitions.getOrDefault(state, new HashMap<>()).get(c);
//                    for (Transition trans : possibleTransitions) {
//                        System.out.println("sstate " + state.toString());
//
//                        System.out.println("trans.toPop " + trans.toPop);
//                        System.out.println("trans.toPush " + trans.toPush);
//                        System.out.println("trans.nextState " + trans.nextState);
//                    }

                    if (possibleTransitions != null) {
                        for (Transition trans : possibleTransitions) {
//                            System.out.println("state "+state.toString());
//
//                            System.out.println("trans.toPop "+trans.toPop);
//                            System.out.println("trans.toPush "+trans.toPush);
//                            System.out.println("trans.nextState "+trans.nextState);
                            if (stack.peek().equals(trans.toPop) ||trans.toPop.equals('ε')) {
//                                System.out.println("ttttttrans.toPop " + trans.toPop);
//                                System.out.println("stack.peek() " + stack.peek());

                                if (!trans.toPop.equals('ε')) {
                                    stack.pop();
                                }
                                if (!trans.toPush.equals('ε')) {
                                    stack.push(trans.toPush);
                                }
                                newStates.add(trans.nextState);
                            }

                        }
                    }

                }

//                System.out.println("newStates "+newStates.toString());

                currentStates = newStates;
            }

            for (State state : currentStates) {
               // System.out.println("stack.peek() " + stack.peek());

                if (acceptingStates.contains(state) && stack.empty()) {

                    return true;
                }
            }

            return false;
        }
    }

}