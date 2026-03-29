package janggi;

import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Scanner;

public class AppConfig {
    public JanggiGame janggi() {
        return new JanggiGame(inputView(), outputView());
    }

    public InputView inputView() {
        return new InputView(new Scanner(System.in));
    }

    public OutputView outputView() {
        return new OutputView();
    }
}
