package domain.piece;

import domain.game.Team;
import domain.position.Movement;
import domain.position.Position;
import java.util.List;

public class Elephant extends Piece {
    private static final List<Movement> MOVEMENTS = List.of(
            new Movement(2, 3), new Movement(3, 2), new Movement(-2, 3), new Movement(-3, 2),
            new Movement(-3, -2), new Movement(-2, -3), new Movement(2, -3), new Movement(3, -2)
    );

    public Elephant(Team team) {
        super(team, PieceDefinition.SANG);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        for (Movement movement : MOVEMENTS) {
            if (movement.row() == rowDiff && movement.col() == colDiff) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Position> searchRoute(Position source, Position target) {
        if (source.columnDiff(target) == -3) {
            Position mid = source.add(new Movement(0, 1));
            return List.of(mid, mid.middlePosition(target));
        }
        if (source.columnDiff(target) == 3) {
            Position mid = source.add(new Movement(0, -1));
            return List.of(mid, mid.middlePosition(target));
        }
        if (source.rowDiff(target) == -3) {
            Position mid = source.add(new Movement(1, 0));
            return List.of(mid, mid.middlePosition(target));
        }
        Position mid = source.add(new Movement(-1, 0));
        return List.of(mid, mid.middlePosition(target));
    }
}
