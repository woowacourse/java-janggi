package controller;

import domain.JanggiGame;
import domain.Player;
import domain.Position;
import domain.boardgenerator.JanggiBoardGenerator;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        List<String> playerNames = inputView.readPlayerNames();
        JanggiGame janggiGame = new JanggiGame(new JanggiBoardGenerator(), playerNames);
        outputView.displayPlayerInfo(playerNames);
        outputView.printJanggiBoard(janggiGame.getBoardState());
        while (true) {
            outputView.printTurnMessage(janggiGame.getThisTurnPlayer());
            janggiGame.move(inputView.readMovePiecePosition(), inputView.readTargetPosition());
            outputView.printJanggiBoard(janggiGame.getBoardState());
        }
    }
}
