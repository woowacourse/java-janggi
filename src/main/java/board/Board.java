package board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import movepolicy.rule.MoveRule;
import movepolicy.rule.MoveTrace;
import participant.Turn;
import pieces.Piece;
import position.Position;

public record Board(Map<Position, Piece> pieces) {

    @Override
    public Map<Position, Piece> pieces() {
        return Map.copyOf(pieces);
    }

    public Board merge(Board other) {
        Map<Position, Piece> merged = new HashMap<>(pieces);
        other.pieces
            .forEach((position, piece) -> put(position, piece, merged));
        return new Board(merged);
    }

    private void put(Position position, Piece piece, Map<Position, Piece> merged) {
        if (merged.containsKey(position)) {
            throw new IllegalArgumentException("이미 기물이 존재하는 위치입니다.");
        }
        merged.put(position, piece);
    }

    public void validateDeparturePiece(Position departure, Turn turn) {
        Piece movingPiece = requirePieceAt(departure);
        if (!movingPiece.isSameSide(turn.side())) {
            throw new IllegalArgumentException("본인 진영의 기물만 이동시킬 수 있습니다.");
        }
    }

    public Board move(Position departure, Position destination) {
        Piece movingPiece = requirePieceAt(departure);
        MoveTrace moveTrace = createMovePath(movingPiece, departure, destination);

        MoveRule moveRule = movingPiece.getMoveRule();
        moveRule.validate(moveTrace);

        return replace(departure, destination, movingPiece);
    }

    private MoveTrace createMovePath(Piece movingPiece, Position departure, Position destination) {
        List<Position> pathPositions = movingPiece.findPathPositions(departure, destination);
        List<Piece> pathPieces = findPathPieces(pathPositions);
        Optional<Piece> targetPiece = pieceAt(destination);

        return new MoveTrace(movingPiece, pathPieces, targetPiece);
    }

    private Piece requirePieceAt(Position position) {
        return pieceAt(position)
            .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
    }

    private List<Piece> findPathPieces(List<Position> pathPieces) {
        return pathPieces.stream()
            .map(this::pieceAt)
            .flatMap(Optional::stream)
            .toList();
    }

    private Optional<Piece> pieceAt(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    private Board replace(Position departure, Position destination, Piece movingPiece) {
        Map<Position, Piece> moved = new HashMap<>(pieces);
        moved.remove(departure);
        moved.put(destination, movingPiece);
        return new Board(moved);
    }
}