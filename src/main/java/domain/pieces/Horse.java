package domain.pieces;

import domain.*;

import java.util.List;

public class Horse extends Piece {
    private final List<List<Direction>> horseDirections = List.of(
            List.of(Direction.NORTH, Direction.NORTHEAST), List.of(Direction.NORTH, Direction.NORTHWEST),
            List.of(Direction.SOUTH, Direction.SOUTHEAST), List.of(Direction.SOUTH, Direction.SOUTHWEST),
            List.of(Direction.EAST, Direction.NORTHEAST), List.of(Direction.EAST, Direction.SOUTHEAST),
            List.of(Direction.WEST, Direction.NORTHWEST), List.of(Direction.WEST, Direction.SOUTHWEST)
    );

    public Horse(Camp camp) {
        super(camp, PieceType.HORSE);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        for (List<Direction> directions : horseDirections) {
            List<Position> path = Route.path(from, directions);
            if (path.size() == 2 && path.getLast().equals(to)) {
                return checkPositionExist(boardReader, path);
            }
        }
        return false;
    }

    private static boolean checkPositionExist(BoardReader boardReader, List<Position> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            if (boardReader.isExist(path.get(i))) {
                return false;
            }
        }
        return true;
    }
}
