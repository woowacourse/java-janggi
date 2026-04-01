package domain.piece;

import domain.Direction;
import domain.Position;
import java.util.List;

public class MoveOneStepPiece extends MoveStraightPiece {
    private static final int DIRECTION_SIZE = 1;

    private static final String INVALID_DIRECTION_SIZE = "[ERROR] 해당 기물은 한 칸만 이동할 수 있습니다.";

    public MoveOneStepPiece(PieceInfo pieceInfo) {
        super(pieceInfo);
    }

    @Override
    public void validateDirections(List<Direction> directions, Position from, Position to) {
        super.validateDirections(directions, from, to);
        if (directions.size() != DIRECTION_SIZE) {
            throw new IllegalArgumentException(INVALID_DIRECTION_SIZE);
        }
    }
}
