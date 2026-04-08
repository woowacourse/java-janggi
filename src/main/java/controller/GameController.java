package controller;

import application.GameService;
import application.GameSession;
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
        GameStartResult startResult = createOrRestoreSession();
        presenter.printStartMessage(startResult);
        GameSession session = startResult.session();
        presenter.printBoard(session.game().board());

        while (processTurn(session)) {
        }
    }

    private GameStartResult createOrRestoreSession() {
        return gameService.startOrResume(this::createNewSession);
    }

    private boolean processTurn(GameSession session) {
        try {
            JanggiGame game = session.game();
            presenter.printTurn(game.currentTurn());
            presenter.printMoveGuide();

            PositionInput departureInput = inputView.readDeparturePosition();
            if (departureInput.pause()) {
                presenter.printPausedMessage();
                return false;
            }
            if (departureInput.finish()) {
                finishByScore(session);
                return false;
            }

            PositionInput destinationInput = inputView.readDestinationPosition();
            if (destinationInput.pause()) {
                presenter.printPausedMessage();
                return false;
            }
            if (destinationInput.finish()) {
                finishByScore(session);
                return false;
            }

            move(session, departureInput.position(), destinationInput.position());
            presenter.printBoard(game.board());

            if (game.gameResult().isEnded()) {
                presenter.printGameResult(game.gameResult());
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

    private GameSession createNewSession() {
        SangSetup choSangSetup = readSangSetup(Side.CHO);
        SangSetup hanSangSetup = readSangSetup(Side.HAN);
        return gameService.createNewGame(choSangSetup, hanSangSetup);
    }

    private void move(GameSession session, Position departure, Position destination) {
        gameService.move(session, departure, destination);
    }

    private void finishByScore(GameSession session) {
        GameScore gameScore = gameService.finishByScore(session);
        GameResult gameResult = session.game().gameResult();
        presenter.printGameScoreResult(gameResult, gameScore);
    }
}
