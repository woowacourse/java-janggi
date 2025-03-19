package domain.piece;

import domain.Direction;
import domain.Movement;
import domain.Movements;
import domain.Position;
import domain.TeamType;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {
    private static final Map<TeamType, Movements> MOVEMENTS;

    static {
        MOVEMENTS = Map.of(
                TeamType.CHO,
                new Movements(
                        List.of(new Movement(List.of(Direction.UP)),
                                new Movement(List.of(Direction.RIGHT)),
                                new Movement(List.of(Direction.LEFT)))
                ),
                TeamType.HAN,
                new Movements(
                        List.of(new Movement(List.of(Direction.DOWN)),
                                new Movement(List.of(Direction.RIGHT)),
                                new Movement(List.of(Direction.LEFT)))
                )
        );
    }

    public Soldier(Position position, TeamType teamType) {
        super(position, teamType);
    }

    @Override
    public boolean canMove(Position expectedPosition, List<Piece> pieces) {
        Movements findMovement = MOVEMENTS.get(this.teamType);
        if(!findMovement.canMoveFromTo(this.position,expectedPosition)){
            return false;
        }
        List<Position> intermediatePositions = findMovement.findIntermediatePositions(this.position, expectedPosition);
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
}
