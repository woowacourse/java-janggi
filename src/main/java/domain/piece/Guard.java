package domain.piece;

import domain.Direction;
import domain.Movement;
import domain.Movements;
import domain.Position;
import domain.TeamType;
import java.util.List;

public class Guard extends Piece {
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

    public Guard(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private Guard(Guard guard){
        super(guard);
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
    public PieceType getType() {
        return PieceType.GUARD;
    }

    @Override
    public Piece newInstance() {
        return new Guard(this);
    }
}
