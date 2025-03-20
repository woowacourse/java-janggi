package domain.piece;

import domain.Direction;
import domain.Movement;
import domain.Movements;
import domain.Position;
import domain.TeamType;
import java.util.List;

public class Elephant extends Piece {
    private static final Movements MOVEMENTS;

    static {
        MOVEMENTS = new Movements(
                List.of(
                        new Movement(List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT_UP)),
                        new Movement(List.of(Direction.UP, Direction.LEFT_UP, Direction.LEFT_UP)),
                        new Movement(List.of(Direction.DOWN, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN)),
                        new Movement(List.of(Direction.DOWN, Direction.LEFT_DOWN, Direction.LEFT_DOWN)),
                        new Movement(List.of(Direction.RIGHT, Direction.RIGHT_UP, Direction.RIGHT_UP)),
                        new Movement(List.of(Direction.RIGHT, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN)),
                        new Movement(List.of(Direction.LEFT, Direction.LEFT_UP, Direction.LEFT_UP)),
                        new Movement(List.of(Direction.LEFT, Direction.LEFT_DOWN, Direction.LEFT_DOWN))
                )
        );
    }

    public Elephant(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private Elephant(Elephant elephant) {
        super(elephant);
    }

    @Override
    public boolean canMove(Position expectedPosition, List<Piece> pieces) {
        if(!MOVEMENTS.canMovePieceToPosition(this, expectedPosition, pieces)){
            return false;
        };
        return hasNotTeamAtPosition(expectedPosition,pieces,(piece -> false));
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }

    @Override
    public Piece newInstance() {
        return new Elephant(this);
    }
}
