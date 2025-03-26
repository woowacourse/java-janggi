package janggi;

import janggi.controller.JanggiController;
import janggi.domain.Turn;
import janggi.domain.piece.Team;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Scanner;

public class JanggiApplication {
    public static void main(final String[] args) {
        InputView inputView = new InputView(new Scanner(System.in));
        OutputView outputView = new OutputView();
        Turn turn = Turn.startWith(Team.RED);
        JanggiController janggiController = new JanggiController(inputView, outputView, turn);
        janggiController.startJanggi();
    }
}
