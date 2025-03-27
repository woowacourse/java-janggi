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

    public static JanggiBoard setUpTestBoardWithPieces(final Map<Position, Piece> piecePositions) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();

        Map<Position, Piece> newJanggiBoard = janggiBoard.getBoard();
        newJanggiBoard.putAll(piecePositions);
        return new JanggiBoard(newJanggiBoard);
    }
}
