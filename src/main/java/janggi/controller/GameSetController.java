package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.board.BoardSetup;
import janggi.domain.piece.TeamColor;
import janggi.dto.GameRoomDto;
import janggi.dto.SetInfoDto;
import janggi.service.GameSetService;
import janggi.view.GameSettingView;
import java.util.List;
import java.util.function.Supplier;

public class GameSetController {
    private final GameSettingView gameSettingView;
    private final GameSetService gameSetService;

    public GameSetController(GameSettingView gameSettingView, GameSetService gameSetService) {
        this.gameSettingView = gameSettingView;
        this.gameSetService = gameSetService;
    }

    public SetInfoDto setJanggiGame() {
        return getWithRetry(() -> {
            MainOption mainOption = getWithRetry(gameSettingView::readMainOption);

            if (mainOption == MainOption.NEW_GAME) {
                return setNewGame();
            }
            return setPlayedGame();
        });
    }

    private SetInfoDto setNewGame() {
        BoardSetup redSetup = readBoardSetup(TeamColor.RED);
        BoardSetup blueSetup = readBoardSetup(TeamColor.BLUE);

        return gameSetService.createNewGame(redSetup, blueSetup);
    }

    private BoardSetup readBoardSetup(TeamColor teamColor) {
        return getWithRetry(() -> {
            int setNumber = gameSettingView.readBoardSetup(teamColor);
                    return BoardSetup.from(setNumber);
        });
    }

    private SetInfoDto setPlayedGame() {
        List<GameRoomDto> allPlayingRooms = getGameRoomDtos();
        gameSettingView.printRooms(allPlayingRooms);

        int selectedIndex = getWithRetry(() -> getSelectedIndexFromUser(allPlayingRooms));
        int roomId = allPlayingRooms.get(selectedIndex).roomId();

        JanggiGame game = gameSetService.getGameByRoomId(roomId);
        return new SetInfoDto(game, roomId);
    }

    private List<GameRoomDto> getGameRoomDtos() {
        List<GameRoomDto> allPlayingRooms = gameSetService.getAllPlayingRooms();
        if (allPlayingRooms.isEmpty()) {
            throw new IllegalArgumentException("진행 중인 게임이 없습니다.");
        }
        return allPlayingRooms;
    }

    private int getSelectedIndexFromUser(List<GameRoomDto> allPlayingRooms) {
        int selectedNumber = gameSettingView.readRoomSelectNumber();

        int selectedIndex = selectedNumber - 1;
        if (selectedIndex < 0 || selectedIndex >= allPlayingRooms.size()) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        return selectedIndex;
    }

    private static <T> T getWithRetry(Supplier<T> task) {
        int attempts = 0;
        while (attempts < 100) {
            try {
                return task.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                attempts++;
            }
        }
        throw new IllegalStateException("최대 재시도 횟수(100)를 초과했습니다. 프로그램을 종료합니다.");
    }
}
