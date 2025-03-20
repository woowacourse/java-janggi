package janggi.fixture;

import janggi.board.Board;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.piece.Cannon;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Team;
import java.util.Map;

public class TestBoardGenerator {
    public static Board generateSoldierCannotMove() {
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

    public static Board generateHorseCannotMove() {
        Map<Position, Piece> board = Map.of(
                new Position(Row.SIX, Column.SEVEN), Soldier.of(Team.CHO)
        );
        return new Board(board);
    }

    public static Board generateElephantCannotMove() {
        Map<Position, Piece> board = Map.of(
                new Position(Row.FIVE, Column.SIX), Soldier.of(Team.CHO)
        );
        return new Board(board);
    }

    public static Board generateCannon() {
        Map<Position, Piece> board = Map.of(
                new Position(Row.SEVEN, Column.ZERO), Cannon.of(Team.CHO)
        );
        return new Board(board);
    }

    public static Board generateEmpty() {
        return new Board(Map.of());
    }

    public static Board generateNotCannon() {
        Map<Position, Piece> board = Map.of(
                new Position(Row.SEVEN, Column.ZERO), Soldier.of(Team.CHO)
        );
        return new Board(board);
    }
}
