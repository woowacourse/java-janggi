package janggi;

import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {

        JanggiGame janggiGame = new JanggiGame(new InputView(), new OutputView());
        janggiGame.start();
    }
}
