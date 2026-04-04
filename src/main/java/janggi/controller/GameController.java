package janggi.controller;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.piece.unit.Piece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Map;

public class GameController {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;
    private final MoveController moveController;


    public GameController(InputView inputView, OutputView outputView, GameService gameService,
                          MoveController moveController) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
        this.moveController = moveController;
    }

    public void createGame() {
        BoardSetUp choBoardSetUp = inputView.readBoardSetup(Side.CHO);
        BoardSetUp hanBoardSetUp = inputView.readBoardSetup(Side.HAN);
        String gameName = inputView.readGameName();
        Game game = gameService.createGame(gameName, choBoardSetUp, hanBoardSetUp);
        playGame(game);
    }

    public void loadGame() {
        outputView.printAllGameNames(gameService.findAllGameNames());
        String gameName = inputView.readGameName();
        Game game = gameService.findByName(gameName);
        playGame(game);
    }

    private void playGame(Game game) {
        while (game.canPlay()) {
            Map<Point, Piece> board = game.getBoard();
            outputView.printBoard(board);
            outputView.printSide(game.getTurn());

            retry(() -> moveController.move(game));
        }
        outputView.printWinner(game.winnerSide());
        gameService.updateWinner(game);
    }

    private void retry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                break;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
