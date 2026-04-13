package controller;

import application.GameService;
import application.GameStartResult;
import domain.board.SangSetup;
import domain.board.SangSetupType;
import domain.game.GameResult;
import domain.game.GameScore;
import domain.game.JanggiGame;
import domain.pieces.Side;
import domain.position.Position;
import view.InputView;
import view.GamePresenter;
import view.dto.PositionInput;

public class GameController {

    private final InputView inputView;
    private final GameService gameService;
    private final GamePresenter presenter;

    public GameController(
            InputView inputView,
            GameService gameService,
            GamePresenter presenter
    ) {
        this.inputView = inputView;
        this.gameService = gameService;
        this.presenter = presenter;
    }

    public void run() {
        GameStartResult startResult = createOrRestoreGame();
        presenter.printStartMessage(startResult);
        JanggiGame janggiGame = startResult.game();
        presenter.printBoard(janggiGame.board());

        while (processTurn(janggiGame)) {
        }
    }

    private GameStartResult createOrRestoreGame() {
        return gameService.startOrResume(this::createNewGame);
    }

    private boolean processTurn(JanggiGame janggiGame) {
        try {
            presenter.printTurn(janggiGame.currentTurn());
            presenter.printMoveGuide();

            PositionInput departureInput = inputView.readDeparturePosition();
            if (departureInput.pause()) {
                presenter.printPausedMessage();
                return false;
            }
            if (departureInput.finish()) {
                finishByScore(janggiGame);
                return false;
            }

            PositionInput destinationInput = inputView.readDestinationPosition();
            if (destinationInput.pause()) {
                presenter.printPausedMessage();
                return false;
            }
            if (destinationInput.finish()) {
                finishByScore(janggiGame);
                return false;
            }

            move(janggiGame, departureInput.position(), destinationInput.position());
            presenter.printBoard(janggiGame.board());

            if (janggiGame.gameResult().isEnded()) {
                presenter.printGameResult(janggiGame.gameResult());
                return false;
            }
            return true;
        } catch (IllegalArgumentException e) {
            presenter.printErrorMessage(e.getMessage());
            return true;
        }
    }

    private SangSetup readSangSetup(Side side) {
        try {
            presenter.printSangSetupType(side);
            SangSetupType type = inputView.readSangSetupType();
            return type.create();
        } catch (IllegalArgumentException e) {
            presenter.printErrorMessage(e.getMessage());
            return readSangSetup(side);
        }
    }

    private JanggiGame createNewGame() {
        SangSetup choSangSetup = readSangSetup(Side.CHO);
        SangSetup hanSangSetup = readSangSetup(Side.HAN);
        return gameService.createNewGame(choSangSetup, hanSangSetup);
    }

    private void move(JanggiGame janggiGame, Position departure, Position destination) {
        gameService.move(janggiGame, departure, destination);
    }

    private void finishByScore(JanggiGame janggiGame) {
        GameScore gameScore = gameService.finishByScore(janggiGame);
        GameResult gameResult = janggiGame.gameResult();
        presenter.printGameScoreResult(gameResult, gameScore);
    }
}
