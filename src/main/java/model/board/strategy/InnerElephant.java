package model.board.strategy;

import model.board.Board;
import model.board.Country;
import model.board.HorseElephantStrategy;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;
import model.position.Row;

public class InnerElephant implements HorseElephantStrategy {
    @Override
    public void deploy(Board board, Country country) {
        board.place(Position.of(Row.edgePiece(country), COLUMN_TWO), new Piece(country, PieceType.HORSE));
        board.place(Position.of(Row.edgePiece(country), COLUMN_THREE), new Piece(country, PieceType.ELEPHANT));
        board.place(Position.of(Row.edgePiece(country), COLUMN_SEVEN), new Piece(country, PieceType.ELEPHANT));
        board.place(Position.of(Row.edgePiece(country), COLUMN_EIGHT), new Piece(country, PieceType.HORSE));
    }
}
