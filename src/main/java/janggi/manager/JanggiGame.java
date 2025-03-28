package janggi.manager;

import janggi.domain.Board;
import janggi.domain.GameRoom;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.dto.PositionDto;
import janggi.dto.TeamMaSangPositionDto;
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

        repeatGameTurns(gameRoom);

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

        TeamMaSangPositionDto maSangPositionByCho = RecoveryUtil.executeWithRetry(
                () -> viewer.settingMaSangPlacement(Team.CHO));
        TeamMaSangPositionDto maSangPositionByHan = RecoveryUtil.executeWithRetry(
                () -> viewer.settingMaSangPlacement(Team.HAN));

        return janggiService.newGameRoom(gameRoomName, maSangPositionByCho, maSangPositionByHan);
    }

    private void repeatGameTurns(GameRoom gameRoom) {
        Board board = gameRoom.board();
        String gameRoomName = gameRoom.name();
        Team turn = gameRoom.turn();

        // TODO 커넥션을 넣어서 처리해야한다.
        // 에러가 났을 경우 롤백이 될 수 있도록 해야한다.
        boolean isNotClosed = true;
        while (isNotClosed && board.hasGeneral(turn.reverse())) {
            viewer.printBoard(board);
            viewer.printTurnInfo(turn);
            isNotClosed = chooseOption(gameRoom, turn);

            janggiService.saveGameRoom(gameRoomName, turn);
            turn = turn.reverse();
        }

        janggiService.deleteGameRoomIfNotEnd(gameRoom, isNotClosed);
    }

    // TODO 재귀 메모리 해제하도록하기
    private boolean chooseOption(GameRoom gameRoom, Team turn) {
        Board board = gameRoom.board();
        PlayerOption playerOption = RecoveryUtil.executeWithRetry(viewer::readChooseOption);

        if (playerOption == PlayerOption.SELECT_PIECE) {
            Position position = RecoveryUtil.executeWithRetry(() -> choosePiece(board, turn));
            RecoveryUtil.executeWithRetry(() -> movePiece(gameRoom.board(), position));
        }

        if (playerOption == PlayerOption.CHECK_SCORE) {
            viewer.printScore(board);
        }

        if (playerOption == PlayerOption.CLOSE) {
            return false;
        }

        if (playerOption != PlayerOption.SELECT_PIECE) {
            return chooseOption(gameRoom, turn);
        }

        return true;
    }

    private Position choosePiece(Board board, Team turn) {
        PositionDto positionDto = viewer.readPieceSelection();
        Position position = Position.of(positionDto.row(), positionDto.column());
        board.checkMoveablePiece(turn, position);

        return position;
    }

    private void movePiece(Board board, Position currentPosition) {
        PositionDto positionDto = viewer.readMove(board.getPiece(currentPosition));

        Position targetPosition = Position.of(positionDto.row(), positionDto.column());

        janggiService.movePiece(board, currentPosition, targetPosition);
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
