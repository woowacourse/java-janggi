package janggi.board;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Position;
import janggi.domain.piece.Empty;
import janggi.domain.piece.Piece;

import java.util.Map;

public class JanggiBoardFixture {

    public static JanggiBoard modifyInitialBoardWithOnePiece(final Position position, final Piece piece) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();

        Map<Position, Piece> newJanggiBoard = janggiBoard.getBoard();
        newJanggiBoard.put(position, piece);
        return new JanggiBoard(newJanggiBoard);
    }

    public static JanggiBoard modifyInitialBoardWithManyPieces(final Map<Position, Piece> piecePositions) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();

        Map<Position, Piece> newJanggiBoard = janggiBoard.getBoard();
        newJanggiBoard.putAll(piecePositions);
        return new JanggiBoard(newJanggiBoard);
    }

    public static JanggiBoard modifyEmptyBoardWithManyPieces(final Map<Position, Piece> piecePositions) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();

        Map<Position, Piece> newJanggiBoard = janggiBoard.getBoard();
        newJanggiBoard.replaceAll((k, v) -> new Empty());

        newJanggiBoard.putAll(piecePositions);
        return new JanggiBoard(newJanggiBoard);
    }
}
