package domain;

public class JanggiBoard implements Board {

    @Override
    public boolean isOutOfBoundary(int row, int col) {
        if (row < 1 || row > 9) {
            return true;
        }
        return col < 1 || col > 10;
    }
}
