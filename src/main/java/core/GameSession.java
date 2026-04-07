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
            playUntilSuccess();
        }
        view.printGameResult(game.getResult());
    }

    private void playUntilSuccess() {
        game = Retry.untilSuccess(() -> {
            printGameStatus();

            if (view.askEndByScore(game.getTurnSide())) {
                return endGameByScore();
            }
            return playGame();
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

    private JanggiGame endGameByScore() {
        final JanggiGame updatedGame = game.endByScore();
        repository.updateGameState(gameId, updatedGame.getTurn(), updatedGame.getStatus());
        return updatedGame;
    }

    private JanggiGame playGame() {
        final Position departure = view.askDeparture();
        final Position destination = view.askDestination();
        final JanggiGame updatedGame = game.move(departure, destination);

        repository.updatePiecePosition(gameId, departure, destination);
        repository.updateGameState(gameId, updatedGame.getTurn(), updatedGame.getStatus());
        return updatedGame;
    }
}
