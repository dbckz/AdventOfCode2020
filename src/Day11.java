import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 {

    private int calculate(Chair[][] chairs) {
        for (Chair[] array : chairs) {
            for (Chair c : array) {
                c.setAdjacentChairs(chairs);
            }
        }


        while (true) {
            boolean result = run(chairs);
            if (!result) {
                int occ = 0;
                for (Chair[] array : chairs) {
                    for (Chair c : array) {
                        if (State.OCCUPIED == c.getState()) {
                            occ++;
                        }
                    }
                }
                return occ;
            }
        }
    }

    private int calculate2(Chair[][] chairs) {
        for (Chair[] array : chairs) {
            for (Chair c : array) {
                c.setVisibleChairs(chairs);
            }
        }

        while (true) {
            boolean result = run2(chairs);
            if (!result) {
                int occ = 0;
                for (Chair[] array : chairs) {
                    for (Chair c : array) {
                        if (State.OCCUPIED == c.getState()) {
                            occ++;
                        }
                    }
                }
                return occ;
            }
        }
    }

    private boolean run(Chair[][] chairs) {
        for (Chair[] array : chairs) {
            for (Chair c : array) {
                c.applyProtocol();
            }
        }

        boolean stateChange = false;
        for (Chair[] array : chairs) {
            for (Chair c : array) {
                if (c.syncState() && !stateChange) {
                    stateChange = true;
                }
            }
        }
        return stateChange;
    }

    private boolean run2(Chair[][] chairs) {
        for (Chair[] array : chairs) {
            for (Chair c : array) {
                c.applyProtocol2();
            }
        }

        boolean stateChange = false;
        for (Chair[] array : chairs) {
            for (Chair c : array) {
                if (c.syncState() && !stateChange) {
                    stateChange = true;
                }
            }
        }
        return stateChange;
    }

    private static class Chair {
        private State state;
        private State newState;
        private int x;
        private int y;
        private List<Chair> adjacentChairs = new ArrayList<>();
        private List<Chair> visibleChairs = new ArrayList<>();

        Chair(State state, int x, int y) {
            this.state = state;
            this.x = x;
            this.y = y;
        }

        private void setAdjacentChairs(Chair[][] chairs) {
            if (x > 0) {
                if (y > 0) {
                    adjacentChairs.add(chairs[x - 1][y - 1]);
                }
                if (y < chairs[0].length - 1) {
                    adjacentChairs.add(chairs[x - 1][y + 1]);
                }
                adjacentChairs.add(chairs[x - 1][y]);
            }

            if (y > 0) {
                adjacentChairs.add(chairs[x][y - 1]);
            }
            if (y < chairs[0].length - 1) {
                adjacentChairs.add(chairs[x][y + 1]);
            }

            if (x < chairs.length - 1) {
                if (y > 0) {
                    adjacentChairs.add(chairs[x + 1][y - 1]);
                }
                if (y < chairs[0].length - 1) {
                    adjacentChairs.add(chairs[x + 1][y + 1]);
                }
                adjacentChairs.add(chairs[x + 1][y]);
            }
        }

        private void applyProtocol() {
            int numOccupied = 0;
            for (Chair chair : adjacentChairs) {
                if (State.OCCUPIED == chair.getState()) {
                    numOccupied++;
                }
            }

            if ((State.EMPTY == state) && (numOccupied == 0)) {
                newState = State.OCCUPIED;
            } else if ((State.OCCUPIED == state) && (numOccupied >= 4)) {
                newState = State.EMPTY;
            } else if (State.FLOOR == this.state) {
                newState = state;
            }
        }

        private void setVisibleChairs(Chair[][] chairs) {
            // above - y constant, x less than own value
            for (int n = 1; x - n >= 0; n++) {
                if (State.FLOOR != chairs[x - n][y].getState()) {
                    visibleChairs.add(chairs[x - n][y]);
                    break;
                }
            }
            // above left
            for (int n = 1; (x - n >= 0) && (y - n >= 0); n++) {
                if (State.FLOOR != chairs[x - n][y - n].getState()) {
                    visibleChairs.add(chairs[x - n][y - n]);
                    break;
                }
            }

            // above right
            for (int n = 1; (x - n >= 0) && (y + n < chairs[0].length); n++) {
                if (State.FLOOR != chairs[x - n][y + n].getState()) {
                    visibleChairs.add(chairs[x - n][y + n]);
                    break;
                }
            }

            // right
            for (int n = 1; (y + n < chairs[0].length); n++) {
                if (State.FLOOR != chairs[x][y + n].getState()) {
                    visibleChairs.add(chairs[x][y + n]);
                    break;
                }
            }
            // below right
            for (int n = 1; (x + n < chairs.length) && (y + n < chairs[0].length); n++) {
                if (State.FLOOR != chairs[x + n][y + n].getState()) {
                    visibleChairs.add(chairs[x + n][y + n]);
                    break;
                }
            }
            // below
            for (int n = 1; x + n < chairs.length; n++) {
                if (State.FLOOR != chairs[x + n][y].getState()) {
                    visibleChairs.add(chairs[x + n][y]);
                    break;
                }
            }
            // below left
            for (int n = 1; (x + n < chairs.length) && (y - n >= 0); n++) {
                if (State.FLOOR != chairs[x + n][y - n].getState()) {
                    visibleChairs.add(chairs[x + n][y - n]);
                    break;
                }
            }
            // left
            for (int n = 1; (y - n >= 0); n++) {
                if (State.FLOOR != chairs[x][y - n].getState()) {
                    visibleChairs.add(chairs[x][y - n]);
                    break;
                }
            }
        }

        private void applyProtocol2() {
            int numOccupied = 0;
            for (Chair chair : visibleChairs) {
                if (State.OCCUPIED == chair.getState()) {
                    numOccupied++;
                }
            }

            if ((State.EMPTY == state) && (numOccupied == 0)) {
                newState = State.OCCUPIED;
            } else if ((State.OCCUPIED == state) && (numOccupied >= 5)) {
                newState = State.EMPTY;
            } else if (State.FLOOR == this.state) {
                newState = state;
            }
        }

        private boolean syncState() {
            if (this.state != this.newState) {
                this.state = this.newState;
                return true;
            }
            return false;
        }

        State getState() {
            return state;
        }

    }

    private enum State {
        FLOOR, // .
        EMPTY, // L
        OCCUPIED; // #


        static State fromChar(char c) {
            switch (c) {
                case '.':
                    return State.FLOOR;
                case 'L':
                    return State.EMPTY;
                case '#':
                default:
                    return State.OCCUPIED;
            }

        }
    }

    public static void main(String[] args) {
        String input = "LLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLL.LLLLLLLLLLLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLLLLLLL.LLLL.LLLLLLLL.LLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLLLL.LL.LLLLLLLL.LLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLL.LLLLL.L.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLLLLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,..L...L.L..L.LL.....L.L..L...L.L.....L.L...LLL..L....L.L.LL.LL...L.....LLL.....L...L..L..L.......,LLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLL.LLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLL.LLLLLLLLLLLLL.LLLLLLLL,LLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLLLLLLLLLLLLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLL.LLLL.LLLLLLLLLLLLL,.....LLL....L..L.L..L.L..L...L.L.LL.....L...L..LL.LL..L.L.LL...LL....LL.....L.....L.L.LL.L..L.L..,LLLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLL.LLLLLLL.LLLLLL.LLLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLL.LLLL.L.LLLLLLLLLLL.LLLLL.LLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLL..LLLLLLLLLLLLLLLLLLLLLL,LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LL.LLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLLLLLLL.LLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL,..L.L.LL.L..LL..L..LL.L..L................L....L............L.L..L...L...L..LLLL....L.L..LL....L.,LLLLLL.LLL.LLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.L.LLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLL.LLL.LLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLL.LLL.L.LLL.LLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LL.L.L.L.....L..L...L.L...L.......L...LLL...L.LL.L.....L.LLL.LL....LLLLL.L....LLL.L..LL..L....L.L,LLLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLL.LLLLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLL,LLLLLLLLLLLLLLL.LLLL.LLLLLLLL.LLLLLLLLLLLLL.LLLLLLL.LLLLLL.LLLLLLLLLLLLLLL.L.LLLLLLLLLLLLLLLLLLLL,LLLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLL.LLLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLL.LLL,.L.LL..LL...L....LL...L.LL.L....LL.LL......L..L........LL..LLL..L...LLL.....LL....L.L...L.....L..,LLLLLLLLLL.LLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLL.LLL.LLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLL.L.LLLLLLLLLLLLL,.LLLLLLLLLLLLLL.LLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLL.L.LLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLLLLLLL.LLLL.LLLLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLL.LLLL.LLLLL.LLLLLLL.LLLLLLLLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLL,LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLL.LLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLL.LLLLLLL.LLL.LLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLL,..L....LL..LL.LLLL......L..L...LLL.L......L.L.....L....L....LLLL.....L..LL...L....L....LLL....LL.,LLLLLLLLLLLLLLL.LLLL.LLLLL.LLLLLLLL..LLLLLL.LLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLL.LLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLLLLLLL.LLLL.LLLLLLLL.LLLL..LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLL.LLLLL.LLLLLLL.LLL.LLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLL..LLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LL,LLLLLLLLLLLLLLL.LLL..LLLLLLLL.LL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLL.LLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLL,...LL.LL.....LL..LLL.....LL....LL....L.LL.L.LL....L........LLLL..LLLLLL.L..L..LLL......L....L....,LLL.LLLLLLLLLLLLLLLL.LLLLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LL.LLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLL,LLLLLLLLLL.LLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLL.LLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLL,.....L.L..LL..LL..L.L.L.....LLLLL.....L..L...L.....L.L..L..L....LL....L...........L.L.......L.LLL,LLLLLLLLLL.LLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLL,LLL.LLLLLL.LLLL.LLLL.LLLLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLL..LLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLL.LLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLL.LLLLLLLLLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLL.LLLLLLL.L.LLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL,LLLL.L.LLL.LLLL.LLLL.LLLLLLLLLLLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLLLLLLLLL.LLLLLLLL.LL.L.LLLLLLLL,L.LLL.................LLL....L...LL..........L.L.L.......L.....L.....LLLLLL......L.......L...L.LL,LLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLL.LLLLLLL.LLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLL.LL.LLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLL.LLLLL.LLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLL.LL,LLLLLLLLLL.LLLL.LLLLLLLLLLLLLLLLLLL.LLL.LLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLLL.LLLLLLLL,LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLLLLLLL.LLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLLLL.LLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLLLLLLL.LLL..LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLL.LLLLLLLLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLL.LLLLLLLL,..L...LL.LLL.LLL.......LL..LL.L...LLL.....L....LLLL.L..........L.....L.L....L..............LL.L..,LLLLLLLLLL.LLLL.LLL..LLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL,LLLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLLLLLLL.LLL.LLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLLLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLL.LLLLLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLLLL.LL.LLLLLLLL,LLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLLLLL.LLLLL.LLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLLLLLLLLLLL.LL.LL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LL.L.L..LL..L.......L.L..LL....L.L.L...L.L..LL....LL.LL....L....LL..L....L............L.......L..,LLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLL,LLLLLLLLLL.LLLLLLLLL.LLLLLLLL.LLLLLLLLLLLLL.LLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLLLLLLLLLLLLLLLL.LLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLL.L.LLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL,..L.L.....L......L..L.....L.......L..L....LL...L.L.L...LLL.L.L..L..L..L......LLL.....L..L..L.....,LLLLLLLLLLLLLLL.LLLLLL.LLLLLL.LLLLL.LLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLL.LLLLLLLLLLLLL.LLLLLLLL,LLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLL.LLLLLL.LLLLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLL.LLLLLLLLLLLL.LLLLLL.L.LLLLLL.LLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLL..LLLLLLLLLLLL.LLLLLLLL,LLLLLLLLLL.LLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLL.LLLLLL.LLLLLLLLLLLLL.LLLLLLLL,LLLLLLLLLL.LLLLLLLLL.LL.LLLLL.LLLLLLLLLLLLL.LLLLLLL.LLLLLL.LLLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLL";
//        String input = "L.LL.LL.LL,LLLLLLL.LL,L.L.L..L..,LLLL.LL.LL,L.LL.LL.LL,L.LLLLL.LL,..L.L.....,LLLLLLLLLL,L.LLLLLL.L,L.LLLLL.LL";
        List<String> inputList =
                Stream.of(input.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
        Chair[][] inputArray = new Chair[inputList.size()][inputList.get(0).length()];
        Chair[][] inputArray2 = new Chair[inputList.size()][inputList.get(0).length()];

        for (int i = 0; i < inputList.size(); i++) {
            for (int j = 0; j < inputList.get(0).length(); j++) {
                inputArray[i][j] = new Chair(State.fromChar(inputList.get(i).charAt(j)), i, j);
                inputArray2[i][j] = new Chair(State.fromChar(inputList.get(i).charAt(j)), i, j);
            }
        }

        Day11 day11 = new Day11();
        int result1 = day11.calculate(inputArray);
        int result2 = day11.calculate2(inputArray2);

        System.out.println("Answer part 1: " + result1);
        System.out.println("Answer part 2: " + result2);
    }
}