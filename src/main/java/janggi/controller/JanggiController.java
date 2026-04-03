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

    // TODO: 불러오기와 새 게임 분리
    public void run() {
        Map<Dynasty, HorseElephantPosition> horseElephantPositions = readDynastyHorseElephantPositionMap();
        Long gameId = janggiService.makeGame(horseElephantPositions);
        Game game = janggiService.findGame(gameId);
        outputView.printBoard(BoardDto.from(game.boardMap()));

        while (!game.isFinished()) {
            moveProcess(game);
        }
        outputView.printWinner(DynastyDto.from(game.judgeWinner()));
    }

    private Map<Dynasty, HorseElephantPosition> readDynastyHorseElephantPositionMap() {
        Map<Dynasty, HorseElephantPosition> horseElephantPositions = new EnumMap<>(Dynasty.class);
        for (Dynasty dynasty : Dynasty.values()) {
            int ordinal = getUntilValid(() -> inputView.readHorseElephantPosition(DynastyDto.from(dynasty)));
            HorseElephantPosition position = HorseElephantPositionMapper.from(ordinal);
            horseElephantPositions.put(dynasty, position);
        }
        return horseElephantPositions;
    }

    private void moveProcess(Game game) {
        Position from = getUntilValid(() -> {
            Position wantToMove = readPieceWantToMove(game);
            findCanMovePosition(game, wantToMove);
            return wantToMove;
        });

        runUntilValid(() -> {
            Position to = readPositionToMove();
            movePiece(game, from, to);
        });
    }

    private Position readPieceWantToMove(Game game) {
        PositionDto fromDto = getUntilValid(
                () -> inputView.readPieceWantToMove(DynastyDto.from(game.currentTurn().currentDynasty())));
        return Position.from(fromDto.row(), fromDto.column());
    }

    private void findCanMovePosition(Game game, Position from) {
        List<Position> positions = game.canMovePosition(from);
        outputView.printCanMovePositions(PositionDto.fromPositions(positions));
    }

    private Position readPositionToMove() {
        PositionDto toDto = getUntilValid(inputView::readPositionToMove);
        return Position.from(toDto.row(), toDto.column());
    }

    private void movePiece(Game game, Position from, Position to) {
        game.movePiece(from, to);
        outputView.printBoard(BoardDto.from(game.boardMap()));
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
