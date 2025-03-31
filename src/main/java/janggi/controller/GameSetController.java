package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.board.BoardSetup;
import janggi.domain.piece.TeamColor;
import janggi.dto.GameRoomDto;
import janggi.dto.SetInfoDto;
import janggi.service.GameSetService;
import janggi.view.GameSettingView;
import java.util.List;

public class GameSetController {
    private final GameSettingView gameSettingView;
    private final GameSetService gameSetService;

    public GameSetController(GameSettingView gameSettingView, GameSetService gameSetService) {
        this.gameSettingView = gameSettingView;
        this.gameSetService = gameSetService;
    }

    public SetInfoDto setJanggiGame() {
        return RetryUtil.getWithRetry(() -> {
            MainOption mainOption = RetryUtil.getWithRetry(gameSettingView::readMainOption);

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
        return RetryUtil.getWithRetry(() -> {
            int setNumber = gameSettingView.readBoardSetup(teamColor);
                    return BoardSetup.from(setNumber);
        });
    }

    private SetInfoDto setPlayedGame() {
        List<GameRoomDto> allPlayingRooms = getGameRoomDtos();
        gameSettingView.printRooms(allPlayingRooms);

        int selectedIndex = RetryUtil.getWithRetry(() -> getSelectedIndexFromUser(allPlayingRooms));
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
}
