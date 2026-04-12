package domain.piece;

import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.state.Side;

import java.util.List;

public class EmptyPiece extends Piece {

    private static final EmptyPiece INSTANCE = new EmptyPiece();

    private EmptyPiece() {
        super(
                PieceType.EMPTY,
                Side.NEUTRAL
        );
    }

    public static EmptyPiece getInstance() {
        return INSTANCE;
    }

    @Override
    public List<List<Direction>> getPotentialPaths(Position start) {
        return List.of();
    }

    @Override
    public int getScore() {
        return 0;
    }
}
