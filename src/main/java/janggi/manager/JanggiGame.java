package janggi.manager;

import janggi.domain.Board;
import janggi.domain.GameRoom;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.dto.PositionDto;
import janggi.dto.TeamHorseElephantPositionDto;
import janggi.service.JanggiService;
import janggi.util.RecoveryUtil;
import janggi.view.GameModeOption;
import janggi.view.PlayerOption;
import janggi.view.Viewer;

public class JanggiGame {

    private final Viewer viewer;
    private final JanggiService janggiService;

    public JanggiGame(Viewer viewer, JanggiService janggiService) {
        this.viewer = viewer;
        this.janggiService = janggiService;
    }

    public void start() {
        GameRoom gameRoom = RecoveryUtil.executeWithRetry(this::chooseGameMode);

        boolean isNotClosed = repeatGameTurns(gameRoom);
        deleteIfEnd(gameRoom, isNotClosed);

        result(gameRoom.board());
    }

    private GameRoom chooseGameMode() {
        GameModeOption gameModeOption = RecoveryUtil.executeWithRetry(viewer::readGameModeOption);

        if (gameModeOption == GameModeOption.LOAD) {
            janggiService.checkExistRoom();
            return RecoveryUtil.executeWithRetry(this::loadGameRoom);
        }

        if (gameModeOption == GameModeOption.NEW) {
            return RecoveryUtil.executeWithRetry(this::newGameRoom);
        }

        throw new IllegalArgumentException("잘못된 옵션입니다.");
    }

    private GameRoom loadGameRoom() {
        viewer.printGameRooms(janggiService.getAllGameRoomName());
        String gameRoomName = viewer.readGameRoomName();

        return janggiService.loadGameRoom(gameRoomName);
    }

    private GameRoom newGameRoom() {
        String gameRoomName = viewer.readGameRoomName();
        janggiService.validateNewGameRoomName(gameRoomName);

        TeamHorseElephantPositionDto horseElephantPositionByCho = RecoveryUtil.executeWithRetry(
                () -> viewer.settingMaSangPlacement(Team.CHO));
        TeamHorseElephantPositionDto horseElephantPositionByHan = RecoveryUtil.executeWithRetry(
                () -> viewer.settingMaSangPlacement(Team.HAN));

        return janggiService.newGameRoom(gameRoomName, horseElephantPositionByCho, horseElephantPositionByHan);
    }

    private boolean repeatGameTurns(GameRoom gameRoom) {
        Board board = gameRoom.board();
        String gameRoomName = gameRoom.name();
        Team turn = gameRoom.turn();

        boolean isNotClosed = true;
        while (isNotClosed && board.hasGeneral(turn.reverse())) {
            viewer.printBoard(board);
            viewer.printTurnInfo(turn);
            isNotClosed = chooseOption(gameRoom, turn);

            janggiService.saveGameRoom(gameRoomName, turn);
            turn = turn.reverse();
        }

        return isNotClosed;
    }

    private void deleteIfEnd(GameRoom gameRoom, boolean isNotClosed) {
        janggiService.deleteGameRoomIfEnd(gameRoom, isNotClosed);
    }

    private boolean chooseOption(GameRoom gameRoom, Team turn) {
        Board board = gameRoom.board();
        PlayerOption playerOption;

        do {
            playerOption = RecoveryUtil.executeWithRetry(viewer::readChooseOption);

            if (playerOption == PlayerOption.SELECT_PIECE) {
                Position position = RecoveryUtil.executeWithRetry(() -> choosePiece(board, turn));
                RecoveryUtil.executeWithRetry(() -> movePiece(gameRoom, position));
            }

            if (playerOption == PlayerOption.CHECK_SCORE) {
                viewer.printScore(board);
            }

            if (playerOption == PlayerOption.CLOSE) {
                return false;
            }
        } while (playerOption != PlayerOption.SELECT_PIECE);

        return true;
    }

    private Position choosePiece(Board board, Team turn) {
        PositionDto positionDto = viewer.readPieceSelection();
        Position position = Position.of(positionDto.row(), positionDto.column());
        board.checkMoveablePiece(turn, position);

        return position;
    }

    private void movePiece(GameRoom gameRoom, Position currentPosition) {
        Board board = gameRoom.board();
        PositionDto positionDto = viewer.readMove(board.getPiece(currentPosition));

        Position targetPosition = Position.of(positionDto.row(), positionDto.column());

        janggiService.movePiece(gameRoom, currentPosition, targetPosition);
    }

    private void result(Board board) {
        if (board.hasGeneral(Team.CHO) && board.hasGeneral(Team.HAN)) {
            viewer.result(board);
            return;
        }

        if (board.hasGeneral(Team.CHO)) {
            viewer.result(Team.CHO);
            return;
        }

        viewer.result(Team.HAN);
    }
}
