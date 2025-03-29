package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Optional<Piece> findSelectedPiece(
        final BoardPosition selectBoardPosition,
        final Team currentTeam
    ) {
        return Optional.ofNullable(pieces.get(selectBoardPosition))
            .filter(piece -> piece.getTeam() == currentTeam);
    }

    public void movePiece(
        final BoardPosition selectBoardPosition,
        final BoardPosition destinationBoardPosition,
        final Team currentTeam
    ) {
        validateMoveRequest(selectBoardPosition, destinationBoardPosition, currentTeam);
        validateMovement(selectBoardPosition, destinationBoardPosition);

        handleCapture(destinationBoardPosition);
        updateBoard(selectBoardPosition, destinationBoardPosition);
    }

    private void validateMoveRequest(
        final BoardPosition selectBoardPosition,
        final BoardPosition destinationBoardPosition,
        final Team currentTeam
    ) {
        validateSelectBoardPosition(selectBoardPosition);
        final Piece selectedPiece = pieces.get(selectBoardPosition);
        validateSelectPieceTeam(currentTeam, selectedPiece);

        final Piece destinationPiece = pieces.get(destinationBoardPosition);
        validateDestinationPieceTeam(currentTeam, destinationPiece);
    }

    private void validateSelectBoardPosition(final BoardPosition selectBoardPosition) {
        if (!pieces.containsKey(selectBoardPosition)) {
            throw new IllegalArgumentException("이동하려는 기물이 없습니다.");
        }
    }

    private void validateSelectPieceTeam(
        final Team currentTeam,
        final Piece selectedPiece
    ) {
        if (!selectedPiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("다른 팀의 기물을 움직일 수 없습니다.");
        }
    }

    private void validateDestinationPieceTeam(
        final Team currentTeam,
        final Piece destinationPiece
    ) {
        if (destinationPiece != null && destinationPiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("이동하려는 위치에 아군 기물이 존재합니다.");
        }
    }

    private void validateMovement(
        final BoardPosition selectBoardPosition,
        final BoardPosition destinationBoardPosition
    ) {
        final Piece selectedPiece = pieces.get(selectBoardPosition);
        final List<Offset> movementRule = selectedPiece.findMovementRule(selectBoardPosition,
            destinationBoardPosition);

        final List<Piece> obstacles = calculateObstacles(selectBoardPosition,
            destinationBoardPosition, movementRule);
        final Piece destinationPiece = pieces.get(destinationBoardPosition);

        selectedPiece.validateMoveRule(obstacles, destinationPiece);
    }

    private List<Piece> calculateObstacles(
        final BoardPosition selectBoardPosition,
        final BoardPosition destinationBoardPosition,
        final List<Offset> movementRule
    ) {
        final List<Piece> obstacles = new ArrayList<>();
        BoardPosition currentBoardPosition = selectBoardPosition;

        for (final Offset offset : movementRule) {
            currentBoardPosition = currentBoardPosition.calculatePosition(offset);
            addIfObstacle(currentBoardPosition, destinationBoardPosition, obstacles);
        }

        return obstacles;
    }

    private void addIfObstacle(
        final BoardPosition currentBoardPosition,
        final BoardPosition destinationBoardPosition,
        final List<Piece> obstacles
    ) {
        if (!pieces.containsKey(currentBoardPosition) || currentBoardPosition.equals(
            destinationBoardPosition)) {
            return;
        }

        final Piece obstacle = pieces.get(currentBoardPosition);
        obstacles.add(obstacle);
    }

    private void handleCapture(
        final BoardPosition destinationBoardPosition
    ) {
        pieces.remove(destinationBoardPosition);
    }

    private void updateBoard(
        final BoardPosition selectBoardPosition,
        final BoardPosition destinationBoardPosition
    ) {
        final Piece selectedPiece = pieces.get(selectBoardPosition);

        pieces.remove(selectBoardPosition);
        pieces.put(destinationBoardPosition, selectedPiece);
    }

    public boolean isOnlyOneKingLeft() {
        return pieces.values()
            .stream()
            .filter(piece -> piece.isSamePieceType(PieceType.GENERAL))
            .map(Piece::getTeam)
            .distinct()
            .count() == 1;
    }

    public Map<BoardPosition, Piece> getPieces() {
        return Map.copyOf(pieces);
    }
}
