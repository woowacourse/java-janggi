package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.board.BoardSetup;
import janggi.domain.board.InitialBoard;
import janggi.domain.board.PlayingBoard;
import janggi.domain.gameState.BlueTurn;
import janggi.domain.piece.TeamColor;
import janggi.dto.GameRoomDto;
import janggi.dto.SetInfoDto;
import janggi.service.GameSetDBService;
import janggi.view.GameSettingView;
import java.util.HashMap;
import java.util.List;

public class GameSetController {
    private final GameSettingView gameSettingView;

    public GameSetController(GameSettingView gameSettingView, GameSetDBService gameSetDBService) {
        this.gameSettingView = gameSettingView;
        this.gameSetDBService = gameSetDBService;
    }

    private final GameSetDBService gameSetDBService;

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
        InitialBoard initialBoard = RetryUtil.getWithRetry(this::setupBoard);
        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());
        JanggiGame newGame = new JanggiGame(new BlueTurn(playingBoard), new HashMap<>());

        int roomId = gameSetDBService.createNewGameRoomAndGetId(TeamColor.BLUE);

        gameSetDBService.saveInitialBoard(roomId, initialBoard.getInitialBoard());

        return new SetInfoDto(newGame, roomId);
    }

    private InitialBoard setupBoard() {
        BoardSetup redSetup = getBoardSetup(TeamColor.RED);
        BoardSetup blueSetup = getBoardSetup(TeamColor.BLUE);
        return InitialBoard.createBoard(redSetup, blueSetup);
    }

    private BoardSetup getBoardSetup(TeamColor teamColor) {
        int setNumber = gameSettingView.readBoardSetup(teamColor);
        return BoardSetup.from(setNumber);
    }

    private SetInfoDto setPlayedGame() {
        List<GameRoomDto> allPlayingRooms = getGameRoomDtos();
        gameSettingView.printRooms(allPlayingRooms);

        int selectedIndex = RetryUtil.getWithRetry(() -> getSelectedIndexFromUser(allPlayingRooms));
        int selectedRoomId = allPlayingRooms.get(selectedIndex).roomId();
        JanggiGame game = gameSetDBService.getGameByRoomId(selectedRoomId);

        return new SetInfoDto(game, selectedRoomId);
    }

    private List<GameRoomDto> getGameRoomDtos() {
        List<GameRoomDto> allPlayingRooms = gameSetDBService.getAllPlayingRooms();
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
