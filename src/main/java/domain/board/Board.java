package domain.board;

import domain.InitialPiecesPositions;
import domain.Team;
import domain.piece.Piece;
import java.util.ArrayList;
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

        validateCatchable(selectedPiece, destinationPiece, currentTeam);
        removeDestinationEnemyPiece(destinationBoardPosition, currentTeam, destinationPiece);
        changeSelectPieceBoardPosition(selectBoardPosition, destinationBoardPosition, selectedPiece);
    }

    private void validateSelectBoardPosition(final BoardPosition selectBoardPosition) {
        if (!pieces.containsKey(selectBoardPosition)) {
            throw new IllegalArgumentException("이동하려는 기물이 없습니다.");
        }
    }

    private void validateSelectPieceTeam(final Team currentTeam, final Piece selectedPiece) {
        if (!selectedPiece.isMyTeam(currentTeam)) {
            throw new IllegalArgumentException("다른 팀의 기물을 움직일 수 없습니다.");
        }
    }

    private void validateDestinationPieceTeam(final Team currentTeam, final Piece destinationPiece) {
        if (destinationPiece != null && destinationPiece.isMyTeam(currentTeam)) {
            throw new IllegalArgumentException("이동하려는 위치에 아군 기물이 존재합니다.");
        }
    }

    private void validateMovementRule(
            final List<Offset> movementRule,
            final BoardPosition selectBoardPosition,
            final BoardPosition destinationBoardPosition,
            final Piece movePiece
    ) {
        List<BoardPosition> routePositions = calculateRoutePositions(movementRule, selectBoardPosition);
        List<Piece> obstacles = findObstacles(routePositions, destinationBoardPosition);
        if (!movePiece.isAllowedObstacles(obstacles)) {
            throw new IllegalArgumentException("이동경로에 넘을 수 없는 기물이 있습니다.");
        }
    }

    private List<Piece> findObstacles(
            final List<BoardPosition> routePositions,
            final BoardPosition destinationBoardPosition
    ) {
        return routePositions.stream()
                .filter(pieces::containsKey)
                .filter(position -> !position.equals(destinationBoardPosition))
                .map(pieces::get)
                .toList();
    }

    private List<BoardPosition> calculateRoutePositions(
            final List<Offset> movementRule,
            BoardPosition currentBoardPosition
    ) {
        List<BoardPosition> routePositions = new ArrayList<>(movementRule.size());
        for (final Offset offset : movementRule) {
            currentBoardPosition = currentBoardPosition.plus(offset);
            routePositions.add(currentBoardPosition);
        }
        return routePositions;
    }

    private void validateCatchable(
            final Piece selectPiece,
            final Piece destinationPiece,
            final Team currentTeam
    ) {
        if (destinationPiece == null || destinationPiece.isMyTeam(currentTeam)) {
            return;
        }
        if (!selectPiece.isCatchable(destinationPiece)) {
            throw new IllegalArgumentException("도착 위치에 있는 기물은 잡을 수 없는 기물입니다.");
        }
    }

    private void removeDestinationEnemyPiece(
            final BoardPosition destinationBoardPosition,
            final Team currentTeam,
            final Piece destinationPiece
    ) {
        if (destinationPiece != null && !destinationPiece.isMyTeam(currentTeam)) {
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
