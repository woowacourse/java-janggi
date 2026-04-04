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

    public Board {
        pieces = Map.copyOf(pieces);
    }

    @Override
    public Map<Position, Piece> pieces() {
        return pieces;
    }

    public Board merge(Board other) {
        Map<Position, Piece> merged = new HashMap<>();
        merged.putAll(pieces);
        merged.putAll(other.pieces);
        return new Board(merged);
    }

    public Board move(Position departure, Position destination) {
        Piece departurePiece = pieces.get(departure);
        Piece destinationPiece = pieces.get(destination);
        MoveContext moveContext = departurePiece.askMoveContext(departure, destination);

        validatePathPieces(moveContext.pathPositions(), moveContext.pathRule());
        validateDestination(departurePiece, destinationPiece, moveContext.destinationRule());

        return new Board(movePiece(departure, destination, departurePiece));
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

    private Map<Position, Piece> movePiece(Position departure, Position destination, Piece departurePiece) {
        Map<Position, Piece> temp = new HashMap<>(pieces);
        temp.put(departure, new EmptyPiece());
        temp.put(destination, departurePiece);
        return temp;
    }
}
