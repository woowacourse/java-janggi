package domain.board;

import domain.piece.Empty;
import domain.piece.Piece;

import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Piece getPieceBy(Position position) {
        return board.getOrDefault(position, new Empty());
    }

    public void move(Piece selectedPiece,  Position source, Position destination) {
        validateMove(source, destination);
        Piece piece = getPieceBy(source);

        board.remove(source);
        board.put(destination, piece);
    }

    private void validateMove(Position source, Position destination) {
        Piece sourcePiece = getPieceBy(source);
        Piece destinationPiece = getPieceBy(destination);
        boolean isValidDestination = sourcePiece.isValidDestination(source, destination);

        List<Position> route = sourcePiece.findAllRoute(source, destination);
        List<Piece> piecesOnRoute = getPiecesOnRoute(route);
        boolean canMove = sourcePiece.canMove(destinationPiece, piecesOnRoute);

        if(!isValidDestination || !canMove) {
            throw new IllegalArgumentException("해당 위치로는 이동할 수 없습니다.");
        }
    }

    private List<Piece> getPiecesOnRoute(List<Position> positions) {
        return positions.stream()
                .map(this::getPieceBy)
                .toList();
    }
}
