package domain.board;

import domain.coordination.Column;
import domain.coordination.Coordination;
import domain.coordination.Row;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.Team;
import dto.BoardRowDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {

    public static Board create(String choOption, String hanOption) {
        Map<Coordination, Piece> board = new HashMap<>();

        placeEmpty(board);
        board.putAll(PlacementOption.hanFrom(hanOption).place());
        board.putAll(PlacementOption.choFrom(choOption).place());

        return new Board(board);
    }

    public static Board from(List<BoardRowDetail> boardRowDetails) {
        Map<Coordination, Piece> board = new HashMap<>();
        placeEmpty(board);
        for (BoardRowDetail dto : boardRowDetails) {
            Coordination coordination = Coordination.of(dto.column(), dto.row());
            Piece piece = PieceFactory.create(dto.pieceType(), dto.team());
            board.put(coordination, piece);
        }
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
