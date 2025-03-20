package domain.piece;

import domain.Direction;
import domain.Movement;
import domain.Movements;
import domain.Position;
import domain.TeamType;
import java.util.List;

public class King extends Piece {
    private static final Movements MOVEMENTS;

    static {
        MOVEMENTS = new Movements(
                List.of(
                        new Movement(List.of(Direction.UP)),
                        new Movement(List.of(Direction.DOWN)),
                        new Movement(List.of(Direction.RIGHT)),
                        new Movement(List.of(Direction.LEFT))
                )
        );
    }

    public King(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private King(King king) {
        super(king);
    }

    @Override
    public boolean canMove(Position expectedPosition, List<Piece> pieces) {
        return MOVEMENTS.canMovePieceToPosition(this, expectedPosition, pieces);
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public Piece newInstance() {
        return new King(this);
    }
}
