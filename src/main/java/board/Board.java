package board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import movepolicy.rule.MoveTrace;
import participant.Turn;
import pieces.Piece;
import pieces.Side;
import position.Position;

public record Board(Map<Position, Piece> pieces) {

    @Override
    public Map<Position, Piece> pieces() {
        return Map.copyOf(pieces);
    }

    public boolean hasGung(Side side) {
        return pieces.values().stream()
            .anyMatch(piece -> piece.isGung() && piece.isSameSide(side));
    }

    public void validateDeparturePiece(Position departure, Turn turn) {
        Piece movingPiece = requirePieceAt(departure);
        if (!movingPiece.isSameSide(turn.getSide())) {
            throw new IllegalArgumentException("본인 진영의 기물만 이동시킬 수 있습니다.");
        }
    }

    public Board move(Position departure, Position destination) {
        Piece movingPiece = requirePieceAt(departure);
        MoveTrace moveTrace = findMoveTrace(movingPiece, departure, destination);

        movingPiece.validate(moveTrace);

        return replace(departure, destination, movingPiece);
    }

    private MoveTrace findMoveTrace(Piece movingPiece, Position departure, Position destination) {
        List<Position> pathPositions = movingPiece.findPathPositions(departure, destination);
        List<Piece> pathPieces = findPathPieces(pathPositions);
        Piece targetPiece = pieceAt(destination);

        return new MoveTrace(movingPiece, pathPieces, targetPiece);
    }

    private Piece pieceAt(Position position) {
        return pieces.get(position);
    }

    private Piece requirePieceAt(Position position) {
        Piece piece = pieceAt(position);
        if (piece == null) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
        }
        return piece;
    }

    private List<Piece> findPathPieces(List<Position> pathPieces) {
        return pathPieces.stream()
            .map(this::pieceAt)
            .filter(Objects::nonNull)
            .toList();
    }

    private Board replace(Position departure, Position destination, Piece movingPiece) {
        Map<Position, Piece> moved = new HashMap<>(pieces);
        moved.remove(departure);
        moved.put(destination, movingPiece);
        return new Board(moved);
    }
}