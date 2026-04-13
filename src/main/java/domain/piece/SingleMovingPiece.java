package domain.piece;

import domain.board.Country;
import domain.board.Direction;
import domain.board.Position;
import java.util.List;

public abstract class SingleMovingPiece extends Piece {
    private static final int DIRECTION_SIZE = 1;

    private static final String INVALID_DIRECTION_SIZE = "[ERROR] 한 칸만 이동할 수 있습니다.";

    public SingleMovingPiece(PieceInfo pieceInfo) {
        super(pieceInfo);
    }

    @Override
    protected void validateDirections(List<Direction> directions) {
        if (directions.size() != DIRECTION_SIZE) {
            throw new IllegalArgumentException(INVALID_DIRECTION_SIZE);
        }
        if (directions.getFirst().isDiagonal()) {
            throw new IllegalArgumentException(ONLY_MOVE_STRAIGHT);
        }
    }

    protected void validateDirectionsInPalace(List<Direction> directions) {
        if (directions.size() != DIRECTION_SIZE) {
            throw new IllegalArgumentException(INVALID_DIRECTION_SIZE);
        }
    }

    @Override
    protected boolean isInPalaceMove(Position from, Position to, Country country) {
        return country.isInPalace(from) && country.isInPalace(to);
    }
}
