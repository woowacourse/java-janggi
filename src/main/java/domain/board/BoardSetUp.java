package domain.board;

import domain.piece.Advisor;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.General;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.side.Side;
import java.util.HashMap;
import java.util.Map;

public interface BoardSetUp {
    Map<Point, Piece> generate();

    default Map<Point, Piece> commonBoard() {
        Map<Point, Piece> board = new HashMap<>();

        board.put(new Point(0, 0), new Chariot(Side.CHO));
        board.put(new Point(3, 0), new Advisor(Side.CHO));
        board.put(new Point(5, 0), new Advisor(Side.CHO));
        board.put(new Point(8, 0), new Chariot(Side.CHO));

        board.put(new Point(4, 1), new General(Side.CHO));

        board.put(new Point(1, 2), new Cannon(Side.CHO));
        board.put(new Point(7, 2), new Cannon(Side.CHO));

        board.put(new Point(0, 3), new Soldier(Side.CHO));
        board.put(new Point(2, 3), new Soldier(Side.CHO));
        board.put(new Point(4, 3), new Soldier(Side.CHO));
        board.put(new Point(6, 3), new Soldier(Side.CHO));
        board.put(new Point(8, 3), new Soldier(Side.CHO));

        board.put(new Point(0, 6), new Soldier(Side.HAN));
        board.put(new Point(2, 6), new Soldier(Side.HAN));
        board.put(new Point(4, 6), new Soldier(Side.HAN));
        board.put(new Point(6, 6), new Soldier(Side.HAN));
        board.put(new Point(8, 6), new Soldier(Side.HAN));

        board.put(new Point(1, 7), new Cannon(Side.HAN));
        board.put(new Point(7, 7), new Cannon(Side.HAN));

        board.put(new Point(4, 8), new General(Side.HAN));

        board.put(new Point(0, 9), new Chariot(Side.HAN));
        board.put(new Point(3, 9), new Advisor(Side.HAN));
        board.put(new Point(5, 9), new Advisor(Side.HAN));
        board.put(new Point(8, 9), new Chariot(Side.HAN));

        return board;
    }
}
