import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 {

    private class Result {
        private List<Integer> listOfSeats;
        private String maxSeatString;
        private int maxSeatId;
        private int mySeat;

        public Result(List<Integer> listOfSeats, String maxSeatString, int maxSeatId, int mySeat) {
            this.listOfSeats = listOfSeats;
            this.maxSeatString = maxSeatString;
            this.maxSeatId = maxSeatId;
            this.mySeat = mySeat;
        }

        public List<Integer> getListOfSeats() {
            return listOfSeats;
        }

        public String getMaxSeatString() {
            return maxSeatString;
        }

        public int getMaxSeatId() {
            return maxSeatId;
        }

        public int getMySeat() {
            return mySeat;
        }
    }

    private Result calculate(List<String> input) {

        int max = 0;
        String maxString = "";
        List<Integer> all = new ArrayList<>();

        for (String s : input) {
            int value = calculateId(s.substring(0, 7), s.substring(7,10));
            all.add(value);
            if (value > max) {
                max = value;
                maxString = s;
            }
        }

        return new Result(all, maxString, max, determineSeatId(all));
    }

    private int determineSeatId(List<Integer> passes) {
        Collections.sort(passes);

        for (int i = 0; i < passes.size(); i++) {
            if (!(passes.get(i) + 1 == passes.get(i+1))) {
                return passes.get(i) + 1;
            }
        }
        return 0;
    }

    private int calculateId(String row, String col) {
        return calculateBin(row) * 8 + calculateBin(col);
    }

    private int calculateBin(String bin) {
        return Integer.parseInt(bin, 2);
    }


    public static void main(String[] args) {
        String input = "1000110101,0100011010,1000001111,0111000011,0010101100,0110011011,0110000100,1000110100,1101010100,1000001100,0010001111,1100100101,1100110000,0101010000,1100000001,0011100100,0010010111,1000110010,1011110011,1010010000,0010011010,0010100101,0010111001,0001101001,0001110010,0101110000,0110001001,1000111100,0011011110,0100111001,0101100110,0111100000,0010100001,1000010001,0111010011,0100000111,0010110110,1000111001,0110010100,1000011100,0111000110,1101000001,0110110101,0001001111,1011011010,0111001011,1100110011,0011011000,1001111110,0110101100,1000000110,1101010000,0100100001,0101010101,1010111101,1100110001,1001010111,1011010000,0101101101,0110101010,0100100011,1000000111,1000010100,0101000001,1000010010,1100000100,0101000011,1001011010,1010110001,1010111010,1001000110,1000000000,0011111001,0111000000,1000110110,0001011100,0001001110,1010101111,1100010001,1000100000,0101101011,0011110011,1001110011,1100111001,0001011110,1100011011,0010001110,0010001101,1010100100,0100010010,1100000101,1011001101,0101011111,1010000001,0010101011,0001111110,0100110111,1001111011,1001000001,1010010001,0111111101,1010000010,1100100000,0111111001,0011111110,1010000000,0101110100,1001110010,0110111000,0101101111,0011000100,1100111010,0110001100,1101001101,0111001001,1010001110,0100111100,0101111100,1000100101,0011010101,1010100011,0001100011,1010111000,1010101101,1000010011,0011101111,1000011001,1100101011,1010001111,1000011101,0111110111,0110110001,0011011100,1011001011,0111000001,1000100010,0111101001,1011010011,1011100000,1001010100,1011010110,1001001011,0010001000,0001010010,0111011011,1000111011,0100000101,1100111011,1010011011,0100111111,0100100000,1011110100,0111111110,1001111010,0010110011,0011101000,0110000101,1101010111,0101001010,0011011010,0101100000,1000101011,0101001001,0111110010,0010100100,1100110111,0001100001,1011101011,0011001010,1001110110,1011110110,1100100110,0010011111,1001010011,0101001111,0110110010,0100001010,0100011000,0010010101,0100011110,1011101000,1010100010,1010010111,1100111100,1100010010,1100100100,0110010000,0111100001,0010000001,0011110001,1011111111,0011010111,0010010010,0101100111,1011100111,0110110100,0110111101,1000001101,1001011100,0100011011,0010000100,0111101110,0111101101,1011110000,0111110100,0101010010,0010110000,0111100110,1001010000,0001110000,0101111000,1011110101,1101000111,0111111010,1000101010,0011010001,1011101010,0111111100,0011000111,1000011111,0100111000,1101000010,0010001011,0011101001,1001100111,0110100010,0011111011,0111011000,1101010101,0101011011,0111011101,0101011001,0001101111,0111001100,0011110010,1100100111,0101010001,0111010101,1001110111,0001011001,1101011011,0100110101,0011100001,0011010010,1001000000,1010010110,0101111110,1011110001,0110011100,1001101101,1010001011,0011001011,0110000110,1011011001,0100010101,1011110010,1011111100,0011110101,1101001001,0110001011,0100000000,0110010101,0100111110,0010101110,0011110000,0010111101,0110101101,1100011001,0110011000,0011000000,0111110011,0011000011,1001001001,0100101111,0001001101,0100000011,1000101100,0101101110,0010111010,1000110001,0110011111,1011010100,0100001100,1100001011,1001010001,0010111110,0110001101,0011110111,1011001001,1010001000,1000111111,0101110111,1010011110,0101101001,1000111101,1000010000,1001001101,0110101111,0011011111,0100011001,1011011101,0011111101,0100110110,1001110001,0111001000,1010100111,1010110111,0110011001,1000000001,0111100101,0111111000,1011110111,1011100101,0010011100,0011010000,1010001010,0001110011,0110100110,0001010000,1001101000,1100001000,1011111110,0110101011,0100101100,1101011000,0011101011,0111000111,1001111000,0101010111,1000001000,1010101001,1100001110,0111010000,0010000000,0101001000,0111111111,1010010011,0010101000,0010010110,1010110110,0011011001,0111110001,1001000100,1100011000,0100000001,0011100111,0100010111,0001111010,0101011110,0110101000,1010000011,1100101010,0001010101,1001100010,0001100110,1010101110,0011100000,1100010011,0001111001,0011100101,0010110010,0011101010,1010000110,0101100011,0111111011,0101111111,0010100011,0110100101,1001111111,1000101000,1001100000,1000011010,0011111000,0111100111,0011001110,1101100000,0001101110,1010000101,0100101000,0001100010,1100100001,0110100011,0100111101,0100000110,0001101100,1001000010,1001101011,0010101010,0110101001,1100001001,1010101010,0011101110,1100101001,1100001111,0010101101,1101000101,0111010100,0011010110,1101011001,0011000010,0010011000,0101100001,0010000011,1011001100,1011011000,1101000110,0111011110,1101011101,1101001110,0101110010,1010100110,0011110110,1000110111,0001111100,0111001101,0110111100,1010011010,0001110001,1011011111,0001011000,0001111101,0100101001,0101011101,0011001101,1100000000,0001001100,0011111100,0110100001,1100011010,0111011001,1000000010,0001010111,1000001010,1011010010,1011001110,0101100100,0010100110,1000110000,1100101101,1001011001,0101111010,0100100111,0100011111,0110011010,0101111101,1011000111,1001101110,0110100100,0011000101,0001010011,0011110100,0110111010,1100110101,1100010000,1000010110,1001101001,0010001001,0001011011,1010010010,1001101100,0100110100,0011010011,1100101100,0111001110,0101110001,0011000001,0100110011,1010101000,1101001000,1000110011,1011101100,1010110100,1101000000,0100001011,1000101001,0011001000,0111000010,1001001110,0110101110,0111110101,0111100010,0111010110,1000111110,0110100111,1000010101,1101001011,1011111101,0100011101,0100110010,0100001001,1001010110,0010111000,0110010011,1010101011,0001010110,0011111010,1001000111,0010101001,0010011110,1001001100,1100101000,1011011110,0010011101,1000100111,1011001000,0100010001,1010001001,0011100010,0010110111,1011000101,1000100001,0110110110,0110010010,1011010001,0011101101,0101100010,0101010110,0111010010,0101100101,0010010011,1001011110,1001111100,1010111110,1000011110,1000011000,0100010011,1000111010,1100110010,0011010100,0111000101,0100101110,1011111000,1100101111,0111001010,1011000000,1100000111,0001001011,0111010001,1100011111,0100010000,1100010101,1001010101,1011100110,1010111100,1001101010,0010110001,0110001000,1101010010,0001011111,1100001100,1001001000,1000111000,0001101011,0110111011,0100011100,0010001010,1100010100,1010100101,0101011100,0111001111,0101000101,1011101001,0100010110,1011000010,1001101111,1101000100,1011100001,0100111011,1001110100,1011000110,0001111011,1100111101,0101111001,1000101111,0101000000,0001110111,0010010000,1100111111,0010111100,0111100011,0110001110,0111011111,0101101010,1100011100,0101001011,0010000110,0111101000,0001100111,1010110010,1010111111,0100110001,0101011000,1010110101,0011101100,1101010001,1000100110,0001110100,0100100010,1000001001,0001101101,1101001010,1001100100,1011000011,1011111001,1001100001,0010101111,0011001001,0111011100,1011001010,0110111110,0111101010,1011100010,1000001011,0101000100,0101001110,0100100100,1010101100,1001010010,1011001111,1100000010,0111011010,1010011100,1100011101,1010010100,0010001100,1001111001,0100000010,1011100100,1001011000,1100111000,1001100011,1001000101,0101000110,0110010110,0110111111,1000001110,1011010111,1100100010,0101101100,0101010011,1100110100,0011100110,1001100110,1001011111,1100000110,0011011101,1101011111,1010000100,1001100101,0001101010,1100000011,1010110000,1011111011,0001010001,0100001000,1001110000,1001111101,1000000011,0001011010,1000010111,0100110000,0100010100,1101001100,1100001010,1100100011,0111101111,0110000000,0011111111,1010011001,0100000100,0010100111,0010010001,1010110011,0101111011,0110000011,0010111011,0100001101,0101110011,1010000111,0110000001,1101011010,0011001111,0010011001,1010100001,0110110011,0110001010,1001011011,0011000110,0100101101,0100101010,0010110101,0010000010,1100001101,0100101011,1001001010,1010011111,0100001110,0101011010,0001111111,1100110110,0110011101,1000000101,0110110111,0101110101,1010001101,0110010111,0011011011,0001100101,0001011101,0110000010,0101001100,1011000001,0110111001,1000000100,1100111110,0101101000,1101010011,0010100010,0010110100,0111000100,1100101110,0111010111,0010100000,0100001111,1010011000,0001010100,0110110000,1100010110,0001101000,1101011100,1011101111,1000011011,1010001100,1000100011,1001110101,0110000111,0100111010,0011100011,0010111111,1011000100,1010111001,0001100100,0101010100,0111100100,0001110101,1010111011,0100100110,0010010100,0110001111,0110011110,1100010111,0001110110,0001100000,1011010101,0111110000,1010100000,0101000111,0100100101,1011011011,0011001100,1011011100,0111110110,1000101101,1011111010,1001001111,1101010110,1010010101,1001000011,0111101100,1101001111,1000101110,1001011101,0111101011,1010011101,1011101101,1100011110,0110100000,0010000101,0101001101,1000100100,0101110110,0101000010,1101000011,0010000111,1011101110,0001111000,1101011110,0010011011,0110010001";
//        String input = "0101100101,1000110111,0001110111,1100110100";
        List<String> inputList =
                Stream.of(input.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
        System.out.println("Total passes: " + inputList.size());
        Day5 day5 = new Day5();

        Result result = day5.calculate(inputList);
        System.out.println("Answer part 1: " + result.getMaxSeatId());
        System.out.println("Answer part 2: " + result.getMySeat());

    }
}