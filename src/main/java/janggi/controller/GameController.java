package janggi.controller;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class GameController {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;


    public GameController(InputView inputView, OutputView outputView, GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
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
            ControllerUtil.retry(() -> move(game), outputView);
        }
        outputView.printWinner(game.winnerSide());
        gameService.updateWinner(game);
    }

    private void move(Game game) {
        outputView.printBoard(game.getBoard());
        outputView.printSide(game.getTurn());

        Point from = readPoint(game);
        outputView.printBoardWithPath(game.getBoard(), game.destinations(from));

        Point to = inputView.readDestination();
        if (to == null) {
            return;
        }
        gameService.move(game, from, to);
    }

    private Point readPoint(Game game) {
        Point from = inputView.readPoint();
        if (!game.isTurnPiece(from)) {
            throw new IllegalArgumentException("움직일 수 없습니다.");
        }
        return from;
    }
}
