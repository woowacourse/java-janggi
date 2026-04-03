package domain.pieces;

import domain.*;

import java.util.List;

public class Elephant extends Piece {
    private final List<List<Direction>> elephantDirections = List.of(
            List.of(Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST),
            List.of(Direction.NORTH, Direction.NORTHWEST, Direction.NORTHWEST),
            List.of(Direction.SOUTH, Direction.SOUTHEAST, Direction.SOUTHEAST),
            List.of(Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST),
            List.of(Direction.EAST, Direction.NORTHEAST, Direction.NORTHEAST),
            List.of(Direction.EAST, Direction.SOUTHEAST, Direction.SOUTHEAST),
            List.of(Direction.WEST, Direction.NORTHWEST, Direction.NORTHWEST),
            List.of(Direction.WEST, Direction.SOUTHWEST, Direction.SOUTHWEST)
    );

    public Elephant(Camp camp) {
        super(camp, PieceType.ELEPHANT);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        for (List<Direction> directions : elephantDirections) {
            List<Position> path = Route.path(from, directions);
            if (path.size() == 3 && path.getLast().equals(to)) {
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
