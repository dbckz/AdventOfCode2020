import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {


    private int calculate(List<Instruction> input) throws Exception {
        int accumulator = 0;
        int index = 0;

        while (!input.get(index).hasVisited()) {
            if (input.get(index).getType().equals("acc")) {
                accumulator += input.get(index).getValue();
                input.get(index).visit();
                index++;
            } else if (input.get(index).getType().equals("jmp")) {
                input.get(index).visit();
                index += input.get(index).getValue();
            } else if (input.get(index).getType().equals("nop")) {
                input.get(index).visit();
                index++;
            } else {
                throw new Exception("Unexpected operation type");
            }
        }
        return accumulator;
    }

    private int runTrial(List<Instruction> input) throws Exception {
        int accumulator = 0;
        int index = 0;

        while (index < input.size()) {
            if (input.get(index).hasVisited()) {
                // i.e. we're still stuck in a recursive loop
                return 0;
            }
            if (input.get(index).getType().equals("acc")) {
                accumulator += input.get(index).getValue();
                input.get(index).visit();
                index++;
            } else if (input.get(index).getType().equals("jmp")) {
                input.get(index).visit();
                index += input.get(index).getValue();
            } else if (input.get(index).getType().equals("nop")) {
                input.get(index).visit();
                index++;
            } else {
                throw new Exception("Unexpected operation type");
            }
        }
        return accumulator;
    }

    private int calculate2(List<Instruction> input) throws Exception{
        for (int i = 0; i < input.size(); i++) {
            if (!input.get(i).getType().equals("acc")) {
                List<Instruction> trial = new ArrayList<>();
                for (Instruction in : input) {
                    trial.add(new Instruction(in.getType(), in.getValue()));
                }
                if (input.get(i).getType().equals("jmp")) {
                    trial.get(i).setType("nop");
                } else {
                    trial.get(i).setType("jmp");
                }

                int result = runTrial(trial);
                if (result != 0) {
                    System.out.println("We changed the instruction at index " + i + ", type=" + input.get(i).getType() + ", value=" + input.get(i).getValue());
                    return result;
                }
            }
        }
        return 0;
    }

    private static class Instruction {
        String type; // would be an enum if doing properly
        int value;
        boolean visited = false;

        Instruction(String type, int value) {
            this.type = type;
            this.value = value;
        }

        void visit() {
            visited = true;
        }

        boolean hasVisited() {
            return visited;
        }

        String getType() {
            return type;
        }

        int getValue() {
            return value;
        }

        void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Instruction{" +
                    "type='" + type + '\'' +
                    ", value=" + value +
                    ", visited=" + visited +
                    '}';
        }
    }


    public static void main(String[] args) throws Exception {
        String input = "acc +17,acc +37,acc -13,jmp +173,nop +100,acc -7,jmp +447,nop +283,acc +41,acc +32,jmp +1,jmp +585,jmp +1,acc -5,nop +71,acc +49,acc -18,jmp +527,jmp +130,jmp +253,acc +11,acc -11,jmp +390,jmp +597,jmp +1,acc +6,acc +0,jmp +588,acc -17,jmp +277,acc +2,nop +163,jmp +558,acc +38,jmp +369,acc +13,jmp +536,acc +38,acc +39,acc +6,jmp +84,acc +11,nop +517,acc +48,acc +47,jmp +1,acc +42,acc +0,acc +2,acc +24,jmp +335,acc +44,acc +47,jmp +446,nop +42,nop +74,acc +45,jmp +548,jmp +66,acc +1,jmp +212,acc +18,jmp +1,acc +4,acc -16,jmp +366,acc +0,jmp +398,acc +45,jmp +93,acc +40,acc +38,acc +21,nop +184,jmp -46,nop -9,jmp +53,acc +46,acc +36,jmp +368,acc +16,acc +8,acc -9,acc -4,jmp +328,acc -15,acc -5,acc +21,jmp +435,acc -5,acc +36,jmp +362,acc +26,jmp +447,jmp +1,jmp +412,acc +11,acc +41,nop -32,acc +17,jmp -63,jmp +1,nop +393,jmp +62,acc +18,acc +30,nop +417,jmp +74,acc +29,acc +23,jmp +455,jmp +396,jmp +395,acc +33,nop +137,nop +42,jmp +57,jmp +396,acc +7,acc +0,jmp +354,acc +15,acc +50,jmp -12,jmp +84,nop +175,acc +5,acc -2,jmp -82,acc +1,acc +26,jmp +288,nop -113,nop +366,acc +45,jmp +388,acc +21,acc +38,jmp +427,acc +33,jmp -94,nop -118,nop +411,jmp +472,nop +231,nop +470,acc +48,jmp -124,jmp +1,acc +5,acc +37,acc +42,jmp +301,acc -11,acc -17,acc +14,jmp +357,acc +6,acc +20,acc +13,jmp +361,jmp -65,acc +29,jmp +26,jmp +329,acc +32,acc +32,acc +17,jmp -102,acc -6,acc +33,acc +9,jmp +189,acc +3,jmp -128,jmp -142,acc +24,acc -5,jmp +403,acc +28,jmp +310,acc +34,acc +4,acc +33,acc +18,jmp +227,acc -8,acc -15,jmp +112,jmp +54,acc +21,acc +23,acc +20,jmp +320,acc +13,jmp -77,acc +15,nop +310,nop +335,jmp +232,acc -3,nop +50,acc +41,jmp +112,nop -10,acc +29,acc +27,jmp +52,acc +40,nop -132,acc -16,acc +27,jmp +309,acc -8,nop +147,acc +20,acc +46,jmp +202,acc +27,jmp -43,jmp +1,acc +33,acc -13,jmp +300,acc +1,jmp -202,acc -17,acc +0,acc +34,jmp -5,nop +335,acc -16,acc -17,jmp -120,acc -19,acc -13,acc +4,jmp +368,jmp +21,acc +39,acc +39,acc -18,jmp -157,nop +280,acc +33,nop -37,jmp +32,acc -16,acc +18,acc +46,jmp -121,acc -19,jmp +195,acc +28,jmp +124,jmp +331,jmp -228,jmp -146,jmp +85,jmp +60,acc +20,acc -9,jmp +303,jmp -122,jmp +111,acc +32,acc +0,acc +39,acc +29,jmp -31,nop +320,jmp -63,jmp +223,nop -149,acc -12,acc -11,acc +32,jmp +309,jmp -13,acc -19,jmp -123,acc +21,acc +18,acc +49,jmp +175,acc -14,nop -129,acc -2,acc +31,jmp +79,acc +23,acc +50,acc +39,acc +7,jmp -235,jmp -166,acc +9,jmp +293,acc -11,jmp +76,acc +44,acc +3,acc +37,jmp +123,nop -104,jmp -157,acc +14,acc +10,acc +28,jmp +25,acc +37,jmp +188,jmp -49,acc -11,jmp -90,acc -8,jmp +197,acc +5,jmp +115,acc +44,jmp -228,nop -2,acc +46,jmp +130,nop +183,nop +106,acc +27,acc +37,jmp -309,acc +28,acc -4,acc -12,acc +38,jmp +93,acc +8,acc +23,acc -9,acc +6,jmp -42,acc +10,acc +35,acc +4,jmp -231,acc +19,acc +7,acc +23,acc +11,jmp -90,acc +0,nop +158,nop -150,acc +33,jmp +107,acc +48,acc -2,jmp -104,acc +6,nop -57,nop +172,acc -11,jmp -7,acc +6,acc +50,acc -9,acc +12,jmp -171,acc +3,jmp +26,acc +42,acc +31,acc +20,acc +32,jmp -48,acc +13,jmp -6,jmp +178,acc +47,jmp -153,acc +28,nop +74,jmp -162,acc -15,nop -104,acc -9,jmp -227,acc +49,acc -19,acc +41,jmp -318,acc +9,acc +12,acc +7,jmp +34,jmp +137,nop -143,acc -8,acc +5,acc +31,jmp -20,jmp -237,acc +39,acc +0,jmp -298,acc +45,acc -19,acc +11,jmp -151,acc +40,acc +27,nop +150,nop -391,jmp -341,acc +1,acc +11,acc +18,nop -234,jmp +77,nop +104,jmp -65,acc +32,jmp -27,nop -317,nop +159,acc +14,acc -10,jmp -348,acc +29,jmp +32,acc +48,acc -19,jmp +17,jmp -201,jmp -224,nop +26,acc -7,acc +23,acc +46,jmp -6,acc +22,acc +39,acc +9,acc +23,jmp -30,jmp -243,acc +47,acc -15,jmp -298,jmp -393,jmp +1,acc +3,nop -24,acc +7,jmp -59,acc -6,acc +26,jmp -102,acc +34,acc +24,jmp -207,acc +36,acc +40,acc +41,jmp +1,jmp -306,jmp +57,jmp +1,nop +99,acc +28,jmp -391,acc +50,jmp -359,acc -5,jmp +9,jmp -355,acc +5,acc +2,jmp -77,acc +40,acc +28,acc +22,jmp -262,nop -287,acc +34,acc -4,nop +112,jmp -195,acc +29,nop -94,nop -418,jmp +24,jmp -190,acc +2,jmp -311,jmp -178,jmp -276,acc -12,acc -18,jmp +62,jmp -174,nop +31,acc +33,nop -158,jmp -417,acc +3,acc +21,acc +47,jmp +87,acc +45,jmp -77,acc +6,acc -10,jmp +1,jmp -240,acc +7,acc +47,jmp -379,acc -14,acc +50,nop -75,acc +30,jmp +70,jmp -392,jmp -430,acc +22,acc -2,jmp -492,jmp +1,acc -6,acc +38,jmp -36,nop -336,jmp -32,jmp +61,acc +20,acc -9,acc +2,jmp -175,acc +21,acc -2,jmp -6,jmp -527,acc +11,acc +16,jmp -262,jmp +1,nop -327,acc +29,jmp -114,acc +11,acc +17,acc +26,nop -104,jmp -428,nop -178,nop -242,acc +29,acc +5,jmp -245,jmp -417,jmp -278,acc +35,acc +21,jmp +1,nop -263,jmp +8,acc +42,jmp -95,nop -312,acc -11,acc +34,acc +0,jmp +19,acc +8,acc -13,acc +32,acc +21,jmp -208,acc +15,acc +39,nop -194,jmp -280,jmp +24,nop -516,acc +21,acc +48,jmp -367,jmp -121,acc +49,acc -16,jmp -136,acc +0,jmp -148,jmp -85,jmp -103,nop -446,jmp -242,acc -12,acc +13,acc +31,acc -1,jmp -435,nop -420,acc +22,acc -5,jmp -567,nop -354,acc +11,acc +33,acc +45,jmp -76,acc -2,acc +0,acc +25,acc +46,jmp -555,acc +0,acc +11,nop -2,jmp -394,jmp -395,acc +8,acc +14,acc +47,acc +22,jmp +1";
//        String input = "nop +0,acc +1,jmp +4,acc +3,jmp -3,acc -99,acc +1,jmp -4,acc +6";
        List<String> inputList =
                Stream.of(input.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());

        List<Instruction> instructions = new ArrayList<>();
        for (String s : inputList) {
            String[] split = s.split(" ");
            Instruction inst = new Instruction(split[0], Integer.parseInt(split[1]));
            instructions.add(inst);
        }

        System.out.println(instructions);
        Day8 day8 = new Day8();
        System.out.println("Answer part 1: " + day8.calculate(instructions));
        System.out.println("Answer part 2: " + day8.calculate2(instructions));
    }
}