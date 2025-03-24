package janggi;

import janggi.board.BoardFactory;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiManager janggiManager = new JanggiManager(
                BoardFactory.initBoard(),
                new InputView(),
                new OutputView()
        );
        janggiManager.play();
    }
}
