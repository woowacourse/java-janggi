package core;

import db.repository.JanggiGameRepository;
import pieces.Side;
import position.Position;
import util.Retry;
import view.DisplayBoard;
import view.JanggiView;

public class GameSession {

    private final Long gameId;
    private JanggiGame game;
    private final JanggiView view;
    private final JanggiGameRepository repository;

    public GameSession(Long gameId, JanggiGame game, JanggiView view, JanggiGameRepository repository) {
        this.gameId = gameId;
        this.game = game;
        this.view = view;
        this.repository = repository;
    }

    public void run() {
        while (!game.isOver()) {
            moveUntilSuccess();
        }
        view.printGameIsOver(game.getWinnerSide());
    }

    private void moveUntilSuccess() {
        game = Retry.untilSuccess(() -> {
            printGameStatus();

            Position departure = view.askDeparture();
            Position destination = view.askDestination();
            JanggiGame updatedGame = game.move(departure, destination);

            repository.updatePiecePosition(gameId, departure, destination);
            repository.updateGameState(gameId, updatedGame.getTurn(), updatedGame.getStatus());

            return updatedGame;
        });
    }

    private void printGameStatus() {
        view.printBoard(DisplayBoard.of(game.getBoard()));

        final Side turnSide = game.getTurnSide();
        final Side otherTurnSide = turnSide.other();

        view.printTurnSide(turnSide);
        view.printScore(turnSide, game.calculateScoreOf(turnSide));
        view.printScore(otherTurnSide, game.calculateScoreOf(otherTurnSide));
    }
}
