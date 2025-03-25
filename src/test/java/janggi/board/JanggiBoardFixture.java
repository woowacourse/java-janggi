package janggi.board;

import janggi.piece.Piece;

import java.util.Map;

public class JanggiBoardFixture {

    public static JanggiBoard setUpTestBoard(final Position position, final Piece piece) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();

        Map<Position, Piece> newJanggiBoard = janggiBoard.getBoard();
        newJanggiBoard.put(position, piece);
        return new JanggiBoard(newJanggiBoard);
    }
}
