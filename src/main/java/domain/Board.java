package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<BoardPosition, Piece> pieces;

    public Board(final Map<BoardPosition, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void movePiece(
        final BoardPosition targetBoardPosition,
        final BoardPosition moveBoardPosition
    ) {
        if (!pieces.containsKey(targetBoardPosition)) {
            throw new IllegalArgumentException("이동하려는 위치에 기물이 없습니다.");
        }

        final Piece movePiece = pieces.get(targetBoardPosition);
        final Piece targetPiece = pieces.get(moveBoardPosition);
        if (targetPiece != null
            && movePiece.getTeam() == targetPiece.getTeam()) {
            throw new IllegalArgumentException("이동하려는 위치에 아군 기물이 존재합니다.");
        }

        final List<Offset> movementRule = movePiece.findMovementRule(
            targetBoardPosition, moveBoardPosition);
        validateMovementRule(movementRule, targetBoardPosition,
            moveBoardPosition, movePiece);

        if (targetPiece != null
            && movePiece.getTeam() != targetPiece.getTeam()) {

            pieces.remove(moveBoardPosition);
        }

        pieces.remove(targetBoardPosition);
        pieces.put(moveBoardPosition, movePiece);
    }

    private void validateMovementRule(
        final List<Offset> movementRule,
        final BoardPosition targetBoardPosition,
        final BoardPosition moveBoardPosition,
        final Piece movePiece
    ) {
        BoardPosition currentBoardPosition = targetBoardPosition;
        int obstacleCount = 0;
        for (final Offset offset : movementRule) {
            currentBoardPosition = currentBoardPosition.calculatePosition(
                offset);
            if (currentBoardPosition.equals(moveBoardPosition)) {
                continue;
            }
            if (pieces.containsKey(currentBoardPosition)) {
                obstacleCount++;
            }
        }
        if (!movePiece.isObstacleCountAllowed(obstacleCount)) {
            throw new IllegalArgumentException("이동경로에 적합하지 않은 장애물이 있습니다.");
        }
        if (movePiece.getPieceType() == PieceType.CANNON) {
            validateCannonMovementRule(movementRule, targetBoardPosition);
        }
    }

    private void validateCannonMovementRule(
        final List<Offset> movementRule,
        final BoardPosition targetBoardPosition
    ) {
        BoardPosition currentBoardPosition = targetBoardPosition;
        for (final Offset offset : movementRule) {
            currentBoardPosition = currentBoardPosition.calculatePosition(
                offset);
            if (pieces.containsKey(currentBoardPosition) && pieces.get(
                    currentBoardPosition)
                .getPieceType() == PieceType.CANNON) {
                throw new IllegalArgumentException("포는 포를 넘거나 잡을 수 없습니다.");
            }
        }
    }

    public static Board initialize() {
        final Map<BoardPosition, Piece> pieces = new HashMap<>();
        initializeBoard(pieces);
        return new Board(pieces);
    }

    private static void initializeBoard(final Map<BoardPosition, Piece> pieces) {
        PieceType.CANNON.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.CANNON, team))));

        PieceType.CHARIOT.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.CHARIOT, team))));

        PieceType.ELEPHANT.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.ELEPHANT, team))));

        PieceType.GENERAL.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.GENERAL, team))));

        PieceType.GUARD.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.GUARD, team))));

        PieceType.HORSE.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.HORSE, team))));

        PieceType.쭈.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.쭈, team))));
    }
}
