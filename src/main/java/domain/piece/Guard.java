package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;

public class Guard extends PalacePiece {

    private static final int FAR_FROM_BASE_ROW = 0;
    private static final List<Integer> INITIAL_FILES = List.of(4, 6);

    public Guard(Side side) {
        super(side);
    }

    @Override
    public List<Intersection> initAt() {
        int row = side.calculateRowFromBase(FAR_FROM_BASE_ROW);
        return INITIAL_FILES.stream()
                .map(file -> new Intersection(row, file))
                .toList();
    }

    @Override
    public double getScore() {
        return 3;
    }

    @Override
    public boolean canBelongToWing() {
        return false;
    }

    @Override
    public boolean isRoyalPiece() {
        return false;
    }

    @Override
    protected boolean isScreenable() {
        return true;
    }
}
