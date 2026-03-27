package controller;

import model.board.Army;
import model.board.Board;
import model.board.Country;
import model.board.strategy.InnerElephant;
import model.board.strategy.LeftElephant;
import model.board.strategy.OuterElephant;
import model.board.strategy.RightElephant;
import view.InputView;
import view.OutputView;

public class GameController {

    public void start() {
        Board board = new Board();
        init(board);
        OutputView.printBoard(board);
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
}
