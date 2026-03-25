package model;

import model.pieces.Cannon;
import model.pieces.Chariot;
import model.pieces.General;
import model.pieces.Guard;
import model.pieces.Soldier;

public class Army {
    private static final int HAN_COLUMN = 1;
    private static final int CHO_COLUMN = 1;
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
        board.place(Position.of(4, 1), new Soldier(country));
        board.place(Position.of(4, 3), new Soldier(country));
        board.place(Position.of(4, 5), new Soldier(country));
        board.place(Position.of(4, 7), new Soldier(country));
        board.place(Position.of(4, 9), new Soldier(country));
    }

    private void deployCannon(Board board, Country country) {
        board.place(Position.of(3, 2), new Cannon(country));
        board.place(Position.of(3, 8), new Cannon(country));
    }

    private void deployGeneral(Board board, Country country) {
        board.place(Position.of(2, 5), new General(country));
    }

    private void deployGuard(Board board, Country country) {
        board.place(Position.of(1, 4), new Guard(country));
        board.place(Position.of(1, 6), new Guard(country));
    }

    private void deployChariot(Board board, Country country) {
        board.place(Position.of(1, 1), new Chariot(country));
        board.place(Position.of(1, 9), new Chariot(country));
    }

    private void deployHorseElephant(Board board, Country country) {
        strategy.deploy(board, country);
    }
}
