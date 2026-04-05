package domain.strategy;

import domain.Team;
import domain.position.Position;
import domain.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements Strategy {
    @Override
    public List<Position> getMoveCandidates(Position from, Team team, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();

        Direction[] straightDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction straight : straightDirections) {
            // 1칸 직선 방향으로 가기
            int myeokRow = from.row() + straight.getRowOffset(team);
            int myeokCol = from.col() + straight.getColOffset(team);
            Position myeokPosition = new Position(myeokRow, myeokCol);

            if (board.isBlank(myeokPosition)) {
                List<Direction> diagonals = getDiagonalsFor(straight);
                for (Direction diag : diagonals) {
                    int targetRow = myeokPosition.row() + diag.getRowOffset(team);
                    int targetCol = myeokPosition.col() + diag.getColOffset(team);
                    Position targetPosition = new Position(targetRow, targetCol);

                    candidates.add(targetPosition);
                }
            }
        }
        return candidates;
    }

    private List<Direction> getDiagonalsFor(Direction straight) {
        if (straight == Direction.NORTH) return List.of(Direction.NORTH_WEST, Direction.NORTH_EAST);
        if (straight == Direction.SOUTH) return List.of(Direction.SOUTH_WEST, Direction.SOUTH_EAST);
        if (straight == Direction.WEST) return List.of(Direction.NORTH_WEST, Direction.SOUTH_WEST);
        if (straight == Direction.EAST) return List.of(Direction.NORTH_EAST, Direction.SOUTH_EAST);
        return List.of();
    }
}
