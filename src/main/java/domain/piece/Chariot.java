package domain.piece;

import domain.game.Team;
import domain.position.Movement;
import domain.position.PalacePosition;
import domain.position.Position;
import java.util.List;

public class Chariot extends Piece {
    private static final List<Movement> MOVEMENTS = List.of(
            new Movement(1, 1), new Movement(1, -1), new Movement(-1, 1), new Movement(-1, -1),
            new Movement(2, 2), new Movement(2, -2), new Movement(-2, 2), new Movement(-2, -2)
    );

    public Chariot(Team team) {
        super(team, PieceDefinition.CHA);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return isStraightMove(source, target) || isPalaceDiagonalMove(source, target);
    }

    private boolean isPalaceDiagonalMove(Position source, Position target) {
        if (!isInPalace(source, target)) {
            return false;
        }
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        for (Movement movement : MOVEMENTS) {
            if (movement.row() == rowDiff && movement.col() == colDiff) {
                return PalacePosition.isCanMoveDiagonal(source);
            }
        }
        return false;
    }

    private boolean isStraightMove(Position source, Position target) {
        return source.isSameCol(target) || source.isSameRow(target);
    }

    @Override
    public List<Position> searchRoute(Position source, Position target) {
        if (source.isSameCol(target)) {
            return source.betweenSameCol(target);
        }
        if (source.isSameRow(target)) {
            return source.betweenSameRow(target);
        }
        if (Math.abs(target.rowDiff(source)) == 2) {
            return List.of(source.middlePosition(target));
        }
        return List.of();
    }

    private boolean isInPalace(Position src, Position dest) {
        return PalacePosition.isPalacePosition(src) && PalacePosition.isPalacePosition(dest);
    }
}
