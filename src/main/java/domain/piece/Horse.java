package domain.piece;

import domain.game.Team;
import domain.position.Movement;
import domain.position.Position;
import java.util.List;

public class Horse extends Piece {
    private static final List<Movement> MOVEMENTS = List.of(
            new Movement(1, 2), new Movement(2, 1), new Movement(2, -1), new Movement(1, -2),
            new Movement(-1, -2), new Movement(-2, -1), new Movement(-2, 1), new Movement(-1, 2)
    );

    public Horse(Team team) {
        super(team, PieceDefinition.MA);
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
        if (source.columnDiff(target) == -2) {
            return List.of(source.add(new Movement(0, 1)));
        }
        if (source.columnDiff(target) == 2) {
            return List.of(source.add(new Movement(0, -1)));
        }
        if (source.rowDiff(target) == -2) {
            return List.of(source.add(new Movement(1, 0)));
        }
        return List.of(source.add(new Movement(-1, 0)));
    }
}
