package janggi.gimul;

import janggi.position.PositionConnection;
import janggi.position.PositionPath;
import janggi.position.Position;
import janggi.Team;
import java.util.List;

public class Sang extends Gimul {

    protected Sang(Team team) {
        super(team);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        PositionConnection positionConnection = PositionConnection.of(from, to);

        if (positionConnection.isMoreThanOneStepAndDoubleDiagonal()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        if (positionConnection.isHorizontalLonger()) {
            PositionPath first = from.moveHorizontal(positionConnection.straightDistance());
            PositionPath second = first.getDestination().moveVertical(positionConnection.diagonalDistance());
            PositionPath third = PositionPath.concatenate(first, second);
            PositionPath four = third.getDestination().moveVertical(positionConnection.diagonalDistance());
            return PositionPath.concatenate(third, four);
        }
        PositionPath first = from.moveVertical(positionConnection.straightDistance());
        PositionPath second = first.getDestination().moveHorizontal(positionConnection.diagonalDistance());
        PositionPath third = PositionPath.concatenate(first, second);
        PositionPath four = third.getDestination().moveHorizontal(positionConnection.diagonalDistance());
        return PositionPath.concatenate(third, four);
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimuls) {
        return gimuls.isEmpty();
    }
}
