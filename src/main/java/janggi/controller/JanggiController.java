package janggi.controller;

import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.Game;
import janggi.domain.position.Position;
import janggi.dto.BoardDto;
import janggi.dto.DynastyDto;
import janggi.dto.PositionDto;
import janggi.service.JanggiService;
import janggi.util.HorseElephantPositionMapper;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JanggiController {

    private final JanggiService janggiService;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(JanggiService janggiService, InputView inputView, OutputView outputView) {
        this.janggiService = janggiService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Long gameId = initGame();
        printBoard(gameId);

        while (!janggiService.isFinishedGame(gameId)) {
            moveProcess(gameId);
        }
        printWinner(gameId);
    }

    private Long initGame() {
        return getUntilValid(this::findGameIdToPlay);
    }

    private Long findGameIdToPlay() {
        if (inputView.readWantToRestore()) {
            List<Long> gameIds = janggiService.findPlayableGameIds();
            outputView.printPlayableGameIds(gameIds);
            return readGameIdToRestore();
        }
        return janggiService.makeGame(readDynastyHorseElephantPositions());
    }

    private Long readGameIdToRestore() {
        long gameId = inputView.readGameIdToRestore();
        return janggiService.validatePlayableGameId(gameId);
    }

    private Map<Dynasty, HorseElephantPosition> readDynastyHorseElephantPositions() {
        Map<Dynasty, HorseElephantPosition> horseElephantPositions = new EnumMap<>(Dynasty.class);
        for (Dynasty dynasty : Dynasty.values()) {
            int ordinal = getUntilValid(() -> inputView.readHorseElephantPosition(DynastyDto.from(dynasty)));
            horseElephantPositions.put(dynasty, HorseElephantPositionMapper.from(ordinal));
        }
        return horseElephantPositions;
    }

    private void moveProcess(Long gameId) {
        Position from = readSourcePositionUntilValid(gameId);
        Position to = readDestinationPositionUntilValid(gameId, from);

        janggiService.saveMovement(gameId, from, to);
        printBoard(gameId);
    }

    private Position readSourcePositionUntilValid(Long gameId) {
        return getUntilValid(() -> {
            Position source = readSourcePosition(gameId);
            findPlaceablePosition(gameId, source);
            return source;
        });
    }

    private Position readDestinationPositionUntilValid(Long gameId, Position from) {
        return getUntilValid(() -> {
            Position destination = readDestinationPosition();
            janggiService.movePiece(gameId, from, destination);
            return destination;
        });
    }

    private Position readSourcePosition(Long gameId) {
        Game game = janggiService.findGame(gameId);
        DynastyDto currentTurn = DynastyDto.from(game.currentDynasty());
        PositionDto from = getUntilValid(() -> inputView.readPieceWantToMove(currentTurn));
        return Position.from(from.row(), from.column());
    }

    private void findPlaceablePosition(Long gameId, Position from) {
        Game game = janggiService.findGame(gameId);
        List<Position> positions = game.placeablePositions(from);
        outputView.printCanMovePositions(PositionDto.fromPositions(positions));
    }

    private Position readDestinationPosition() {
        PositionDto to = getUntilValid(inputView::readDestinationPosition);
        return Position.from(to.row(), to.column());
    }

    private void printBoard(Long gameId) {
        Game game = janggiService.findGame(gameId);
        outputView.printBoard(BoardDto.from(game.pieces()));
    }

    private void printWinner(Long gameId) {
        Dynasty winner = janggiService.findWinner(gameId);
        outputView.printWinner(DynastyDto.from(winner));
    }

    private <T> T getUntilValid(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException | IllegalStateException e) {
                outputView.printWarningMessage(e.getMessage());
            } catch (Exception e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

}
