package board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import movepolicy.rule.MovePath;
import movepolicy.rule.MoveRule;
import participant.Turn;
import pieces.EmptyPiece;
import pieces.FullPiece;
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
        if (isDuplicatedFullPiece(position, piece, merged)) {
            throw new IllegalArgumentException("이미 기물이 존재하는 위치입니다.");
        }
        merged.put(position, piece);
    }

    private static boolean isDuplicatedFullPiece(Position position, Piece piece,
                                                 Map<Position, Piece> merged) {
        return !piece.isEmpty() && merged.containsKey(position);
    }

    public void validateDeparturePiece(Position departure, Turn turn) {
        FullPiece piece = fullPieceAt(departure);
        if (!piece.isSameSide(turn.side())) {
            throw new IllegalArgumentException("본인 진영의 기물만 이동시킬 수 있습니다.");
        }
    }

    public Board move(Position departure, Position destination) {
        FullPiece movingPiece = fullPieceAt(departure);
        MovePath movePath = createMovePath(movingPiece, departure, destination);

        MoveRule moveRule = movingPiece.getMoveRule();
        moveRule.validatePathPieces(movePath);

        return replace(departure, destination, movingPiece);
    }

    private MovePath createMovePath(FullPiece movingPiece, Position departure,
                                    Position destination) {
        List<Position> interveningPositions = movingPiece
            .getInterveningPositions(departure, destination);
        List<Piece> interveningPieces = findInterveningPieces(interveningPositions);
        Piece targetPiece = pieceAt(destination);

        return new MovePath(movingPiece, interveningPieces, targetPiece);
    }

    private List<Piece> findInterveningPieces(List<Position> interveningPositions) {
        return interveningPositions.stream()
            .map(this::pieceAt)
            .toList();
    }

    private FullPiece fullPieceAt(Position position) {
        Piece piece = pieceAt(position);
        if (piece.isEmpty()) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
        }
        return piece.asFullPiece();
    }

    private Piece pieceAt(Position position) {
        return pieces.get(position);
    }

    private Board replace(Position departure, Position destination, FullPiece movingPiece) {
        Map<Position, Piece> moved = new HashMap<>(pieces);
        moved.put(departure, EmptyPiece.getInstance());
        moved.put(destination, movingPiece);
        return new Board(moved);
    }
}