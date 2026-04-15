package domain.piece;

import domain.game.Team;
import domain.position.Movement;
import domain.position.PalacePosition;
import domain.position.Position;
import java.util.List;

public abstract class PalacePiece extends Piece {
    private static final List<Movement> MOVEMENTS = List.of(
            new Movement(-1, 0), new Movement(1, 0), new Movement(0, -1), new Movement(0, 1),
            new Movement(1, 1), new Movement(1, -1), new Movement(-1, 1), new Movement(-1, -1)
    );

    protected PalacePiece(Team team, PieceDefinition type) {
        super(team, type);
    }

    public static General general(Team team) {
        return new General(team);
    }

    public static Guard guard(Team team) {
        return new Guard(team);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        if (!isInPalace(source, target)) {
            return false;
        }
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        for (Movement movement : MOVEMENTS) {
            if (movement.row() == rowDiff && movement.col() == colDiff) {
                if (isDiagonalMove(movement)) {
                    return PalacePosition.isCanMoveDiagonal(source);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Position> searchRoute(Position source, Position target) {
        return List.of();
    }

    private boolean isDiagonalMove(Movement m) {
        return Math.abs(m.row()) == Math.abs(m.col());
    }

    private boolean isInPalace(Position src, Position dest) {
        return PalacePosition.isPalacePosition(src) && PalacePosition.isPalacePosition(dest);
    }
}
