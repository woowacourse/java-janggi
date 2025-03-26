package controller;

import domain.Country;
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

        while (!game.isGameOver()) {
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

        outputView.printWinner(game.getWinner());
        outputView.printScore(Country.CHO, game.getCountryScore(Country.CHO));
        outputView.printScore(Country.HAN, game.getCountryScore(Country.HAN));
    }
}
