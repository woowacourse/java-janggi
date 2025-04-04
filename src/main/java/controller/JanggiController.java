package controller;

import domain.position.ChessPosition;
import domain.type.ChessTeam;
import game.Janggi;
import service.JanngiService;
import util.LoopTemplate;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanngiService janngiService;

    public JanggiController(final InputView inputView, final OutputView outputView, final JanngiService janngiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janngiService = janngiService;
    }

    public void run() {
        final Janggi janggi = janngiService.getJanggi();
        processGame(janggi);
    }

    private void processGame(final Janggi janggi) {
        if (!janngiService.canContinueGame(janggi)) {
            outputView.printWinner(janngiService.getWinner(janggi));
            janngiService.clear();
            return;
        }
        continueGame(janggi);
        processGame(janggi);
    }

    private void continueGame(final Janggi janggi) {
        outputView.printBoard(janggi.getChessPiecesMapView());
        outputView.printCurrentScore(janngiService.getScoreByTeam(janggi,ChessTeam.BLUE), janngiService.getScoreByTeam(janggi, ChessTeam.RED));
        LoopTemplate.tryCatchLoop(() -> {
            outputView.printCurrentTeam(janggi.getCurrentTeam());
            final ChessPosition fromPosition = inputView.readFromPosition();
            janggi.validateFromPosition(fromPosition);
            outputView.printAvailableDestinations(janggi.getAvailableDestinations(fromPosition));
            final ChessPosition toPosition = inputView.readToPosition();
            janngiService.processTurn(janggi, fromPosition, toPosition);
        });
    }
}
