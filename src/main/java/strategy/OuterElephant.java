package strategy;

import model.Board;
import model.Country;
import model.HorseElephantStrategy;
import model.Position;
import model.pieces.Elephant;
import model.pieces.Horse;

public class OuterElephant implements HorseElephantStrategy {
    @Override
    public void deploy(Board board, Country country) {
        board.place(Position.of(1, 2), new Elephant(country));
        board.place(Position.of(1, 3), new Horse(country));
        board.place(Position.of(1, 7), new Horse(country));
        board.place(Position.of(1, 8), new Elephant(country));
    }
}
