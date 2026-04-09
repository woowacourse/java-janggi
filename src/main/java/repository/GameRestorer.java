package repository;

import model.board.Board;
import model.pieces.Piece;
import model.position.Position;

public class GameRestorer {
    private GameRestorer() {
    }

    public static Board restoreBoard(SavedGame savedGame) {
        Board board = new Board();
        for (SavedPiece savedPiece : savedGame.pieces()) {
            Position position = Position.of(savedPiece.row(), savedPiece.col());
            Piece piece = new Piece(savedPiece.country(), savedPiece.pieceType());

            board.place(position, piece);
        }
        return board;
    }
}
