package domain.piece;

import domain.game.Team;
import domain.position.Movement;
import domain.position.Position;
import java.util.List;

public class Soldier extends Piece {
    private static final List<Movement> SIDE_MOVEMENTS = List.of(
            new Movement(0, -1), new Movement(0, 1)
    );

    public Soldier(Team team) {
        super(team, PieceDefinition.BYEONG);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        if (isValidSideMove(source, target)) {
            return true;
        }
        return target.rowDiff(source) == forwardDirection() && target.columnDiff(source) == 0;
    }

    private boolean isValidSideMove(Position source, Position target) {
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        for (Movement movement : SIDE_MOVEMENTS) {
            if (movement.row() == rowDiff && movement.col() == colDiff) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Position> searchRoute(Position source, Position target) {
        return List.of();
    }
}
