package piece;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import board.Board;
import board.Position;

public class Cannon extends Piece {

    public Cannon(final Team team) {
        super(team);
    }

    @Override
    protected Set<Position> getMovablePositions(final Position position, final Board board) {
        Map<Direction, Position> hurdlePositions = findHurdlePositions(position, board);
        if (hurdlePositions.isEmpty()) {
            throw new IllegalArgumentException("움직일 수 없습니다.");
        }
        Set<Position> movablePositions = new HashSet<>();
        for (Direction direction : hurdlePositions.keySet()) {
            Position movablePosition = hurdlePositions.get(direction);
            addMovablePosition(board, direction, movablePosition, movablePositions);
        }
        return movablePositions;
    }

    private Map<Direction, Position> findHurdlePositions(final Position position, final Board board) {
        Map<Direction, Position> hurdlePositions = new EnumMap<>(Direction.class);
        for (Direction straightDirection : Direction.getStraightDirection()) {
            addHurdlePosition(position, board, straightDirection, hurdlePositions);
        }
        return hurdlePositions;
    }

    private void addHurdlePosition(final Position position, final Board board, final Direction straightDirection,
                                   final Map<Direction, Position> hurdlePositions
    ) {
        Position movePosition = position;
        while (true) {
            movePosition = movePosition.moveByDirection(straightDirection);
            if (movePosition.isInValidPosition() || board.isCannonPosition(movePosition)) {
                break;
            }
            if (board.isExists(movePosition)) {
                hurdlePositions.put(straightDirection, movePosition);
                break;
            }
        }
    }

    private void addMovablePosition(final Board board, final Direction direction,
                                    final Position hurdlePosition, final Set<Position> movablePositions
    ) {
        Position movablePosition = hurdlePosition;
        while (true) {
            movablePosition = movablePosition.moveByDirection(direction);
            if (movablePosition.isInValidPosition()) {
                break;
            }
            if (board.isSameTeamPosition(team, movablePosition) || board.isCannonPosition(movablePosition)) {
                break;
            }
            movablePositions.add(movablePosition);
            if (board.isExists(movablePosition)) {
                break;
            }
        }
    }

    @Override
    public String getDisplayName() {
        return "포";
    }

}
