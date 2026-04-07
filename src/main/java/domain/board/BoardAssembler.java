package domain.board;

import domain.piece.Piece;
import domain.piece.PieceSnapshot;
import domain.position.Position;

import java.util.List;

public class BoardAssembler {
    public static Board assemble(List<PieceSnapshot> pieces) {
        Board board = new Board();

        for (PieceSnapshot pieceSnapShot : pieces) {
            Position position = Position.of(pieceSnapShot.getPositionX(), pieceSnapShot.getPositionY());
            Piece piece = Piece.of(pieceSnapShot.getSide(), pieceSnapShot.getPieceType());
            board.put(position, piece);
        }
        return board;
    }
}
