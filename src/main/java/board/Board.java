package board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import movepolicy.MoveContext;
import movepolicy.destination.DestinationRule;
import movepolicy.path.PathRule;
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
        Map<Position, Piece> merged = new HashMap<>();
        pieces.forEach(merged::put);
        other.pieces.forEach(merged::put);
        return new Board(merged);
    }

    public void validateDeparturePieceSide(Position departure, Turn turn) {
        FullPiece piece = pieces.get(departure).asFullPiece();
        if (!piece.isSameSide(turn.side())) {
            throw new IllegalArgumentException("본인 진영의 기물만 이동시킬 수 있습니다.");
        }
    }

    public Board move(Position departure, Position destination) {
        FullPiece departurePiece = pieces.get(departure).asFullPiece();
        Piece destinationPiece = pieces.get(destination);
        MoveContext moveContext = departurePiece.askMoveContext(departure, destination);

        validatePathPieces(moveContext.pathPositions(), moveContext.pathRule());
        validateDestination(departurePiece, destinationPiece, moveContext.destinationRule());

        Map<Position, Piece> moved = new HashMap<>(pieces);
        moved.put(departure, EmptyPiece.getInstance());
        moved.put(destination, departurePiece);

        System.out.println(moved);
        return new Board(moved);
    }

    private void validatePathPieces(List<Position> pathPositions, PathRule pathRule) {
        List<Piece> pathPieces = getPathPieces(pathPositions);
        pathRule.validatePathPieces(pathPieces);
    }

    private List<Piece> getPathPieces(List<Position> pathPositions) {
        return pathPositions.stream()
            .map(pieces::get)
            .toList();
    }

    private void validateDestination(FullPiece departurePiece, Piece destinationPiece,
                                     DestinationRule destinationRule) {
        destinationRule.validateDestination(departurePiece, destinationPiece);
    }
}
