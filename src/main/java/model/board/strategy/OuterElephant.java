package model.board.strategy;

import model.board.Board;
import model.board.Country;
import model.board.HorseElephantStrategy;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Column;
import model.position.Position;
import model.position.Row;

public class OuterElephant implements HorseElephantStrategy {
    @Override
    public void deploy(Board board, Country country) {
        board.place(new Position(Row.edgePiece(country), Column.from(COLUMN_TWO)), new Piece(country, PieceType.ELEPHANT));
        board.place(new Position(Row.edgePiece(country), Column.from(COLUMN_THREE)), new Piece(country, PieceType.HORSE));
        board.place(new Position(Row.edgePiece(country), Column.from(COLUMN_SEVEN)), new Piece(country, PieceType.HORSE));
        board.place(new Position(Row.edgePiece(country), Column.from(COLUMN_EIGHT)), new Piece(country, PieceType.ELEPHANT));
    }
}
