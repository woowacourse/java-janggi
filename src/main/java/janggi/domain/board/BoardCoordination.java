package janggi.domain.board;

public class BoardCoordination implements Coordination {
    private static final int MIN_X = 0;
    private static final int MAX_X = 9;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 8;

    public boolean isInRange(int nx, int ny) {
        return nx >= MIN_X && nx <= MAX_X && ny >= MIN_Y && ny <= MAX_Y;
    }
}
