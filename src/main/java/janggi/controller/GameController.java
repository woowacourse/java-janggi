package janggi.controller;

import janggi.dao.entity.GameEntity;
import janggi.domain.board.setup.BoardSetUp;
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
        GameEntity gameEntity = gameService.createGame(gameName, choBoardSetUp, hanBoardSetUp);
        playGame(gameEntity.id());
    }

    public void loadGame() {
        outputView.printAllGameNames(gameService.findAllGameNames());
        String gameName = inputView.readGameName();
        GameEntity gameEntity = gameService.findByName(gameName);
        playGame(gameEntity.id());
    }

    private void playGame(int gameId) {
        while (gameService.canPlay(gameId)) {
            ControllerUtil.retry(() -> move(gameId), outputView);
        }
        outputView.printWinner(gameService.getWinnderSide(gameId));
    }

    private void move(int gameId) {
        outputView.printBoard(gameService.getBoardByGameId(gameId));
        outputView.printSide(gameService.getTurn(gameId));

        Point from = readPoint(gameId);
        outputView.printBoardWithPath(gameService.getBoardByGameId(gameId),
                gameService.getDestinations(gameId, from));

        Point to = inputView.readDestination();
        if (to == null) {
            return;
        }
        gameService.move(gameId, from, to);
    }

    private Point readPoint(int gameId) {
        Point from = inputView.readPoint();
        if (!gameService.isTurnPiece(gameId, from)) {
            throw new IllegalArgumentException("움직일 수 없습니다.");
        }
        return from;
    }
}
