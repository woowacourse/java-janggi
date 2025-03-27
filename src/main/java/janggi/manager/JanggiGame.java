package janggi.manager;

import janggi.dao.BoardDAO;
import janggi.dao.GameRoomDAO;
import janggi.domain.Board;
import janggi.domain.GameRoom;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.dto.PositionDto;
import janggi.factory.PieceInitFactory;
import janggi.factory.masang.MaSangFactory;
import janggi.util.RecoveryUtil;
import janggi.view.GameModeOption;
import janggi.view.MaSangPosition;
import janggi.view.PlayerOption;
import janggi.view.Viewer;
import java.sql.SQLException;
import java.util.Map;

public class JanggiGame {

    private final Viewer viewer;
    private final BoardDAO boardDAO;
    private final GameRoomDAO gameRoomDAO;

    public JanggiGame(Viewer viewer, BoardDAO boardDAO, GameRoomDAO gameRoomDAO) {
        this.viewer = viewer;
        this.boardDAO = boardDAO;
        this.gameRoomDAO = gameRoomDAO;
    }

    public void start() throws SQLException {
        GameRoom gameRoom = RecoveryUtil.executeWithRetry(this::chooseGameMode);

        Team turn = gameRoom.turn();

        repeatGameTurns(gameRoom, turn);

        result(gameRoom.board());
    }

    private GameRoom chooseGameMode() {
        GameModeOption gameModeOption = RecoveryUtil.executeWithRetry(viewer::readGameModeOption);

        if (gameModeOption == GameModeOption.LOAD) {
            checkExistRoom();
            return RecoveryUtil.executeWithRetry(this::loadGameRoom);
        }

        if (gameModeOption == GameModeOption.NEW) {
            return RecoveryUtil.executeWithRetry(this::newGameRoom);
        }

        throw new IllegalArgumentException("잘못된 옵션입니다.");
    }

    private void checkExistRoom() {
        if (gameRoomDAO.findAll().isEmpty()) {
            throw new IllegalArgumentException("방이 존재하지 않습니다.");
        }
    }

    private GameRoom loadGameRoom() {
        viewer.printGameRooms(gameRoomDAO.findAll());
        String gameRoomName = viewer.readGameRoomName();
        if (!gameRoomDAO.exist(gameRoomName)) {
            throw new IllegalArgumentException("존재하지 않는 방입니다. 다시 입력해주세요!");
        }

        Board board = boardDAO.toDomain(gameRoomName);
        Team turn = gameRoomDAO.findTurn(gameRoomName);
        return new GameRoom(gameRoomName, board, turn);
    }

    private GameRoom newGameRoom() {
        String gameRoomName = viewer.readGameRoomName();

        if (gameRoomDAO.exist(gameRoomName)) {
            throw new IllegalArgumentException("이미 존재하는 방입니다. 다시 입력해주세요!");
        }
        gameRoomDAO.create(gameRoomName);
        Board board = initializeBoard();

        boardDAO.saveAll(gameRoomName, board);
        return new GameRoom(gameRoomName, board, Team.CHO);
    }

    private Board initializeBoard() {
        Map<Position, Piece> initializeBoard = PieceInitFactory.initialize();
        initializeBoard.putAll(placeMaSangPiecesBySide(Team.CHO));
        initializeBoard.putAll(placeMaSangPiecesBySide(Team.HAN));

        return new Board(initializeBoard);
    }

    private Map<Position, Piece> placeMaSangPiecesBySide(Team team) {
        MaSangPosition maSangPosition = RecoveryUtil.executeWithRetry(() -> viewer.settingMaSangPlacement(team));

        return MaSangFactory.create(maSangPosition, team);
    }

    private void repeatGameTurns(GameRoom gameRoom, Team turn) {
        Board board = gameRoom.board();
        String gameRoomName = gameRoom.name();

        // TODO 커넥션을 넣어서 처리해야한다.
        // 에러가 났을 경우 롤백이 될 수 있도록 해야한다.
        boolean isNotClosed = true;
        while (isNotClosed && board.hasGeneral(turn.reverse())) {
            viewer.printBoard(board);
            viewer.printTurnInfo(turn);
            isNotClosed = chooseOption(gameRoom, turn);

            gameRoomDAO.save(gameRoomName, turn);
            turn = turn.reverse();
        }

        if (isNotClosed && !board.hasGeneral(turn.reverse())) {
            gameRoomDAO.delete(gameRoomName);
        }
    }

    // TODO 재귀 메모리 해제하도록하기
    private boolean chooseOption(GameRoom gameRoom, Team turn) {
        Board board = gameRoom.board();
        PlayerOption playerOption = RecoveryUtil.executeWithRetry(viewer::readChooseOption);

        if (playerOption == PlayerOption.SELECT_PIECE) {
            Position position = RecoveryUtil.executeWithRetry(() -> choosePiece(board, turn));
            RecoveryUtil.executeWithRetry(() -> movePiece(board, position));
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

        board.movePiece(currentPosition, targetPosition);
        boardDAO.movePiece(currentPosition, targetPosition);
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
