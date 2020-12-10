import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 {


    private Result calculate(List<Integer> input) {
        int diff1 = 0;
        int diff3 = 0;

        for (int i = 0; i < input.size() - 1; i++) {
            int diff = input.get(i + 1) - input.get(i);

            if (diff == 1) {
                diff1++;
            }
            if (diff == 3) {
                diff3++;
            }
        }

        return new Result(diff1, diff3);
    }

    private Long calculate2(List<Integer> input) {
        Map<Integer, Adapter> adapterMap = new HashMap<>();
        for (Integer a : input) {
            adapterMap.put(a, new Adapter(a));
        }

        for (Adapter a : adapterMap.values()) {
            a.findPossibleConnections(adapterMap);
        }
        return adapterMap.get(0).findPermutations();
    }

    private class Adapter {
        int value;
        List<Adapter> possibleConnections = new ArrayList<>();
        Long permutations;

        Adapter(int value) {
            this.value = value;
        }

        private void findPossibleConnections(Map<Integer, Adapter> adapters) {
            for (int i = 1; i < 4; i++) {
                if (adapters.containsKey(value + i)) {
                    possibleConnections.add(adapters.get(value + i));
                }
            }
        }

        private Long findPermutations() {
            if (permutations != null) {
                return permutations;
            }

            for (Adapter connection : possibleConnections) {
                if (permutations == null) {
                    permutations = 0L;
                }
                permutations += connection.findPermutations();
            }

            if (possibleConnections.isEmpty()) {
                permutations = 1L;
            }

            return permutations;
        }
    }

    private class Result {
        int diff1;
        int diff3;

        Result(int diff1, int diff3) {
            this.diff1 = diff1;
            this.diff3 = diff3;
        }

        int getDiff1() {
            return diff1;
        }

        int getDiff3() {
            return diff3;
        }
    }


    public static void main(String[] args) {
        String input = "149,87,67,45,76,29,107,88,4,11,118,160,20,115,130,91,144,152,33,94,53,148,138,47,104,121,112,116,99,105,34,14,44,137,52,2,65,141,140,86,84,81,124,62,15,68,147,27,106,28,69,163,97,111,162,17,159,122,156,127,46,35,128,123,48,38,129,161,3,24,60,58,155,22,55,75,16,8,78,134,30,61,72,54,41,1,59,101,10,85,139,9,98,21,108,117,131,66,23,77,7,100,51";
//        String input = "28,33,18,42,31,14,46,20,48,47,24,23,49,45,19,38,39,11,1,32,25,35,8,17,7,9,4,2,34,10,3";
//        String input = "16,10,15,5,1,11,7,19,6,12,4";
        List<Integer> inputList =
                Stream.of(input.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
        Collections.sort(inputList);
        inputList.add(0, 0); // prepend the outlet's joltage rating to the list
        inputList.add(inputList.get(inputList.size() - 1) + 3); //
        Day10 day10 = new Day10();
        Result result1 = day10.calculate(inputList);
        System.out.println("Answer part 1: " + (result1.getDiff1() * result1.getDiff3()));
        System.out.println("Answer part 2: " + day10.calculate2(inputList));
    }
}