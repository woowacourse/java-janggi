package domain.board;

import domain.InitialPiecesPositions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.Team;
import java.util.Arrays;
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


    public void movePiece(
            final BoardPosition selectBoardPosition,
            final BoardPosition destinationBoardPosition,
            final Team currentTeam
    ) {
        validateSelectBoardPosition(selectBoardPosition);

        final Piece selectedPiece = pieces.get(selectBoardPosition);
        validateSelectPieceTeam(currentTeam, selectedPiece);

        final Piece destinationPiece = pieces.get(destinationBoardPosition);
        validateDestinationPieceTeam(currentTeam, destinationPiece);

        final List<Offset> movementRule = selectedPiece.findMovementRule(selectBoardPosition, destinationBoardPosition);
        validateMovementRule(movementRule, selectBoardPosition, destinationBoardPosition, selectedPiece);

        removeDestinationEnemyPiece(destinationBoardPosition, currentTeam, destinationPiece);
        changeSelectPieceBoardPosition(selectBoardPosition, destinationBoardPosition, selectedPiece);
    }

    private void validateSelectBoardPosition(final BoardPosition selectBoardPosition) {
        if (!pieces.containsKey(selectBoardPosition)) {
            throw new IllegalArgumentException("이동하려는 기물이 없습니다.");
        }
    }

    private void validateSelectPieceTeam(final Team currentTeam, final Piece selectedPiece) {
        if (selectedPiece.getTeam() != currentTeam) {
            throw new IllegalArgumentException("다른 팀의 기물을 움직일 수 없습니다.");
        }
    }

    private void validateDestinationPieceTeam(final Team currentTeam, final Piece destinationPiece) {
        if (destinationPiece != null && currentTeam == destinationPiece.getTeam()) {
            throw new IllegalArgumentException("이동하려는 위치에 아군 기물이 존재합니다.");
        }
    }

    private void validateMovementRule(
            final List<Offset> movementRule,
            final BoardPosition selectBoardPosition,
            final BoardPosition destinationBoardPosition,
            final Piece movePiece
    ) {
        int obstacleCount = calculateObstacleCount(movementRule, destinationBoardPosition, selectBoardPosition);
        if (!movePiece.isObstacleCountAllowed(obstacleCount)) {
            throw new IllegalArgumentException("이동경로에 적합하지 않은 장애물이 있습니다.");
        }

        if (movePiece.getPieceType() == PieceType.CANNON) {
            validateCannonMovementRule(movementRule, selectBoardPosition);
        }
    }

    private int calculateObstacleCount(
            final List<Offset> movementRule,
            final BoardPosition destinationBoardPosition,
            BoardPosition currentBoardPosition
    ) {
        int obstacleCount = 0;
        for (final Offset offset : movementRule) {
            currentBoardPosition = currentBoardPosition.calculatePosition(offset);
            if (currentBoardPosition.equals(destinationBoardPosition)) {
                break;
            }

            if (pieces.containsKey(currentBoardPosition)) {
                obstacleCount++;
            }
        }
        return obstacleCount;
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

    private void removeDestinationEnemyPiece(
            final BoardPosition destinationBoardPosition,
            final Team currentTeam,
            final Piece destinationPiece
    ) {
        if (destinationPiece != null && currentTeam != destinationPiece.getTeam()) {
            pieces.remove(destinationBoardPosition);
        }
    }

    private void changeSelectPieceBoardPosition(
            final BoardPosition selectBoardPosition,
            final BoardPosition destinationBoardPosition,
            final Piece selectedPiece
    ) {
        pieces.remove(selectBoardPosition);
        pieces.put(destinationBoardPosition, selectedPiece);
    }

    public static Board initialize() {
        final Map<BoardPosition, Piece> pieces = createInitializePieces();
        return new Board(pieces);
    }

    private static Map<BoardPosition, Piece> createInitializePieces() {
        final Map<BoardPosition, Piece> pieces = new HashMap<>();
        initializeByTeam(pieces, Team.GREEN);
        initializeByTeam(pieces, Team.RED);
        return pieces;
    }

    private static void initializeByTeam(
            final Map<BoardPosition, Piece> pieces,
            final Team team
    ) {
        for (final InitialPiecesPositions value : InitialPiecesPositions.values()) {
            value.getBoardPosition(team)
                    .forEach(position -> pieces.put(position, value.generatePiece(team)));
        }
    }

    public Map<BoardPosition, Piece> getPieces() {
        return Map.copyOf(pieces);
    }
}
