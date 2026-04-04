package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceStrategy;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class ChariotStrategy implements MoveStrategy {

    @Override
    public void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceStrategy pieceStrategy) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);
        List<Position> path = findPath(source, directionInformation);
        List<Position> pathBeforeDestination = path.subList(0, path.size() - 1);
        validatePath(pathBeforeDestination, board);
    }

    private List<Position> findPath(Position source, DirectionInformation directionInformation) {
        if (directionInformation.isHorizontal()) {
            return createPath(source, directionInformation.colDistance(), Position::moveCol);
        }
        if (directionInformation.isVertical()) {
            return createPath(source, directionInformation.rowDistance(), Position::moveRow);
        }
        throw new IllegalArgumentException(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
    }

    private List<Position> createPath(Position source, int difference, BiFunction<Position, Integer, Position> move) {
        List<Position> path = new ArrayList<>();
        int direction = Integer.signum(difference);
        int distance = Math.abs(difference);

        for (int i = 0; i < distance; i++) {
            source = move.apply(source, direction);
            path.add(source);
        }
        return path;
    }

    private void validatePath(List<Position> path, BoardChecker board) {
        if (isEmptyPath(path, board)) {
            throw new IllegalArgumentException(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
        }
    }

    private boolean isEmptyPath(List<Position> path, BoardChecker board) {
        return path.stream()
                .anyMatch(board::hasPieceAt);
    }
}
