package domain.strategy;

import domain.Position;
import domain.Team;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import util.CollisionValidator;

import java.util.ArrayList;
import java.util.List;

public class PawnStrategy implements MoveStrategy {

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();

        Piece piece = board.getPiece(currentPosition);
        Team team = piece.getTeam();

        List<Direction> directions = getDirections(team);
        for (Direction direction : directions) {
            int targetRow = currentPosition.getRows() + direction.getRowOffset();
            int targetColumns = currentPosition.getColumns() + direction.getColOffset();

            if (CollisionValidator.isWithinBoard(targetRow, targetColumns)) {
                Position targetPosition = new Position(targetRow, targetColumns);
                if (CollisionValidator.canMoveToTarget(targetPosition, board, team)) {
                    candidates.add(targetPosition);
                }
            }
        }
        return candidates;
    }

    private static List<Direction> getDirections(Team team) {
        List<Direction> directions = new ArrayList<>(List.of(Direction.EAST, Direction.WEST));
        if (team == Team.HAN) {
            directions.add(Direction.SOUTH);
        }
        if (team == Team.CHO) {
            directions.add(Direction.NORTH);
        }
        return directions;
    }

}
