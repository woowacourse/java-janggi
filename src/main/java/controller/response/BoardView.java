package controller.response;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record BoardView(
        int minRow,
        int maxRow,
        int minCol,
        int maxCol,
        Map<Position, Piece> pieces
) {

    public static BoardView from(Board board) {
        return new BoardView(
                board.getMinRowRange(),
                board.getMaxRowRange(),
                board.getMinColumnRange(),
                board.getMaxColumnRange(),
                extractPieces(board)
        );
    }

    private static Map<Position, Piece> extractPieces(Board board) {
        Map<Position, Piece> map = new HashMap<>();

        for (int row = board.getMinRowRange(); row <= board.getMaxRowRange(); row++) {
            for (int col = board.getMinColumnRange(); col <= board.getMaxColumnRange(); col++) {
                Position pos = Position.of(row, col);

                if (board.hasPiece(pos)) {
                    map.put(pos, board.getPiece(pos));
                }
            }
        }

        return map;
    }

    public Optional<Piece> findPiece(Position pos) {
        return Optional.ofNullable(pieces.get(pos));
    }
}
