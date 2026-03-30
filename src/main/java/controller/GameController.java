package controller;

import java.util.List;
import model.board.Army;
import model.board.Board;
import model.board.Country;
import model.board.strategy.InnerElephant;
import model.board.strategy.LeftElephant;
import model.board.strategy.OuterElephant;
import model.board.strategy.RightElephant;
import model.move.Move;
import model.position.Position;
import view.InputHandler;
import view.InputView;
import view.OutputView;

public class GameController {

    public void start() {
        Board board = new Board();
        init(board);
        OutputView.printBoard(board);
        while (true) {
            choGamePhase(board);
            hanGamePhase(board);
        }
    }

    private void init(Board board) {
        OutputView.printArrangeCountry(Country.CHO);
        Army cho = initArmy(Country.CHO);
        cho.deployTo(board, Country.CHO);
        OutputView.printLine();
        OutputView.printArrangeCountry(Country.HAN);
        Army han = initArmy(Country.HAN);
        han.deployTo(board, Country.HAN);
    }

    private Army initArmy(Country country) {
        String number = InputView.readArrangement(country);
        if (number.equals("2")) {
            return new Army(new OuterElephant());
        }
        if (number.equals("3")) {
            return new Army(new RightElephant());
        }
        if (number.equals("4")) {
            return new Army(new LeftElephant());
        }
        return new Army(new InnerElephant());
    }

    private void choGamePhase(Board board) {
        Country country = Country.CHO;
        OutputView.printPositionCountry(Country.CHO);
        gamePhase(board, country);
    }

    private void hanGamePhase(Board board) {
        Country country = Country.HAN;
        OutputView.printPositionCountry(Country.HAN);
        gamePhase(board, country);
    }

    private void gamePhase(Board board, Country country) {
        InputHandler.retry(() -> {
            List<Integer> startList = InputView.readStartPosition();
            Position from = Position.of(startList.get(0), startList.get(1));
            board.checkTurn(from, country);
            List<Integer> endList = InputView.readEndPosition();
            Position to = Position.of(endList.get(0), endList.get(1));
            Move move = new Move(from, to);
            board.move(move);
            OutputView.printBoard(board);
            return null;
        });
    }
}
