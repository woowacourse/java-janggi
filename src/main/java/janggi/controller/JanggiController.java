package janggi.controller;

import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.Game;
import janggi.domain.position.Position;
import janggi.dto.BoardDto;
import janggi.dto.DynastyDto;
import janggi.dto.PositionDto;
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

        Game game = janggiService.findGame(gameId);
        while (!game.isFinished()) {
            moveProcess(gameId);
            game = janggiService.findGame(gameId);
        }
        printWinner(gameId);
    }

    private Long initGame() {
        if (inputView.readWantToRestore()) {
            return janggiService.findRecentlyGameId();
        }
        return janggiService.makeGame(readDynastyHorseElephantPositions());
    }

    private Map<Dynasty, HorseElephantPosition> readDynastyHorseElephantPositions() {
        Map<Dynasty, HorseElephantPosition> horseElephantPositions = new EnumMap<>(Dynasty.class);
        for (Dynasty dynasty : Dynasty.values()) {
            int ordinal = getUntilValid(() -> inputView.readHorseElephantPosition(DynastyDto.from(dynasty)));
            HorseElephantPosition position = HorseElephantPositionMapper.from(ordinal);
            horseElephantPositions.put(dynasty, position);
        }
        return horseElephantPositions;
    }

    private void moveProcess(Long gameId) {
        Game game = janggiService.findGame(gameId);

        Position from = getUntilValid(() -> {
            Position wantToMove = readPieceWantToMove(game);
            findCanMovePosition(game, wantToMove);
            return wantToMove;
        });

        runUntilValid(() -> {
            Position to = readDestinationPosition();
            janggiService.movePiece(gameId, from, to);
            printBoard(gameId);
        });
    }

    private Position readPieceWantToMove(Game game) {
        PositionDto fromDto = getUntilValid(
                () -> inputView.readPieceWantToMove(DynastyDto.from(game.currentDynasty())));
        return Position.from(fromDto.row(), fromDto.column());
    }

    private void findCanMovePosition(Game game, Position from) {
        List<Position> positions = game.canMovePosition(from);
        outputView.printCanMovePositions(PositionDto.fromPositions(positions));
    }

    private Position readDestinationPosition() {
        PositionDto position = getUntilValid(inputView::readDestinationPosition);
        return Position.from(position.row(), position.column());
    }

    private void printBoard(Long gameId) {
        Game game = janggiService.findGame(gameId);
        outputView.printBoard(BoardDto.from(game.boardMap()));
    }

    private void printWinner(Long gameId) {
        Game game = janggiService.findGame(gameId);
        outputView.printWinner(DynastyDto.from(game.judgeWinner()));
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

    private void runUntilValid(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                break;
            } catch (IllegalArgumentException | IllegalStateException e) {
                outputView.printWarningMessage(e.getMessage());
            } catch (Exception e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

}
