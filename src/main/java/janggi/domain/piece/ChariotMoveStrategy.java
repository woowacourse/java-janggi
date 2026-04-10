package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Direction;
import janggi.domain.position.Palace;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChariotMoveStrategy implements MoveStrategy {

    private static final ChariotMoveStrategy chariotMoveStrategy = new ChariotMoveStrategy();

    private ChariotMoveStrategy () {
    }

    public static ChariotMoveStrategy getInstance() {
        return chariotMoveStrategy;
    }

    @Override
    public List<Position> findMovablePositions(Map<Position, Piece> board, Position from, Dynasty dynasty) {

        List<Position> movablePositions = new ArrayList<>();

        List<Direction> defaultMovableDirections = List.of(Direction.valuesFourDirections());
        for (Direction dir : defaultMovableDirections) {
            List<Position> positions = from.findAllPositionsByDirection(dir);
            movablePositions.addAll(filterMovablePositionsInNotPalace(positions, board, dynasty));
        }

        if(Palace.isPalace(from)) {
            List<Direction> newDirectionsOfPalace = getNewDirectionsOfPalace(from, defaultMovableDirections);
            for (Direction dir : newDirectionsOfPalace) {
                List<Position> positions = from.findAllPositionsByDirection(dir);
                movablePositions.addAll(filterMovablePositionsInPalace(positions, board, dynasty));
            }
        }

        return movablePositions;
    }

    private static List<Position> filterMovablePositionsInNotPalace(List<Position> positions, Map<Position, Piece> board, Dynasty dynasty) {
        return filterMovablePositions(positions, board, dynasty, false);
    }

    private static List<Position> filterMovablePositionsInPalace(List<Position> positions, Map<Position, Piece> board, Dynasty dynasty) {
        return filterMovablePositions(positions, board, dynasty, true);
    }

    private static List<Position> filterMovablePositions(List<Position> positions, Map<Position, Piece> board, Dynasty dynasty, boolean palaceRelated) {
        List<Position> movablePositions = new ArrayList<>();

        for (Position to : positions) {
            if(palaceRelated && !Palace.isPalace(to)) {
                break;
            }
            if (isPiecePresent(board, to)) {
                addIfEnemy(board, dynasty, to, movablePositions);
                break;
            }
            movablePositions.add(to);
        }

        return movablePositions;
    }

    private static List<Direction> getNewDirectionsOfPalace(Position from, List<Direction> defaultDirections) {
        List<Direction> newDirectionsOfPalace = new ArrayList<>(Palace.getMovableDirectionsAtPalace(from));
        newDirectionsOfPalace.removeAll(defaultDirections);
        return newDirectionsOfPalace;
    }

    private static void addIfEnemy(Map<Position, Piece> board, Dynasty dynasty, Position to, List<Position> movablePositions) {
        if (isEnemy(dynasty, board.get(to))) {
            movablePositions.add(to);
        }
    }

    private static boolean isEnemy(Dynasty dynasty, Piece piece) {
        return !piece.isAlly(dynasty);
    }

    private static boolean isPiecePresent(Map<Position, Piece> board, Position position) {
        return board.containsKey(position);
    }

}
