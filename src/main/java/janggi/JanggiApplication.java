package janggi;

import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Scanner;

public class JanggiApplication {
    public static void main(String[] args) {
        JanggiGame janggi = new JanggiGame(new InputView(new Scanner(System.in)), new OutputView());
        janggi.run();
    }
}
