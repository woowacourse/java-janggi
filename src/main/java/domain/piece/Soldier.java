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

    private Soldier(Soldier soldier) {
        super(soldier);
    }

    @Override
    public boolean canMove(Position expectedPosition, List<Piece> pieces) {
        Movements findMovement = MOVEMENTS.get(this.teamType);
        if(!findMovement.canMovePieceToPosition(this, expectedPosition, pieces)){
            return false;
        };
        return hasNotTeamAtPosition(expectedPosition,pieces,(piece -> false));
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }

    @Override
    public Piece newInstance() {
        return new Soldier(this);
    }
}
