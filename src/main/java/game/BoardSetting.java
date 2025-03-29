package game;

import java.util.HashMap;
import java.util.Map;
import piece.Cannon;
import piece.Country;
import piece.Elephant;
import piece.General;
import piece.Guard;
import piece.Horse;
import piece.Piece;
import piece.Rook;
import piece.Soldier;
import position.Column;
import position.Position;
import position.Row;

public class BoardSetting {


    public Map<Position, Piece> setting(Country country, StartSet startSet) {
        Map<Position, Piece> board = new HashMap<>();
        board.put(reverseByCountry(new Position(Column.A, Row.ONE), country), new Rook(country));
        board.put(reverseByCountry(new Position(Column.D, Row.ONE), country), new Guard(country));
        board.put(reverseByCountry(new Position(Column.E, Row.TWO), country), new General(country));
        board.put(reverseByCountry(new Position(Column.F, Row.ONE), country), new Guard(country));
        board.put(reverseByCountry(new Position(Column.I, Row.ONE), country), new Rook(country));

        board.put(reverseByCountry(new Position(Column.B, Row.THREE), country), new Cannon(country));
        board.put(reverseByCountry(new Position(Column.H, Row.THREE), country), new Cannon(country));

        board.put(reverseByCountry(new Position(Column.A, Row.FOUR), country), new Soldier(country));
        board.put(reverseByCountry(new Position(Column.C, Row.FOUR), country), new Soldier(country));
        board.put(reverseByCountry(new Position(Column.E, Row.FOUR), country), new Soldier(country));
        board.put(reverseByCountry(new Position(Column.G, Row.FOUR), country), new Soldier(country));
        board.put(reverseByCountry(new Position(Column.I, Row.FOUR), country), new Soldier(country));

        switch (startSet) {
            case MA_SANG_MA_SANG -> MaSangMaSangPosition(board, country);
            case MA_SANG_SANG_MA -> MaSangSangMaPosition(board, country);
            case SANG_MA_MA_SANG -> SangMaMaSangPosition(board, country);
            case SANG_MA_SANG_MA -> SangMaSangMaPosition(board, country);
        }

        return board;
    }

    private void MaSangMaSangPosition(Map<Position, Piece> board, Country country) {
        board.put(reverseByCountry(new Position(Column.B, Row.ONE), country), new Horse(country));
        board.put(reverseByCountry(new Position(Column.C, Row.ONE), country), new Elephant(country));
        board.put(reverseByCountry(new Position(Column.G, Row.ONE), country), new Horse(country));
        board.put(reverseByCountry(new Position(Column.H, Row.ONE), country), new Elephant(country));
    }

    private void MaSangSangMaPosition(Map<Position, Piece> board, Country country) {
        board.put(reverseByCountry(new Position(Column.B, Row.ONE), country), new Horse(country));
        board.put(reverseByCountry(new Position(Column.C, Row.ONE), country), new Elephant(country));
        board.put(reverseByCountry(new Position(Column.G, Row.ONE), country), new Elephant(country));
        board.put(reverseByCountry(new Position(Column.H, Row.ONE), country), new Horse(country));
    }

    private void SangMaMaSangPosition(Map<Position, Piece> board, Country country) {
        board.put(reverseByCountry(new Position(Column.B, Row.ONE), country), new Elephant(country));
        board.put(reverseByCountry(new Position(Column.C, Row.ONE), country), new Horse(country));
        board.put(reverseByCountry(new Position(Column.G, Row.ONE), country), new Horse(country));
        board.put(reverseByCountry(new Position(Column.H, Row.ONE), country), new Elephant(country));
    }

    private void SangMaSangMaPosition(Map<Position, Piece> board, Country country) {
        board.put(reverseByCountry(new Position(Column.B, Row.ONE), country), new Elephant(country));
        board.put(reverseByCountry(new Position(Column.C, Row.ONE), country), new Horse(country));
        board.put(reverseByCountry(new Position(Column.G, Row.ONE), country), new Elephant(country));
        board.put(reverseByCountry(new Position(Column.H, Row.ONE), country), new Horse(country));
    }


    private Position reverseByCountry(Position position, Country country) {
        return country == Country.CHO ? position : position.reverse();
    }
}

