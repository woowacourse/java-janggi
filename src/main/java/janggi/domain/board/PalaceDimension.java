package janggi.domain.board;

public class PalaceDimension implements Dimension {
    private static final int CHO_MIN_X = 0;
    private static final int CHO_MAX_X = 2;
    private static final int HAN_MIN_X = 7;
    private static final int HAN_MAX_X = 9;
    private static final int MIN_Y = 3;
    private static final int MAX_Y = 5;


    @Override
    public boolean isInRange(int nx, int ny) {
        boolean choXInRange = nx >= CHO_MIN_X && nx <= CHO_MAX_X;
        boolean hanXInRange = nx >= HAN_MIN_X && nx <= HAN_MAX_X;
        return ny >= MIN_Y && ny <= MAX_Y && (choXInRange || hanXInRange);

    }
}
