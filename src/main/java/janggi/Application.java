package janggi;

import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiManager janggiManager = new JanggiManager(
                new InputView(),
                new OutputView()
        );
        janggiManager.play();
    }
}
