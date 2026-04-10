package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;

public class General extends PalacePiece {

    private static final int INITIAL_FILE = 5;
    private static final int FAR_FROM_BASE_ROW = 1;

    public General(Side side) {
        super(side);
    }

    @Override
    public List<Intersection> initAt() {
        int row = side.calculateRowFromBase(FAR_FROM_BASE_ROW);
        return List.of(new Intersection(row, INITIAL_FILE));
    }

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public boolean canBelongToWing() {
        return false;
    }

    @Override
    public boolean isRoyalPiece() {
        return true;
    }

    @Override
    protected boolean isScreenable() {
        return true;
    }
}
