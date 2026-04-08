package controller;

import domain.game.JanggiGame;
import domain.piece.Team;
import service.JanggiService;
import util.Retry;
import view.InputView;
import view.OutputView;

import java.util.List;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiController(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void start() {
        JanggiGame janggiGame = initGame();
        outputView.printBoard(janggiGame.gameSnapshot());
        while (!janggiGame.isGameEnd()) {
            outputView.printTurn(janggiGame.getTurnName());
            List<Integer> from = choosePiece(janggiGame);
            chooseDestinationAndGameStart(janggiGame, from);
        }
        janggiService.gameEnd();
        outputView.printGameEnd(janggiGame.getWinnerName());
        outputView.printScore(janggiGame.getScore(Team.CHO), janggiGame.getScore(Team.HAN));
    }

    private JanggiGame initGame() {
        if (janggiService.existsGame()) {
            return janggiService.loadOngoingGame();
        }
        String inputCho = inputView.inputPlacementChoOption();
        String inputHan = inputView.inputPlacementHanOption();
        JanggiGame janggiGame = JanggiGame.of(inputCho, inputHan);
        janggiService.save(janggiGame.toSaveRequest());
        return janggiGame;
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
            janggiService.update(from, to, janggiGame.getTurnName());
            outputView.printBoard(janggiGame.gameSnapshot());
        });
    }
}
