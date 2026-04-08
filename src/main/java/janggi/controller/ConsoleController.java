package janggi.controller;

import janggi.domain.Side;
import janggi.domain.board.Formation;
import janggi.domain.player.Name;
import janggi.domain.space.Position;
import janggi.dto.SideDto;
import janggi.service.GameService;
import janggi.view.InputParser;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.function.Supplier;

public class ConsoleController {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;

    public ConsoleController(InputView inputView, OutputView outputView, GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
    }

    public void run() {
        retry(this::startGame);
        outputView.printBoard(gameService.getBoardDto());
        while (gameService.isPlaying()) {
            playTurn();
        }
        outputView.printWinner(gameService.getWinnerDto());
    }

    private void startGame() {
        GameCommand gameCommand = InputParser.parseGameCommand(inputView.readGameCommand());
        gameCommand.execute(this);
    }

    void initializeGame() {
        Name choName = getPlayerName(Side.CHO);
        Formation choFormation = getFormation(Side.CHO);
        Name hanName = getPlayerName(Side.HAN);
        Formation hanFormation = getFormation(Side.HAN);
        retry(() -> gameService.initializeGame(choName, hanName, choFormation, hanFormation));
    }

    void loadGame() {
        retry(() -> gameService.loadGame(InputParser.parseGameId(inputView.readGameId())));
    }

    private Name getPlayerName(Side side) {
        return retry(() -> InputParser.parseName(inputView.readPlayerName(SideDto.from(side))));
    }

    private Formation getFormation(Side side) {
        return retry(() -> Formation.from(InputParser.parseFormation(inputView.readFormation(SideDto.from(side)))));
    }

    private void playTurn() {
        Position source = selectPiecePosition();
        retry(() -> {
            Position target = InputParser.parsePosition(inputView.readTargetPosition());
            gameService.move(source, target);
        });
        outputView.printBoard(gameService.getBoardDto());
    }

    private Position selectPiecePosition() {
        return retry(() -> {
            Position position = InputParser.parsePosition(
                    inputView.readSourcePosition(SideDto.from(gameService.getCurrentSide())));
            outputView.printDestinations(gameService.selectSource(position));
            return position;
        });
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void retry(Runnable action) {
        while (true) {
            try {
                action.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
