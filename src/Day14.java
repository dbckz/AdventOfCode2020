import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {

    private long calculate(List<String> input) {
        Map<Integer, Long> memory = new HashMap<>();
        char[] bitmask = new char[36];
        for (String s : input) {
            if (s.startsWith("mask")) {
                bitmask = s.split("=")[1].toCharArray();
                continue;
            }
            String[] split = s.split("=");
            int memoryLocation = Integer.valueOf(split[0]);
            String value = Long.toBinaryString(Long.valueOf(split[1]));
            value = String.format("%0" + (bitmask.length - value.length()) + "d", 0).replace("0", "0") + value;
            char[] valueArray = value.toCharArray();

            for (int i = 0; i < bitmask.length; i++) {
                if (bitmask[i] == 'X') {
                    continue;
                }
                valueArray[i] = bitmask[i];
            }

            memory.put(memoryLocation, Long.parseLong(String.valueOf(valueArray), 2));
        }

        long result = 0;
        for (Long l : memory.values()) {
            result += l;
        }
        return result;
    }

    private long calculate2(List<String> input) {
        Map<Long, Long> memory = new HashMap<>();
        char[] bitmask = new char[36];
        for (String s : input) {
            if (s.startsWith("mask")) {
                bitmask = s.split("=")[1].toCharArray();
                continue;
            }
            String[] split = s.split("=");
            String memoryLocation = Long.toBinaryString(Long.valueOf(split[0]));
            long value = Long.valueOf(split[1]);
            memoryLocation = String.format("%0" + (bitmask.length - memoryLocation.length()) + "d", 0).replace("0", "0") + memoryLocation;
            char[] memoryLocationArray = memoryLocation.toCharArray();


            test(0, bitmask, memoryLocationArray, memory, value);
        }
        long result = 0;
        for (Long l : memory.values()) {
            result += l;
        }
        return result;
    }

    private void test(int start, char[] bitmask, char[] memoryLocationArray, Map<Long, Long> memory, long value) {
        for (int i = start; i < bitmask.length; i++) {
            if (bitmask[i] == 'X') {
                for (int j = 0; j <= 1; j++) {
                    memoryLocationArray[i] = String.valueOf(j).charAt(0);
                    test(i+1, bitmask, memoryLocationArray, memory, value);
                }
            }
            if (bitmask[i] == '1') {
                memoryLocationArray[i] = bitmask[i];
            }
        }

        memory.put(Long.parseLong(String.valueOf(memoryLocationArray), 2), value);
    }


    public static void main(String[] args) {
        String input = "mask=110000011XX0000X101000X10X01XX001011,49397=468472,50029=23224119,39033=191252712,37738=25669,45831=238647542,55749=1020,29592=57996,mask=100X10XXX101011X10X0110111X01X0X0010,10526=1843,2144=177500,33967=5833292,58979=25707732,mask=100010X011XX00X11011010011101100XXX1,1729=1042,30433=366890,7726=2862,19747=52273994,mask=11001X0011010110X01X011X001X0XX01010,40528=32637378,16008=30888145,mask=X11X1X0X10X10110011X0001X01001X100X0,27746=14986812,45873=4381392,26216=538203,mask=1100101011X00010101111001001XX1X0011,30777=84408647,6931=133210956,5173=7497,65147=912575421,12597=55281597,20417=909474,65270=1914920,mask=X100XX10XX010X10X110000000X0X1100100,50768=3383,59421=111147,33900=427465715,33084=14313354,12648=17983288,mask=11X0100011X011100X00100X01111000XX11,17710=60,30013=296,48130=31469003,45585=3231589,mask=X1XX1010110001X0XX000010X0101010X01X,20502=15059188,29762=375,24169=594,24197=64508559,8424=108440,20424=21436372,mask=X10010001XX0X1100000X00000X010X00001,18190=448461,37090=5353,39942=5084619,18325=1962539,mask=10101110110000X010100X1X10XX1X1X1101,9299=6164,8421=990,23905=34526767,44233=39766571,mask=1110X1X01010X1111X0XX1X01110X011001X,53340=16503076,59433=378862,18190=1792792,56498=227,mask=1100100X11000X1X0100X00010X01X010101,65168=265913,40500=18368848,39558=1810777,24300=911,47807=3491,6201=267177,17369=21952,mask=1111101010100X0111001X10011XX1110100,32283=17550,55129=56452456,7945=2961,mask=1X00101X0101001011101000010XX1001100,1120=7335,65276=493090,17104=220,mask=11001X101101101110111XX01001110X0000,15933=859,50326=3145522,48794=367683,24561=57849668,43526=103212,33478=20703997,mask=11001010111X01111001100X100X110X0110,718=1589870,8424=1123972,966=7551,mask=11X01010110001X00000X1X0101X10000000,16160=26953,16417=419431373,54811=430477,4340=180411,mask=10X0X00011X100101X1X1010X1X111X00X10,37425=922346,289=810051,58526=86518,374=92968,37165=6023,61397=8223350,mask=1X001000X1X11X100000100X1011111X1110,43693=743,9418=1128022,11571=47294995,449=52713877,mask=X1XX1X1011000110XX0001X01XX001000000,29924=1125544,10782=342783,15523=218611,8009=1866,mask=10XX011X11000X001X100110100111110100,40200=54187,19587=45108,50857=1309,18658=11992852,mask=1X001000XX1001101100001X010X00001001,21333=7608315,9746=259920,63211=126262747,59768=65880460,mask=11X11X100X1000X01X00X011110011111001,59121=293545,14925=17664197,60673=1663,45765=195645,33094=58807,mask=1X0010X011X1001X1001X0110XX0000000X0,32288=20128,50857=1189904,18918=913,7726=50248226,22429=18716,7848=272580,mask=01XX100010010X1X0X1X00X1X110X1100000,40002=72763964,20337=36642182,19538=230553,11992=8409,mask=11001000X11X111000XXX011X0111000111X,63876=969,1336=5375872,31377=5165,41185=161434,38292=634,mask=1X0010101X00011010X1X10101X011XX1010,59768=10746,27445=2335,26812=58960,40116=104178572,40702=48107383,mask=00000001X0X1011XX011X00X01111100X11X,18702=150975,62270=502767513,6931=15732227,12320=3799,29975=99827,mask=1100100X1100XX1000001X00000010X00110,17011=11786404,25382=98379404,35946=791341,49767=719,11664=738,mask=000XX0011011011000111010X11X111001X1,53375=513,776=31438875,26228=6566431,62653=352,8883=13700386,17292=66198210,mask=1110XX001110X110000X1000010001100100,65123=23447,53419=1784255,32201=472209,mask=10X0X0001101X01000100X000011001001X1,45831=4941253,17666=7,52211=250885474,33711=38546733,54654=108397257,54577=7660097,mask=110010001100X11010000100010XX110X010,48263=203073,46274=329424784,mask=XX00000XXX0X0X101011X11001001100X111,46639=245946590,24300=769,54106=23763,35221=970549,23333=322574122,32283=9651,38047=804,mask=01X00XXXX101011010110010X10001010X01,52675=50846938,43900=69746023,54409=1786723,30815=4286,37=4678667,mask=1X0X1X1010000101110XXX0X0001011X11X0,40133=158160,13432=984,mask=1110X000011X0110001X10010100X0001000,28551=97731716,21298=1506013,mask=110XX01X10100110100X01X001001111X001,5461=26227,4650=1623,mask=110110X0110XX110010000001000101X0001,18167=5899011,45492=18393,13148=171228654,59109=52915776,37=1212,mask=111X1000110X01X01000110X0X00110X1011,13148=11483926,33841=22637,60690=16733,35555=125444,19999=10615,49083=57306580,2958=113424903,mask=1X00X0X011X010100110011XX0X110000X10,16044=2922,58981=99,17754=41326186,57873=767731,mask=0000110011011X10101110X001X0X1101X00,53194=54243360,15023=258913,37425=678,36057=2068683,6540=145235,46515=5824196,mask=1XX0X00X1X0000X010101X00001000101011,42985=2821,17666=178146480,35891=111717,37731=280009,45606=27440,14991=26844935,mask=01X0X010X10101101011001001X001110100,45084=377769619,58867=3974659,48117=374339883,1141=1632150,mask=1010X1X0X10000X01010X01010011111XX0X,45122=3222,2300=16240,58035=6201,40871=16257123,24285=12751,57579=24679,mask=X1XX000X11X01110010X0000000011110001,10424=280052,36995=398570435,160=6920,42829=3609,49083=76851,mask=11001XX011011X100X00000011011X0X011X,24655=976,56929=23232,63878=63802677,19968=15946871,mask=1100101011X1XX1110X100X0X0X11100X100,29216=2636405,3744=344561,60039=11290842,45769=9817,52361=250607,43526=6568339,28084=47601,mask=X10010101101101X1001X001X00011001000,33294=65108649,39245=1562390,18702=880826,mask=X110X00000100100110000X1X01111X1X011,62194=21047,56498=8195045,19165=7369328,13257=536577153,mask=XX00100X0X00111000X1X000X0X011110010,6133=795,40702=1159,49254=936358,20224=33223599,mask=10001000111100101X11100XX101111000X1,12938=250757561,8424=795011162,6681=444240,mask=XX001000111011100000X00100X0XX00000X,34480=317,642=6967048,27203=3233,mask=X100100011X011X0000X11X0XX1X10010X11,9519=6889363,48618=56235450,45084=3643761,22351=128696,mask=1X00101011XX001X1000101100X01X011XX0,43960=1039599408,29626=8360561,31260=256268877,50373=1706687,24558=753,mask=111011X000X0XX1010000X01011XX0XX1101,37425=562,32022=231573,52827=36198,1203=187184,mask=11X0101011110010100X00XX10001010001X,27236=50136301,36499=18610469,23179=193,2602=520829,mask=1X1011000010X1100000X10110X00X1001X1,58650=17011909,30325=1792,21629=146235659,mask=1X000010110X011001X0000X10000X10X1X1,56201=65276,45769=27536,63677=76310013,32288=38391157,2732=553,21153=674,mask=110010001100X1X0000X00X111X001001010,20650=1639,37394=2020484,10598=46526712,18167=18124530,mask=1100X00XX100X1100X0X000000X0111X000X,49767=503,23201=170673423,37394=2873290,mask=11001010X10X00X01011X00XX00101110001,12597=4852003,45585=241,6816=252644,55923=3191,59547=165517,10853=1769226,37991=238,mask=11001000110X11100X0001XX100110X0010X,22590=60452,59590=18099,50198=21070930,5308=5434548,7675=6165055,mask=11XX1001010011100X01010X011111010010,1312=30936,48263=2432189,58137=3014,mask=1000XX101X0100000100X01XX01000000100,27203=610377,11538=1967996,32288=26776,7745=330,43272=1383,18399=6837,mask=111X1000X11001X0X0XX11010100X0100X1X,17790=7714503,54074=32718129,5352=1054,mask=11001110001001011100X00101X000X10X00,18972=783671072,59100=54416,59256=621566,31471=591,2884=2615461,51=790,mask=11101110XX1X0XX11000X1110X1011011000,20222=882,27763=7914,32294=145898791,33294=254866534,24498=96614215,45811=59795025,mask=1100X0X0110001100X0001XX000X1X100X01,31950=1352,10853=766,3709=5103902,mask=110000X0110001100100011X10X001000XX1,30788=426,19168=42816,27236=45039961,21448=8723202,48744=11100131,37=3152,mask=1X0X1010110X0XX001000X00101XX1000101,25916=52795821,1763=5368864,13148=378742711,10853=4345777,64644=8348080,mask=X11011000010011110001X011X100X00000X,45572=172063,39527=19012657,24187=758186,65360=97,37394=2174365,22260=170639258,11465=45577,mask=11011X10XXXX01X111000111111X001X1001,33046=40550135,55128=487381,48068=7496218,24391=15110,mask=11XX1010X0X00XXX11000011110X111X1001,56260=2566,40500=11350955,16482=470,mask=110110X01100011001X0010X101X001011X0,11839=1035,27964=455,21803=109558713,20663=1163,12474=36111,mask=X10010XX1100X11X0010010101001001111X,15464=51852071,59553=620,28798=248109182,mask=11X000001X0X0110000X01111X111X110X01,22073=3262,17070=33580553,11911=2692,mask=1X100000X1110110001011001X011X110X0X,10155=747210936,57352=1286964,12621=3237187,58650=17477,13702=759723,mask=11X010X01XX00110XX00X1X001X011101011,38922=205,45585=99912,53888=48069,44233=1788,mask=1110110X00X00110000010X00000100X0XX1,11817=4458,58578=4618,27624=173091087,mask=XX0110X011010100010X00101010010111X1,14010=3227436,492=6881522,5687=2478716,12673=14623351,53812=140355,mask=110010X0110X0X10X0XX011100X01X10001X,3709=6604,19531=29597,38507=2150917,59768=56061470,54074=4058,mask=11101XX0X010011XX000X0XX01100X111010,60451=1612,42190=37042,20069=96923,21689=592,1247=8651172,48777=40334782,mask=XX1010X011100110X1100X10010X1000X010,18179=45826,33139=838529759,mask=01X010001X0X011X00X01001111XX1100X1X,33377=1739,35840=6769704,14441=22736868,22630=1700619,mask=X110100X1010X11011001110010X0XX0101X,39736=41854026,5320=172367335,24297=10252548,mask=X11X101011000110000XX010000001101010,46338=393890,55364=969778,32531=267024186,704=3741,50527=218631,mask=11X0X00XX0X001X011000XX0000011110011,374=216,30607=788,17248=1204,21290=356140,11719=12908630,5338=98892,mask=XX00100011010110X0001X001000X0100X1X,65147=16521590,50886=4725,29082=846562,26065=24418411,56929=403301,489=2168,mask=1000100011010110XXX01010X10010XX0010,36629=2579,20122=2088646,2798=2730,20062=232728360,27203=3015,47864=10789801,mask=1XX00X001100X0100110X1000XX110X1X011,25357=1792,25872=56296,1964=26399389,mask=1X00100011010X1X101X0X1X01000100000X,61985=42984,19168=394494472,30890=213,58650=581887,50658=2763,mask=10X0XX00110XX010X010000X0X010X100010,42533=153956,58867=470369,59441=176314,53867=1949039,59547=2730,mask=1010X00011XX0X10XX100110X0011X110011,62586=2420073,56548=7379,50515=2405893,mask=10001X00110110101010XX1X010X001X0101,33606=211,3055=106121132,12465=823,mask=11001X10X0XX010111001011X10XX1X0110X,40544=476165,23184=280716800,12930=63529,46092=2274568,38292=1051815696,48873=1125500,mask=XX00XX10110101100X1X11101000001101X1,41185=228856274,20806=6455676,10598=9012,18273=3452904,43960=117914,8412=16428888,56401=15927,mask=110010XX110101100X000011X0010X010X10,15287=1639969,53222=60401483,21266=5960,32861=7234007,61866=36199944,19264=550701,mask=X0X0X100110110X01X1X001X110XX0110001,13148=3209260,49522=22692520,45544=532538,38922=127394,53475=850137,41422=762248838";
//        String input = "mask=000000000000000000000000000000X1001X,42=100,mask=00000000000000000000000000000000X0XX,26=1";
        List<String> inputList =
                Stream.of(input.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());

        Day14 day14 = new Day14();
        long result1 = day14.calculate(inputList);
        System.out.println("Answer part 1: " + result1);
        long result2 = day14.calculate2(inputList);
        System.out.println("Answer part 2: " + result2);
    }
}