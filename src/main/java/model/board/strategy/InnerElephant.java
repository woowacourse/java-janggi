package model.board.strategy;

import model.board.Board;
import model.board.Country;
import model.board.HorseElephantStrategy;
import model.pieces.Elephant;
import model.pieces.Horse;
import model.position.Position;
import model.position.Row;

public class InnerElephant implements HorseElephantStrategy {
    @Override
    public void deploy(Board board, Country country) {
        board.place(Position.of(Row.edgePiece(country), COLUMN_TWO), new Horse(country));
        board.place(Position.of(Row.edgePiece(country), COLUMN_THREE), new Elephant(country));
        board.place(Position.of(Row.edgePiece(country), COLUMN_SEVEN), new Elephant(country));
        board.place(Position.of(Row.edgePiece(country), COLUMN_EIGHT), new Horse(country));
    }
}
