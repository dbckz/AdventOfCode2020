import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day15 {

    private int calculate(List<Integer> input, int value) {
        Map<Integer, Integer> cache = new HashMap<>();
        int index = 1;

        for (int i = 0; i < input.size() - 1; i++) {
            cache.put(input.get(i), index);
            index++;
        }

        int lastNumber = input.get(input.size() - 1);

        while (index < value) {
            if (!cache.containsKey(lastNumber)) {
                cache.put(lastNumber, index);
                lastNumber = 0;
            } else {
                int oldIndex = cache.get(lastNumber);
                cache.put(lastNumber, index);
                lastNumber = index - oldIndex;
            }
            index++;
        }
        return lastNumber;
    }

    public static void main(String[] args) {
        String input = "12,20,0,6,1,17,7";
        List<Integer> inputList =
                Stream.of(input.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

        Day15 day15 = new Day15();
        long result1 = day15.calculate(inputList, 2020);
        System.out.println("Answer: " + result1);
    }
}