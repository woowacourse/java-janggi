package controller;

import domain.game.JanggiGame;
import dto.PieceSnapshot;
import service.FacadeService;
import util.Retry;
import view.InputView;
import view.OutputView;

import java.util.List;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final FacadeService facadeService;

    public JanggiController(InputView inputView, OutputView outputView, FacadeService facadeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.facadeService = facadeService;
    }

    public void start() {
        JanggiGame janggiGame = initGame();
        outputView.printBoard(janggiGame.gameSnapshot());
        while (!janggiGame.isGameEnd()) {
            outputView.printTurn(janggiGame.getTurnName());
            List<Integer> from = choosePiece(janggiGame);
            chooseDestinationAndGameStart(janggiGame, from);
        }
        outputView.printGameEnd(janggiGame.getWinnerName());
    }

    private JanggiGame initGame() {
        if (facadeService.existsGame()) {
            return facadeService.loadOngoingGame();
        }
        String inputCho = inputView.inputPlacementChoOption();
        String inputHan = inputView.inputPlacementHanOption();
        JanggiGame janggiGame = JanggiGame.of(inputCho, inputHan);
        List<PieceSnapshot> pieceSnapshots = janggiGame.capturePieces();
        facadeService.save(pieceSnapshots, janggiGame.getTurn());
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
            facadeService.update(from, to, janggiGame.getTurn());
            outputView.printBoard(janggiGame.gameSnapshot());
        });
    }
}
