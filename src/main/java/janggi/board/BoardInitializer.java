package janggi.board;

import static janggi.PieceType.CANON;
import static janggi.PieceType.CHARIOT;
import static janggi.PieceType.ELEPHANT;
import static janggi.PieceType.GENERAL;
import static janggi.PieceType.GUARD;
import static janggi.PieceType.HORSE;
import static janggi.PieceType.SOLDIER;

import janggi.Team;
import janggi.board.position.Column;
import janggi.board.position.Position;
import janggi.board.position.Row;
import janggi.piece.Piece;
import janggi.piece.PieceCreator;
import java.util.HashMap;
import java.util.Map;

public class BoardInitializer {
    public Board makeBoard() {
        Map<Position, Piece> board = new HashMap<>();
        initializeRedTeam(board);
        initializeGreenTeam(board);
        return new Board(board);
    }

    private static void initializeRedTeam(Map<Position, Piece> board) {
        Team team = Team.RED;
        board.put(new Position(Column.from(0), Row.from(6)), PieceCreator.create(team, SOLDIER));
        board.put(new Position(Column.from(2), Row.from(6)), PieceCreator.create(team, SOLDIER));
        board.put(new Position(Column.from(4), Row.from(6)), PieceCreator.create(team, SOLDIER));
        board.put(new Position(Column.from(6), Row.from(6)), PieceCreator.create(team, SOLDIER));
        board.put(new Position(Column.from(8), Row.from(6)), PieceCreator.create(team, SOLDIER));

        board.put(new Position(Column.from(1), Row.from(7)), PieceCreator.create(team, CANON));
        board.put(new Position(Column.from(7), Row.from(7)), PieceCreator.create(team, CANON));

        board.put(new Position(Column.from(4), Row.from(8)), PieceCreator.create(team, GENERAL));

        board.put(new Position(Column.from(0), Row.from(9)), PieceCreator.create(team, CHARIOT));
        board.put(new Position(Column.from(8), Row.from(9)), PieceCreator.create(team, CHARIOT));

        board.put(new Position(Column.from(1), Row.from(9)), PieceCreator.create(team, ELEPHANT));
        board.put(new Position(Column.from(6), Row.from(9)), PieceCreator.create(team, ELEPHANT));

        board.put(new Position(Column.from(2), Row.from(9)), PieceCreator.create(team, HORSE));
        board.put(new Position(Column.from(7), Row.from(9)), PieceCreator.create(team, HORSE));

        board.put(new Position(Column.from(3), Row.from(9)), PieceCreator.create(team, GUARD));
        board.put(new Position(Column.from(5), Row.from(9)), PieceCreator.create(team, GUARD));
    }

    private static void initializeGreenTeam(Map<Position, Piece> board) {
        Team team = Team.GREEN;
        board.put(new Position(Column.from(0), Row.from(3)), PieceCreator.create(team, SOLDIER));
        board.put(new Position(Column.from(2), Row.from(3)), PieceCreator.create(team, SOLDIER));
        board.put(new Position(Column.from(4), Row.from(3)), PieceCreator.create(team, SOLDIER));
        board.put(new Position(Column.from(6), Row.from(3)), PieceCreator.create(team, SOLDIER));
        board.put(new Position(Column.from(8), Row.from(3)), PieceCreator.create(team, SOLDIER));

        board.put(new Position(Column.from(1), Row.from(2)), PieceCreator.create(team, CANON));
        board.put(new Position(Column.from(7), Row.from(2)), PieceCreator.create(team, CANON));

        board.put(new Position(Column.from(4), Row.from(1)), PieceCreator.create(team, GENERAL));

        board.put(new Position(Column.from(0), Row.from(0)), PieceCreator.create(team, CHARIOT));
        board.put(new Position(Column.from(8), Row.from(0)), PieceCreator.create(team, CHARIOT));

        board.put(new Position(Column.from(1), Row.from(0)), PieceCreator.create(team, ELEPHANT));
        board.put(new Position(Column.from(6), Row.from(0)), PieceCreator.create(team, ELEPHANT));

        board.put(new Position(Column.from(2), Row.from(0)), PieceCreator.create(team, HORSE));
        board.put(new Position(Column.from(7), Row.from(0)), PieceCreator.create(team, HORSE));

        board.put(new Position(Column.from(3), Row.from(0)), PieceCreator.create(team, GUARD));
        board.put(new Position(Column.from(5), Row.from(0)), PieceCreator.create(team, GUARD));
    }
}
