package domain.board;

import domain.piece.EmptyPiece;
import domain.piece.Piece;

import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Piece getPieceBy(Position position) {
        return board.getOrDefault(position, new EmptyPiece());
    }

    public void move(Piece piece, Position source, Position destination) {
        if(!isValidMove(source, destination)) {
            return;
        }
        board.remove(source);
        board.put(destination, piece);
    }

    private boolean isValidMove(Position source, Position destination) {
        Piece sourcePiece = getPieceBy(source);
        Piece destinationPiece = getPieceBy(destination);

        boolean validDestination = sourcePiece.isValidDestination(source, destination);

        List<Position> route = sourcePiece.findAllRoute(source, destination);
        List<Piece> piecesOnRoute = getPiecesOnRoute(route);

        boolean canMove = sourcePiece.canMove(destinationPiece, piecesOnRoute);

        return canMove && validDestination;
    }

    private List<Piece> getPiecesOnRoute(List<Position> positions) {
        return positions.stream()
                .map(this::getPieceBy)
                .toList();
    }
}
