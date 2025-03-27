package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Board {

    private static final double HAN_BONUS_SCORE = 1.5;
    private final Map<Position, Piece> pieceMap;

    public Board(Map<Position, Piece> pieceMap) {
        validate(pieceMap);
        this.pieceMap = new HashMap<>(pieceMap);
    }

    public void validate(Map<Position, Piece> pieceMap) {
        if (pieceMap == null) {
            throw new IllegalArgumentException();
        }
    }

    public boolean hasPiece(Position position) {
        return pieceMap.containsKey(position);
    }

    public boolean isSameSide(Team team, Position position) {
        return getPiece(position).isSameSide(team);
    }

    public void checkMoveablePiece(Team team, Position position) {
        validatePositionExists(position);
        Piece piece = pieceMap.get(position);
        if (!piece.isSameSide(team)) {
            throw new IllegalArgumentException(ErrorMessage.IS_NOT_SAME_SIDE.getMessage());
        }

        if (piece.getAvailableMovePositions(this, position).isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_PIECE.getMessage());
        }
    }

    private void validatePositionExists(Position position) {
        if (!pieceMap.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
        }
    }

    public void movePiece(Position currentPosition, Position targetPosition) {
        Piece piece = getPiece(currentPosition);
        Set<Position> availablePositions = piece.getAvailableMovePositions(this, currentPosition);

        if (!availablePositions.contains(targetPosition)) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_TO_POSITION.getMessage());
        }

        pieceMap.remove(currentPosition);
        // TODO PUT을 할 때 기존의 POSITION의 키가 덮어씌워진다는 것을 표현할 수 없다. 이러한 방식을 리팩토링할 필요가 있다.
        pieceMap.put(targetPosition, piece);
    }

    public Piece getPiece(Position position) {
        if (!pieceMap.containsKey(position)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BOARD_POSITION.getMessage());
        }

        return pieceMap.get(position);
    }

    public boolean canMoveToPosition(Team team, Position position) {
        return !hasPiece(position) || !isSameSide(team, position);
    }

    public boolean hasGeneral(Team team) {
        return pieceMap.values().stream()
                .anyMatch(piece -> piece.isGeneral(team));
    }

    public boolean isCannon(Position position) {
        return hasPiece(position) && getPiece(position).isCannon();
    }

    public double getScore(Team team) {
        int sum = pieceMap.values().stream()
                .filter(piece -> piece.isSameSide(team))
                .filter(piece -> !piece.isGeneral(team))
                .mapToInt(Piece::toScore)
                .sum();

        if (team.isSameSide(Team.HAN)) {
            return sum + HAN_BONUS_SCORE;
        }

        return sum;
    }

    public String getPieceName(int row, int column) {
        Position position = Position.of(row, column);

        if (!hasPiece(position)) {
            return "＿";
        }

        return pieceMap.get(position).toName();
    }
}
