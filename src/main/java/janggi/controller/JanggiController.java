package janggi.controller;

import janggi.application.GameDto;
import janggi.application.GameService;
import janggi.domain.DomainException;
import janggi.domain.board.DefaultBoardDesignPolicy;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.Game;
import janggi.domain.position.Position;
import janggi.view.dto.BoardDto;
import janggi.view.dto.PositionDto;
import janggi.view.mapper.HorseElephantPositionMapper;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;

    public JanggiController(InputView inputView, OutputView outputView, GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
    }

    public void run() {

        int gameOption = getUntilValid(inputView::readGameOption);
        Game game = null;
        if(gameOption == 1) {
            game = createGame();
        } else if (gameOption == 2) {
            List<GameDto> recentlyPlayedGames = gameService.getRecentlyPlayedGames();
            Long selectedGameId = getUntilValid(() -> inputView.readSelectedGame(recentlyPlayedGames));
            game = gameService.loadGame(selectedGameId);
        }

        outputView.printBoard(BoardDto.from(game.boardMap()));

        while (game.winner().isEmpty()) {
            printScore(game);
            moveProcess(game);
        }
        outputView.printWinner(game.winner().get());
    }

    private Game createGame() {
        Game game;
        Map<Dynasty, HorseElephantPosition> horseElephantPositions = readDynastyHorseElephantPositionMap();
        game = gameService.createGame(new DefaultBoardDesignPolicy(horseElephantPositions), "room", LocalDateTime.now());
        return game;
    }

    private void printScore(Game game) {
        outputView.printScore(new EnumMap<>(Map.of(
                Dynasty.CHO, game.calculateScoreByDynasty(Dynasty.CHO),
                Dynasty.HAN, game.calculateScoreByDynasty(Dynasty.HAN)
        )));
    }

    private Map<Dynasty, HorseElephantPosition> readDynastyHorseElephantPositionMap() {
        Map<Dynasty, HorseElephantPosition> horseElephantPositions = new EnumMap<>(Dynasty.class);
        for (Dynasty dynasty : Dynasty.values()) {
            int ordinal = getUntilValid(() -> inputView.readHorseElephantPosition(dynasty));
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
                () -> inputView.readPieceWantToMove(game.currentTurn()));
        return Position.from(fromDto.row(), fromDto.column());
    }

    private void findCanMovePosition(Game game, Position from) {
        List<Position> positions = game.findMovablePositions(from);
        outputView.printBoard(BoardDto.canMovePositionsFrom(game.boardMap(), positions));
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
     *
     * @param readOperation: 특정 입력을 받는 작업
     * @return: 입력값
     */
    private <T> T getUntilValid(Supplier<T> readOperation) {
        while (true) {
            try {
                return readOperation.get();
            } catch (DomainException | IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            } catch (Exception e) {
                outputView.printErrorMessage("알 수 없는 에러가 발생했습니다.");
            }
        }
    }

    /**
     * 적절한 입력이 들어올 때까지 반복해서 실행하는 메서드(반환값 없음)"
     *
     * @param readOperation: 특정 입력을 받는 작업
     */
    private void runUntilValid(Runnable readOperation) {
        while (true) {
            try {
                readOperation.run();
                break;
            } catch (DomainException | IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            } catch (Exception e) {
                outputView.printErrorMessage("알 수 없는 에러가 발생했습니다.");
            }
        }
    }

}
