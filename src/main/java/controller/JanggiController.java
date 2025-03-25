package controller;

import domain.JanggiCoordinate;
import domain.JanggiGame;
import domain.PieceInitializer;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void startJanggiGame() {
        JanggiGame game = new JanggiGame(PieceInitializer.init());

        while (true) {
            try {
                outputView.printCurrBoard(game.getBoard());
                outputView.printCurrTurn(game.getCurrTurn());
                JanggiCoordinate from = inputView.readMovePiece();
                JanggiCoordinate to = inputView.readMoveDestination();
                game.movePlayerPiece(from, to);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
