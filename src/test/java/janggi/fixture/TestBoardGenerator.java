package janggi.fixture;

import janggi.Board;
import janggi.Column;
import janggi.Position;
import janggi.Row;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Team;
import java.util.Map;

public class TestBoardGenerator {
    public static Board generateSoldierCanNotMove() {
        Map<Position, Piece> board = Map.of(
                new Position(Row.SIX, Column.SEVEN), Soldier.of(Team.CHO)
        );
        return new Board(board);
    }

    public static Board generateSoldierCatch() {
        Map<Position, Piece> board = Map.of(
                new Position(Row.SIX, Column.SEVEN), Soldier.of(Team.CHO),
                new Position(Row.FIVE, Column.SEVEN), Soldier.of(Team.HAN)
        );
        return new Board(board);
    }
}
