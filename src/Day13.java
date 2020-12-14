import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 {

    private int calculate(int earliest, List<String> input) {
        int time = earliest;
        while (true) {
            for (String s : input) {
                if (s.equals("x")) {
                    continue;
                }

                int id = Integer.valueOf(s);

                if ((time % id) == 0) {
                    System.out.println("The ID is: " + id);
                    int timeDifference = time - earliest;
                    return timeDifference * id;
                }
            }
            time++;
        }
    }

    private long calculate2(List<String> input) {
        long t = 0;
        long increment = 1;
        Map<Integer, Integer> map = new HashMap<>();

        for (int n = 0; n < input.size(); n++) {
            if (input.get(n).equals("x")) {
                continue;
            }
            map.put(n, Integer.valueOf(input.get(n)));
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            while ((t + entry.getKey()) % entry.getValue() != 0) {
                t += increment;
            }
            increment *= entry.getValue();
        }
        return t;
    }


    public static void main(String[] args) {
        String input = "41,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,659,x,x,x,x,x,x,x,23,x,x,x,x,13,x,x,x,x,x,19,x,x,x,x,x,x,x,x,x,29,x,937,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,17";
//        String input = "7,13,x,x,59,x,31,19";
        List<String> inputList =
                Stream.of(input.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());

        int earliest = 1000104;
//        int earliest = 939;

        Day13 day13 = new Day13();
        int result1 = day13.calculate(earliest, inputList);
        System.out.println("Answer part 1: " + result1);
        long result2 = day13.calculate2(inputList);

        System.out.println("Answer part 2: " + result2);
    }
}