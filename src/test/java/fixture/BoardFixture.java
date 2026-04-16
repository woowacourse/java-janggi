package fixture;

import constant.BoardSpec;
import domain.board.Board;
import domain.piece.Empty;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.vo.Position;
import java.util.HashMap;
import java.util.Map;

public final class BoardFixture {

    private BoardFixture() {
    }

    public static Board createGameEndBoard() {
        Map<Position, Piece> board = createEmptyBoard();
        board.put(Position.of(5, 9), PieceType.KING.create(Side.CHO));
        board.put(Position.of(5, 6), PieceType.KING.create(Side.HAN));
        board.put(Position.of(5, 7), PieceType.SOLDIER.create(Side.CHO));
        return new Board(board);
    }

    private static Map<Position, Piece> createEmptyBoard() {
        Map<Position, Piece> board = new HashMap<>();
        for (int x = BoardSpec.MIN_X; x <= BoardSpec.MAX_X; x++) {
            for (int y = BoardSpec.MIN_Y; y <= BoardSpec.MAX_Y; y++) {
                board.put(Position.of(x, y), new Empty());
            }
        }
        return board;
    }
}
