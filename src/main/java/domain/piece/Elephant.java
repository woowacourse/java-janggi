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
                        new Movement(List.of(Direction.UP, Direction.RIGHT_UP,Direction.RIGHT_UP)),
                        new Movement(List.of(Direction.UP, Direction.LEFT_UP,Direction.LEFT_UP)),
                        new Movement(List.of(Direction.DOWN, Direction.RIGHT_DOWN,Direction.RIGHT_DOWN)),
                        new Movement(List.of(Direction.DOWN, Direction.LEFT_DOWN,Direction.LEFT_DOWN)),
                        new Movement(List.of(Direction.RIGHT, Direction.RIGHT_UP,Direction.RIGHT_UP)),
                        new Movement(List.of(Direction.RIGHT, Direction.RIGHT_DOWN,Direction.RIGHT_DOWN)),
                        new Movement(List.of(Direction.LEFT, Direction.LEFT_UP,Direction.LEFT_UP)),
                        new Movement(List.of(Direction.LEFT, Direction.LEFT_DOWN,Direction.LEFT_DOWN))
                )
        );
    }

    public Elephant(Position position, TeamType teamType) {
        super(position, teamType);
    }

    @Override
    public boolean canMove(Position expectedPosition, List<Piece> pieces) {
        if(!MOVEMENTS.canMoveFromTo(this.position,expectedPosition)){
            return false;
        }
        List<Position> intermediatePositions = MOVEMENTS.findIntermediatePositions(this.position, expectedPosition);
        for (Position intermediatePosition : intermediatePositions) {
            for (Piece piece : pieces) {
                if(piece.hasSamePosition(intermediatePosition)){
                    return false;
                }
            }
        }
        boolean check = pieces.stream()
                .anyMatch(piece -> piece.hasSamePosition(expectedPosition) && piece.isSameTeam(this));
        if(check){
            return false;
        }
        return true;
    }

    @Override
    protected PieceType getType() {
        return PieceType.ELEPHANT;
    }
}
