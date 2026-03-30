package domain.strategy;

import domain.Position;
import domain.Team;
import domain.piece.Piece;
import domain.piece.PieceProvider;

import java.util.ArrayList;
import java.util.List;

public class PawnStrategy implements MoveStrategy {

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();

        Piece piece = board.getPiece(currentPosition);
        Team team = piece.getTeam();

        ArrayList<Direction> directions = getDirections(team);
        for (Direction direction : directions) {
            int targetRow = currentPosition.getRows() + direction.getRowOffset();
            int targetColumns = currentPosition.getColumns() + direction.getColOffset();

            Position targetPosition = new Position(targetRow, targetColumns);
            candidates.add(targetPosition);
        }
        return candidates;
    }

    private static ArrayList<Direction> getDirections(Team team) {
        ArrayList<Direction> directions = new ArrayList<>(List.of(Direction.EAST, Direction.WEST));
        if (team == Team.HAN) {
            directions.add(Direction.SOUTH);
        }
        if (team == Team.CHO) {
            directions.add(Direction.NORTH);
        }
        return directions;
    }
}
