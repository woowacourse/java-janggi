package controller;

import model.Army;
import model.Board;
import model.Country;
import strategy.InnerElephant;
import strategy.LeftElephant;
import strategy.OuterElephant;
import strategy.RightElephant;
import view.InputView;
import view.OutputView;

public class GameController {

    public void start() {
        Board board = new Board();
        init(board);
    }

    private void init(Board board) {
        OutputView.printArrangeCountry(Country.CHO.title());
        Army cho = initArmy();
        cho.deployTo(board, Country.CHO);

        OutputView.printArrangeCountry(Country.HAN.title());
        Army han = initArmy();
        han.deployTo(board, Country.HAN);
    }

    private Army initArmy() {
        String number = InputView.readArrangement();
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
