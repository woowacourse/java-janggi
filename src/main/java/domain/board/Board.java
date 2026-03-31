package domain.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import domain.movepolicy.MoveContext;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.path.PathRule;
import domain.pieces.EmptyPiece;
import domain.pieces.Piece;
import domain.position.Position;

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

    public Board move(Position departure, Position destination) {
        Piece departurePiece = pieces.get(departure);
        Piece destinationPiece = pieces.get(destination);
        MoveContext moveContext = departurePiece.askMoveContext(departure, destination);

        validatePathPieces(moveContext.pathPositions(), moveContext.pathRule());
        validateDestination(departurePiece, destinationPiece, moveContext.destinationRule());
        movePiece(departure, destination, departurePiece);

        return new Board(pieces);
    }

    private void validatePathPieces(List<Position> pathPositions, PathRule pathRule) {
        List<Piece> pathPieces = getPathPieces(pathPositions);
        pathRule.validatePathPieces(pathPieces);
    }

    private void validateDestination(Piece departurePiece, Piece destinationPiece,
                                     DestinationRule destinationRule) {
        destinationRule.validateDestination(departurePiece, destinationPiece);
    }

    private List<Piece> getPathPieces(List<Position> pathPositions) {
        return pathPositions.stream()
                .map(pieces::get)
                .toList();
    }

    private void movePiece(Position departure, Position destination, Piece departurePiece) {
        pieces.put(departure, new EmptyPiece());
        pieces.put(destination, departurePiece);
    }
}
