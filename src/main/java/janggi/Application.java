package janggi;

import janggi.controller.JanggiController;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.format.PieceEnglishFormat;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView(new Scanner(System.in));
        OutputView outputView = new OutputView(new PieceEnglishFormat());
        JanggiController janggiController = new JanggiController(inputView, outputView);
        janggiController.start();
    }
}
