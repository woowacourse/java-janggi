package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<BoardPosition, Piece> pieces;

    public Board(final Map<BoardPosition, Piece> pieces) {
        validateNotNull(pieces);
        this.pieces = new HashMap<>(pieces);
    }

    private void validateNotNull(final Map<BoardPosition, Piece> pieces) {
        if (pieces == null) {
            throw new IllegalArgumentException("보드는 기물들을 가져야합니다.");
        }
    }

    public static Board initialize() {
        final Map<BoardPosition, Piece> pieces = createInitializePieces();
        return new Board(pieces);
    }

    private static Map<BoardPosition, Piece> createInitializePieces() {
        final Map<BoardPosition, Piece> pieces = new HashMap<>();

        PieceType.CANNON.getInitialPosition()
                .forEach((team, positions) -> positions
                        .forEach(position -> pieces.put(position, new Piece(PieceType.CANNON, team))));
        PieceType.CHARIOT.getInitialPosition()
                .forEach((team, positions) -> positions
                        .forEach(position -> pieces.put(position, new Piece(PieceType.CHARIOT, team))));
        PieceType.ELEPHANT.getInitialPosition()
                .forEach((team, positions) -> positions
                        .forEach(position -> pieces.put(position, new Piece(PieceType.ELEPHANT, team))));
        PieceType.GENERAL.getInitialPosition()
                .forEach((team, positions) -> positions
                        .forEach(position -> pieces.put(position, new Piece(PieceType.GENERAL, team))));
        PieceType.GUARD.getInitialPosition()
                .forEach((team, positions) -> positions
                        .forEach(position -> pieces.put(position, new Piece(PieceType.GUARD, team))));
        PieceType.HORSE.getInitialPosition()
                .forEach((team, positions) -> positions
                        .forEach(position -> pieces.put(position, new Piece(PieceType.HORSE, team))));
        PieceType.쭈.getInitialPosition()
                .forEach((team, positions) -> positions
                        .forEach(position -> pieces.put(position, new Piece(PieceType.쭈, team))));

        return pieces;
    }

    public void movePiece(
            final BoardPosition selectBoardPosition,
            final BoardPosition destinationBoardPosition,
            final Team currentTeam
    ) {
        if (!pieces.containsKey(selectBoardPosition)) {
            throw new IllegalArgumentException("이동하려는 위치에 기물이 없습니다.");
        }

        final Piece selectedPiece = pieces.get(selectBoardPosition);
        if (selectedPiece.getTeam() != currentTeam) {
            throw new IllegalArgumentException("다른 팀의 기물을 움직일 수 없습니다.");
        }

        final Piece destinationPiece = pieces.get(destinationBoardPosition);
        if (destinationPiece != null && currentTeam == destinationPiece.getTeam()) {
            throw new IllegalArgumentException("이동하려는 위치에 아군 기물이 존재합니다.");
        }

        final List<Offset> movementRule = selectedPiece.findMovementRule(selectBoardPosition, destinationBoardPosition);
        validateMovementRule(movementRule, selectBoardPosition, destinationBoardPosition, selectedPiece);

        if (destinationPiece != null && currentTeam != destinationPiece.getTeam()) {
            pieces.remove(destinationBoardPosition);
        }

        pieces.remove(selectBoardPosition);
        pieces.put(destinationBoardPosition, selectedPiece);
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
            currentBoardPosition = currentBoardPosition.calculatePosition(offset);
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
            currentBoardPosition = currentBoardPosition.calculatePosition(offset);

            if (pieces.containsKey(currentBoardPosition)
                    && pieces.get(currentBoardPosition).getPieceType() == PieceType.CANNON) {
                throw new IllegalArgumentException("포는 포를 넘거나 잡을 수 없습니다.");
            }
        }
    }

    public Map<BoardPosition, Piece> getPieces() {
        return Map.copyOf(pieces);
    }
}
