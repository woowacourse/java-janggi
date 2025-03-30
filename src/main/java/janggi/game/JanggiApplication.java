package janggi.game;

import janggi.dao.GameDao;
import janggi.entity.GameEntity;
import janggi.dao.PieceDao;
import janggi.entity.PieceEntity;
import janggi.dto.GameDto;
import janggi.dto.PiecesOnBoardDto;
import janggi.piece.Piece;
import janggi.point.Point;
import janggi.view.BoardView;
import janggi.view.InputView;
import janggi.view.ResultView;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class JanggiApplication {

    private final InputView inputView;
    private final BoardView boardView;
    private final ResultView resultView;

    private JanggiApplication() {
        inputView = new InputView();
        boardView = new BoardView();
        resultView = new ResultView();
    }

    public static void main(String[] args) {
        JanggiApplication janggiApplication = new JanggiApplication();
        janggiApplication.run();
    }

    private void run() {
        Game game = retryUntilSuccessAndReturn(this::startGame);

        while (game.canContinue()) {
            boardView.displayBoard(game.getBoard());
            boardView.printTeam(game.getTurn());

            Piece movingPiece = retryUntilSuccessAndReturn(() -> {
                Point startPoint = inputView.readStartPoint();
                return game.findMovingPiece(startPoint);
            });

            movePieceUntilSuccess(piece -> {
                Point targetPoint = inputView.readTargetPoint();
                game.move(piece, targetPoint);
            }
            , movingPiece);

            game.reverseTurn();
            GameDao.updateTurn(game); //TODO DAO
        }

        resultView.printResult(game, game.calculateScore());
        GameDao.deleteGame(game); //TODO DAO
    }

    private Game startGame() { //TODO DAO
        if (inputView.readGameRestart()) {
            //TODO 더줄이기
            GameDto lastGameData = GameDao.findLastCreated();
            PiecesOnBoardDto lastPiecesData = PieceDao.findPieceDataBy(lastGameData);
            Game lastGame = GameEntity.recreateGameFrom(lastPiecesData, lastGameData);
            PieceEntity.recreatePieceRecordsFrom(lastPiecesData);

            return lastGame;
        }

        Game game = new Game();
        GameDao.createGame(game);
        for (Piece piece : game.getRunningPieces()) {
            PieceDao.createPiece(piece, game);
        }
        return game;
    }

    private <T> T retryUntilSuccessAndReturn(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private <T> void movePieceUntilSuccess(Consumer<T> action, T input) {
        while (true) {
            try {
                action.accept(input);
                return;
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
