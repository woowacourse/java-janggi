package domain;

import domain.board.JanggiBoard;
import view.OutputView;

public class JanggiGame {

    public void start() {
        JanggiBoard janggiBoard = new JanggiBoard();
        OutputView.printJanggiBoard(janggiBoard);
    }
}
