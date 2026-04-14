package controller.response;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record BoardViewResponse(
        int minRow,
        int maxRow,
        int minCol,
        int maxCol,
        Map<Position, Piece> pieces
) {

    public static BoardViewResponse from(final Board board) {
        return new BoardViewResponse(
                board.getMinRowRange(),
                board.getMaxRowRange(),
                board.getMinColumnRange(),
                board.getMaxColumnRange(),
                extractPieces(board)
        );
    }

    private static Map<Position, Piece> extractPieces(final Board board) {
        final Map<Position, Piece> map = new HashMap<>();

        for (int row = board.getMinRowRange(); row <= board.getMaxRowRange(); row++) {
            for (int col = board.getMinColumnRange(); col <= board.getMaxColumnRange(); col++) {
                final Position pos = Position.of(row, col);

                if (board.hasPiece(pos)) {
                    map.put(pos, board.getPiece(pos));
                }
            }
        }

        return map;
    }

    public Optional<Piece> findPiece(final Position pos) {
        return Optional.ofNullable(pieces.get(pos));
    }
}
