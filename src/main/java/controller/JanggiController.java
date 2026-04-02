package controller;

import domain.board.Board;
import domain.board.ElephantSetup;
import domain.player.Player;
import domain.player.Team;
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
        Player choPlayer = generateChoPlayer();
        Player hanPlayer = generateHanPlayer();

        Board board = initializeBoard();
    }


    private Player generateHanPlayer() {
        outputView.printEnterHanPlayerNamePrompt();
        String hanPlayerName = inputView.readPlayerName();
        return Player.of(hanPlayerName, Team.HAN);
    }

    private Player generateChoPlayer() {
        outputView.printEnterChoPlayerNamePrompt();
        String choPlayerName = inputView.readPlayerName();
        return Player.of(choPlayerName, Team.CHO);
    }


    private Board initializeBoard() {
        outputView.printChoiceChoElephantSetupPrompt();
        int choElephantSetupNumber = inputView.readElephantSetup();

        outputView.printChoiceHanElephantSetupPrompt();
        int hanElephantSetupNumber = inputView.readElephantSetup();

        return Board.init(ElephantSetup.of(choElephantSetupNumber), ElephantSetup.of(hanElephantSetupNumber));
    }
}
