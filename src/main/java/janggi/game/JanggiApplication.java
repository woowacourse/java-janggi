package janggi.game;

import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.dto.GameDto;
import janggi.dto.MovementDto;
import janggi.dto.PiecesOnBoardDto;
import janggi.entity.GameEntity;
import janggi.entity.PieceEntity;
import janggi.piece.Piece;
import janggi.point.Point;
import janggi.view.BoardView;
import janggi.view.InputView;
import janggi.view.ResultView;
import java.util.function.Function;
import java.util.function.Supplier;

public class JanggiApplication {

    private final InputView inputView;
    private final BoardView boardView;
    private final ResultView resultView;
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    private JanggiApplication() {
        inputView = new InputView();
        boardView = new BoardView();
        resultView = new ResultView();
        gameDao = new GameDao();
        pieceDao = new PieceDao();
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

            MovementDto movement = retryUntilSuccessAndReturn(piece -> {
                        Point targetPoint = inputView.readTargetPoint();
                        return game.move(piece, targetPoint);
                    }
                    , movingPiece);
            updatePieceData(movingPiece, movement);

            game.reverseTurn();
            gameDao.updateTurn(game);
        }

        resultView.printResult(game, game.calculateScore());
        gameDao.deleteGame(game);
    }

    private void updatePieceData(Piece movingPiece, MovementDto movement) {
        pieceDao.updatePointFrom(movingPiece, movement.movedPiece());
        if (movement.attackedPiece().exists()) {
            pieceDao.updateToAttacked(movement.attackedPiece());
        }
    }

    private Game startGame() {
        if (inputView.readGameRestart()) {
            GameDto lastGameData = gameDao.findLastCreated();
            PiecesOnBoardDto lastPiecesData = pieceDao.findPieceDataBy(lastGameData);
            Game lastGame = GameEntity.recreateGameFrom(lastPiecesData, lastGameData);
            PieceEntity.recreatePieceRecordsFrom(lastPiecesData);

            return lastGame;
        }

        Game game = new Game();
        gameDao.createGame(game);
        for (Piece piece : game.getRunningPieces()) {
            pieceDao.createPiece(piece, game);
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

    private <T, R> R retryUntilSuccessAndReturn(Function<T, R> action, T input) {
        while (true) {
            try {
                return action.apply(input);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
