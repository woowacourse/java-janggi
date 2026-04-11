package model.board;

import java.util.List;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Column;
import model.position.Position;
import model.position.Row;

public class Army {
    private static final List<Integer> SOLDIER_COLUMN = List.of(1, 3, 5, 7, 9);
    private static final List<Integer> CANNON_COLUMN = List.of(2, 8);
    private static final List<Integer> GUARD_COLUMN = List.of(4, 6);
    private static final List<Integer> CHARIOT_COLUMN = List.of(1, 9);
    private static final int GENERAL_COLUMN = 5;

    private final HorseElephantStrategy strategy;

    public Army(HorseElephantStrategy strategy) {
        this.strategy = strategy;
    }

    public void deployTo(Board board, Country country) {
        deploySoldier(board, country);
        deployCannon(board, country);
        deployGeneral(board, country);
        deployGuard(board, country);
        deployChariot(board, country);
        deployHorseElephant(board, country);
    }

    private void deploySoldier(Board board, Country country) {
        for (int column : SOLDIER_COLUMN) {
            board.place(new Position(Row.soldier(country), Column.from(column)), new Piece(country, PieceType.SOLDIER));
        }
    }

    private void deployCannon(Board board, Country country) {
        for (int column : CANNON_COLUMN) {
            board.place(new Position(Row.cannon(country), Column.from(column)), new Piece(country, PieceType.CANNON));
        }
    }

    private void deployGeneral(Board board, Country country) {
        board.place(new Position(Row.general(country), Column.from(GENERAL_COLUMN)), new Piece(country, PieceType.GENERAL));
    }

    private void deployGuard(Board board, Country country) {
        for (int column : GUARD_COLUMN) {
            board.place(new Position(Row.edgePiece(country), Column.from(column)), new Piece(country, PieceType.GUARD));
        }
    }

    private void deployChariot(Board board, Country country) {
        for (int column : CHARIOT_COLUMN) {
            board.place(new Position(Row.edgePiece(country), Column.from(column)), new Piece(country, PieceType.CHARIOT));
        }
    }

    private void deployHorseElephant(Board board, Country country) {
        strategy.deploy(board, country);
    }
}
