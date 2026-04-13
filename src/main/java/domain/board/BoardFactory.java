package domain.board;

import domain.coordination.Column;
import domain.coordination.Coordination;
import domain.coordination.Row;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public static Board initialize(String choOption, String hanOption) {
        Map<Coordination, Piece> board = new HashMap<>();

        placeEmpty(board);
        board.putAll(PlacementOption.hanFrom(hanOption).place());
        board.putAll(PlacementOption.choFrom(choOption).place());

        return new Board(board);
    }

    public static Board restore(Map<Coordination, Piece> existedBoard) {
        Map<Coordination, Piece> board = new HashMap<>();
        placeEmpty(board);
        board.putAll(existedBoard);
        return new Board(board);
    }

    private static void placeEmpty(Map<Coordination, Piece> map) {
        for (int row = Row.MIN; row <= Row.MAX; row++) {
            placeColumns(map, row);
        }
    }

    private static void placeColumns(Map<Coordination, Piece> map, int row) {
        for (int col = Column.MIN; col <= Column.MAX; col++) {
            map.put(Coordination.of(col, row), new EmptyPiece(Team.NONE));
        }
    }
}
