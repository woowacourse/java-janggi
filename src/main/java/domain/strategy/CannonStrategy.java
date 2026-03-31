package domain.strategy;

import domain.Position;
import domain.Team;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import util.CollisionValidator;

import java.util.ArrayList;
import java.util.List;

public class CannonStrategy implements MoveStrategy {

    private static final List<Direction> straightDirections = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    @Override
    public List<Position> getMoveCandidates(Position currentPosition, PieceProvider board) {
        List<Position> candidates = new ArrayList<>();
        Team myTeam = board.getPiece(currentPosition).getTeam();

        for (Direction direction : straightDirections) {
            addCannonCandidates(currentPosition, direction, board, candidates, myTeam);
        }
        return candidates;
    }

    private void addCannonCandidates(Position current, Direction direction, PieceProvider board, List<Position> candidates, Team myTeam) {
        int nextRows = current.getRows() + direction.getRowOffset();
        int nextColumns = current.getColumns() + direction.getColOffset();

        while (CollisionValidator.isWithinBoard(nextRows, nextColumns)) {
            Position nextPosition = new Position(nextRows, nextColumns);
            if (!board.isBlank(nextPosition)) {
                checkBridgeAndCollect(nextPosition, direction, board, candidates, myTeam);
                return;
            }
            nextRows += direction.getRowOffset();
            nextColumns += direction.getColOffset();
        }
    }

    private void checkBridgeAndCollect(Position bridge, Direction direction, PieceProvider board, List<Position> candidates, Team myTeam) {
        if (!board.getPiece(bridge).isBridge()) return;
        int nextRow = bridge.getRows() + direction.getRowOffset();
        int nextColumn = bridge.getColumns() + direction.getColOffset();

        while (CollisionValidator.isWithinBoard(nextRow, nextColumn)) {
            Position target = new Position(nextRow, nextColumn);
            if (addCandidateAndCheckPiece(target, board, candidates, myTeam)) return;

            nextRow += direction.getRowOffset();
            nextColumn += direction.getColOffset();
        }
    }

    private boolean addCandidateAndCheckPiece(Position target, PieceProvider board, List<Position> candidates, Team myTeam) {
        Piece targetPiece = board.getPiece(target);
        if (CollisionValidator.canMoveToTarget(target, board, myTeam)) {
            if (targetPiece.isBlank() || targetPiece.isCatchByCannon()) {
                candidates.add(target);
            }
        }
        return !targetPiece.isBlank();
    }
}
