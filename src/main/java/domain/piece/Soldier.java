package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class Soldier extends Piece {
    public Soldier(Team team) {
        super(team, PieceType.BYEONG);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return isValidForwardMove(source, target) || isValidSideMove(source, target);
    }

    private boolean isValidForwardMove(Position source, Position target) {
        int rowDifference = target.rowDifference(source);
        int columnDifference = target.columnDifference(source);
        boolean isForwardOneStep = rowDifference == forwardDirection();
        boolean isSameColumn = columnDifference == 0;
        return isForwardOneStep && isSameColumn;
    }

    private boolean isValidSideMove(Position source, Position target) {
        int rowDifference = target.rowDifference(source);
        int columnDifference = target.columnDifference(source);
        boolean isSameRow = rowDifference == 0;
        boolean isOneColumnAway = Math.abs(columnDifference) == 1;
        return isSameRow && isOneColumnAway;
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        return List.of();
    }
}
