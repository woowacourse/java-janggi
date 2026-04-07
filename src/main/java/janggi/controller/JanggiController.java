package janggi.controller;

import janggi.application.dto.GameDto;
import janggi.application.dto.GameRoomDto;
import janggi.application.GameService;
import janggi.domain.exception.DomainException;
import janggi.domain.board.DefaultBoardDesignPolicy;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.Game;
import janggi.domain.game.RoomName;
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
import java.util.Optional;
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
        GameDto gameDto = startGame();

        Game game = gameDto.game();
        while (game.winner().isEmpty()) {
            printScore(game);
            gameDto = processMove(gameDto);
            game = gameDto.game();
        }

        outputView.printWinner(game.winner().get());
    }

    private GameDto startGame() {
        Optional<GameDto> optionalGameDto = Optional.empty();
        while (optionalGameDto.isEmpty()) {
            int gameOption = getUntilValid(inputView::readGameOption);
            if (gameOption == 1) {
                optionalGameDto = createNewGame();
            } else if (gameOption == 2) {
                optionalGameDto = loadGame();
            }
        }
        GameDto gameDto = optionalGameDto.get();
        Game game = gameDto.game();
        outputView.printBoard(BoardDto.from(game.boardMap()));
        return gameDto;
    }

    private Optional<GameDto> createNewGame() {
        return Optional.ofNullable(getUntilValid(() -> {
            RoomName roomName = new RoomName(inputView.readRoomName());
            Map<Dynasty, HorseElephantPosition> horseElephantPositions = readDynastyHorseElephantPositionMap();
            return gameService.createGame(new DefaultBoardDesignPolicy(horseElephantPositions), roomName.roomName(), LocalDateTime.now());
        }));
    }

    private Optional<GameDto> loadGame() {
        List<GameRoomDto> recentlyPlayedGames = gameService.getRecentlyPlayedGames();
        outputView.printGameList(recentlyPlayedGames);

        if (recentlyPlayedGames.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(getUntilValid(() -> {
            Long selectedGameId = getUntilValid(() -> inputView.readSelectedGame(recentlyPlayedGames));
            return gameService.loadGame(selectedGameId);
        }));
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

    private GameDto processMove(GameDto gameDto) {
        Position from = getUntilValid(() -> {
            Game game = gameDto.game();
            Position wantToMove = readPieceWantToMove(game);
            findCanMovePosition(game, wantToMove);
            return wantToMove;
        });

        return getUntilValid(() -> {
            Position to = readPositionToMove();
            return movePiece(gameDto, from, to);
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

    private GameDto movePiece(GameDto gameDto, Position from, Position to) {
        GameDto updatedGame = gameService.movePiece(gameDto.id(), from, to, LocalDateTime.now());

        outputView.printBoard(BoardDto.from(updatedGame.game().boardMap()));
        return updatedGame;
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

}
