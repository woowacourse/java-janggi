package janggi.fixture;

import janggi.board.Board;
import janggi.piece.Cannon;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Team;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
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
                new Position(Row.FIVE, Column.SEVEN), Horse.of(Team.HAN)
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

    public static Board generateBoardWithOnePiece(
            final Position position, final Piece piece
    ) {
        Map<Position, Piece> board = Map.of(
                position, piece
        );
        return new Board(board);
    }

    public static Board generateBoardWithTwoPiece(
            final Position position1, final Piece piece1,
            final Position position2, final Piece piece2
    ) {
        Map<Position, Piece> board = Map.of(
                position1, piece1,
                position2, piece2
        );
        return new Board(board);
    }
}
