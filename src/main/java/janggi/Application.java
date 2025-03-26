package janggi;

import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiGame janggiGame = new JanggiGame();
        OutputView outputView = new OutputView();
        outputView.printBoard(janggiGame.getBoard());
    }
}
