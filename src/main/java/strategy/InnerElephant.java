package strategy;

import model.Board;
import model.Country;
import model.HorseElephantStrategy;
import model.Position;
import model.Row;
import model.pieces.Elephant;
import model.pieces.Horse;

public class InnerElephant implements HorseElephantStrategy {
    @Override
    public void deploy(Board board, Country country) {
        board.place(Position.of(Row.edgePiece(country), COLUMN_TWO), new Horse(country));
        board.place(Position.of(Row.edgePiece(country), COLUMN_THREE), new Elephant(country));
        board.place(Position.of(Row.edgePiece(country), COLUMN_SEVEN), new Elephant(country));
        board.place(Position.of(Row.edgePiece(country), COLUMN_EIGHT), new Horse(country));
    }
}
