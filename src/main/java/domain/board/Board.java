package domain.board;

import domain.piece.Piece;

import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public boolean isPieceInPosition(Position position) {
        return board.containsKey(position);
    }

    public Piece getPieceBy(Position position) {
        Piece piece = board.get(position);
        if (piece == null) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다");
        }
        return piece;
    }


    public int countPieceOnRoute(List<Position> positions) {
        int count = 0;
        for(Position position : positions) {
            if(board.get(position) != null) {
                count++;
            }
        }
        return count;
    }

    public List<Piece> getPiecesOnRoute(List<Position> positions) {
        return positions.stream()
                .map(board::get)
                .toList();
    }

    public boolean isValidRoute(Position source, Position destination) {
        Piece sourcePiece = board.get(source);
        Piece destinationPiece = board.get(destination);

        boolean validDestination = sourcePiece.isValidDestination(source, destination);

        List<Position> route = sourcePiece.findAllRoute(source, destination);
        List<Piece> piecesOnRoute = getPiecesOnRoute(route);

        boolean canMove = sourcePiece.canMove(destinationPiece, piecesOnRoute);

        return canMove && validDestination;
    }

    public void move(Piece piece, Position source, Position destination) {
        if(!isValidRoute(source, destination)) {
            return;
        }
        //블라블라
    }
}
