package core;

import movepolicy.MoveHistory;
import pieces.Piece;
import pieces.Side;
import position.Position;
import util.Retry;
import view.DisplayBoard;
import view.JanggiView;

public class GameSession {

    private JanggiGame game;
    private final JanggiView view;

    public GameSession(final JanggiGame game, final JanggiView view) {
        this.game = game;
        this.view = view;
    }

    public boolean isPlaying() {
        return game.isOver();
    }

    public void printResult() {
        if (isPlaying()) {
            throw new IllegalArgumentException("아직 게임이 종료되지 않았습니다.");
        }
        view.printGameResult(game.getResult());
    }

    public GameTurnResult run() {
        if (!isPlaying()) {
            throw new IllegalArgumentException("진행중인 게임만 실행할 수 있습니다.");
        }

        final GameTurnResult result = Retry.untilSuccess(() -> {
            printGameStatus();

            if (view.askEndByScore(game.getTurnSide())) {
                return endGameByScore();
            }
            return playGame();
        });

        this.game = result.updatedGame();
        return result;
    }

    private void printGameStatus() {
        view.printBoard(DisplayBoard.of(game.getBoard()));

        final Side turnSide = game.getTurnSide();
        final Side otherTurnSide = turnSide.other();

        view.printTurnSide(turnSide);
        view.printScore(turnSide, game.calculateScoreOf(turnSide));
        view.printScore(otherTurnSide, game.calculateScoreOf(otherTurnSide));
    }

    private GameTurnResult endGameByScore() {
        final JanggiGame updatedGame = game.endByScore();
        return GameTurnResult.endByScore(updatedGame);
    }

    private GameTurnResult playGame() {
        final Position departure = view.askDeparture();
        final Position destination = view.askDestination();

        final Piece movingPiece = game.getPieceAt(departure);
        final Piece capturedPiece = game.getPieceAt(destination);
        final JanggiGame updatedGame = game.move(departure, destination);

        return GameTurnResult.move(
            updatedGame,
            new MoveHistory(departure, destination, movingPiece, capturedPiece)
        );
    }
}
