package domain.strategy;

import domain.Position;
import domain.Team;
import domain.piece.Piece;
import domain.piece.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class PawnStrategy implements MoveStrategy {

    private static final List<Direction> directions = new ArrayList<>(List.of(Direction.EAST, Direction.WEST));

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();

        Piece piece = board.getPiece(currentPosition);
        Team team = piece.getTeam();

        List<Direction> directions = getDirections(team);
        for (Direction direction : directions) {
            int targetRow = currentPosition.getRows() + direction.getRowOffset();
            int targetColumns = currentPosition.getColumns() + direction.getColOffset();

            if (isWithinBoard(targetRow, targetColumns)) {
                candidates.add(new Position(targetRow, targetColumns));
            }
        }
        return candidates;
    }

    private static List<Direction> getDirections(Team team) {
        if (team == Team.HAN) {
            directions.add(Direction.SOUTH);
        }
        if (team == Team.CHO) {
            directions.add(Direction.NORTH);
        }
        return directions;
    }

    private boolean isWithinBoard(int row, int column) {
        return row >= 0 && row < 10 && column >= 0 && column < 9;
    }
}
