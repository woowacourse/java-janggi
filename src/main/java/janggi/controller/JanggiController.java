package janggi.controller;

import janggi.domain.board.DefaultBoardDesignPolicy;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.Game;
import janggi.domain.position.Position;
import janggi.controller.dto.BoardDto;
import janggi.controller.dto.DynastyDto;
import janggi.controller.dto.PositionDto;
import janggi.controller.dto.mapper.HorseElephantPositionMapper;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Map<Dynasty, HorseElephantPosition> horseElephantPositions = readDynastyHorseElephantPositionMap();
        Game game = Game.initGame(new DefaultBoardDesignPolicy(horseElephantPositions));
        outputView.printBoard(BoardDto.from(game.boardMap()));

        while (!game.isGameOver()) {
            moveProcess(game);
        }
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
                () -> inputView.readPieceWantToMove(DynastyDto.from(game.currentTurn())));
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

    /**
     * "적절한 입력이 들어올 때까지 반복해서 실행하여 그 입력값을 리턴받는 메서드"
     * @param readOperation: 특정 입력을 받는 작업
     * @return: 입력값
     */
    private <T> T getUntilValid(Supplier<T> readOperation) {
        while (true) {
            try {
                return readOperation.get();
            } catch (Exception e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * 적절한 입력이 들어올 때까지 반복해서 실행하는 메서드(반환값 없음)"
     * @param readOperation: 특정 입력을 받는 작업
     */
    private void runUntilValid(Runnable readOperation) {
        while (true) {
            try {
                readOperation.run();
                break;
            } catch (Exception e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

}
