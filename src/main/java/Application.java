import controller.JanggiController;

import domain.board.BasicBoardInitializer;
import view.InputView;
import view.OutputView;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(new InputView(new Scanner(System.in)), new OutputView(), new BasicBoardInitializer());
        janggiController.play();
    }
}
