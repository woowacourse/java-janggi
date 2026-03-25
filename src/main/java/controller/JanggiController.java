package controller;

import domain.board.Board;
import domain.board.ElephantSetup;
import domain.player.Player;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printEnterChoPlayerNamePrompt();
        String choPlayerName = inputView.readPlayerName();

        outputView.printEnterChoPlayerNamePrompt();
        String hanPlayerName = inputView.readPlayerName();

        Player choPlayer = Player.cho(choPlayerName);
        Player hanPlayer = Player.han(hanPlayerName);

        outputView.printEnterChoElephantSetup();
        ElephantSetup choElephantSetup = inputView.readElephantSetup();

        outputView.printEnterHanElephantSetup();
        ElephantSetup hanElephantSetup = inputView.readElephantSetup();

        Board board = Board.init(choElephantSetup, hanElephantSetup);
    }
}
