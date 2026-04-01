package controller;

import domain.game.JanggiGame;
import util.Retry;
import view.InputView;
import view.OutputView;

import java.util.List;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        String inputCho = inputView.inputPlacementChoOption();
        String inputHan = inputView.inputPlacementHanOption();
        JanggiGame janggiGame = JanggiGame.of(inputCho, inputHan);
        outputView.printBoard(janggiGame.captureBoard());
        while (!janggiGame.isGameEnd()) {
            outputView.printTurn(janggiGame.getTurnName());
            List<Integer> from = choosePiece(janggiGame);
            chooseDestinationAndGameStart(janggiGame, from);
        }
        outputView.printGameEnd(janggiGame.getWinnerName());
    }

    private List<Integer> choosePiece(JanggiGame janggiGame) {
        return Retry.repeatUntilSuccess(() -> {
            List<Integer> inputTokens = inputView.inputPieceLocation();
            janggiGame.checkSameTeam(inputTokens);
            return inputTokens;
        });
    }

    private void chooseDestinationAndGameStart(JanggiGame janggiGame, List<Integer> from) {
        Retry.repeatUntilSuccess(() -> {
            List<Integer> to = inputView.inputDestination();
            janggiGame.start(from, to);
            outputView.printBoard(janggiGame.captureBoard());
        });
    }
}
