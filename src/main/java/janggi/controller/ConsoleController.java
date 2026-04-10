package janggi.controller;

import janggi.domain.Side;
import janggi.domain.board.Formation;
import janggi.domain.player.Name;
import janggi.domain.space.Position;
import janggi.dto.GameDto;
import janggi.dto.SideDto;
import janggi.service.GameService;
import janggi.view.InputParser;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.function.Supplier;

public class ConsoleController {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;
    private GameSession gameSession;

    public ConsoleController(InputView inputView, OutputView outputView, GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
    }

    public void run() {
        this.gameSession = retry(this::startGame);
        outputView.printBoard(gameService.getBoardDto(gameSession.gameId()));

        while (gameService.isPlaying(gameSession.gameId())) {
            playTurn();
        }
        outputView.printWinner(gameService.getWinnerDto(gameSession.gameId()));
    }

    private GameSession startGame() {
        GameCommand gameCommand = InputParser.parseGameCommand(inputView.readGameCommand());
        return gameCommand.execute(this);
    }

    GameSession initializeGame() {
        Name choName = getPlayerName(Side.CHO);
        Formation choFormation = getFormation(Side.CHO);
        Name hanName = getPlayerName(Side.HAN);
        Formation hanFormation = getFormation(Side.HAN);

        Long gameId = retry(() -> gameService.initializeGame(choName, hanName, choFormation, hanFormation));
        return new GameSession(gameId);
    }

    GameSession loadGame() {
        List<GameDto> gameDtos = gameService.findAllGames();
        if (gameDtos.isEmpty()) {
            outputView.printError("저장된 게임이 없습니다. 새로운 게임을 시작합니다.");
            return initializeGame();
        }
        outputView.printGameList(gameDtos);

        Long gameId = retry(() -> InputParser.parseGameId(inputView.readGameId()));
        return new GameSession(gameId);
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
            gameService.move(gameSession.gameId(), source, target);
        });
        outputView.printBoard(gameService.getBoardDto(gameSession.gameId()));
    }

    private Position selectPiecePosition() {
        return retry(() -> {
            Position position = InputParser.parsePosition(
                    inputView.readSourcePosition(SideDto.from(gameService.getCurrentSide(gameSession.gameId()))));
            outputView.printDestinations(gameService.selectSource(gameSession.gameId(), position));
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
