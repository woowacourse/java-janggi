package janggi.gimul;

import janggi.position.DiagonalMove;
import janggi.position.PositionConnection;
import janggi.position.PositionPath;
import janggi.position.Position;
import janggi.Team;
import java.util.List;

public class Jang extends Gimul {
    protected Jang(Team team) {
        super(team);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        PositionConnection positionConnection = PositionConnection.of(from, to);

        if (positionConnection.isMoreThanOneStepIncludingDiagonal()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        if (positionConnection.isHorizontal()) {
            return from.moveHorizontal(positionConnection.columnDistance());
        }

        if (positionConnection.isVertical()) {
            return from.moveVertical(positionConnection.rowDistance());
        }

        DiagonalMove diagonalMove = DiagonalMove.of(positionConnection.rowDistance(), positionConnection.columnDistance());

        return from.moveDiagonal(diagonalMove);
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimuls) {
        return gimuls.isEmpty();
    }
}
