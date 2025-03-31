package janggi.service;

import janggi.dao.BoardDAO;
import janggi.dao.GameRoomDAO;
import janggi.domain.Board;
import janggi.domain.GameRoom;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.dto.TeamHorseElephantPositionDto;
import janggi.factory.PieceInitFactory;
import janggi.factory.horse_elephant.HorseElephantFactory;
import java.util.List;
import java.util.Map;

public class JanggiService {

    private final GameRoomDAO gameRoomDAO;
    private final BoardDAO boardDAO;

    public JanggiService(GameRoomDAO gameRoomDAO, BoardDAO boardDAO) {
        this.gameRoomDAO = gameRoomDAO;
        this.boardDAO = boardDAO;
    }

    public void validateNewGameRoomName(String gameRoomName) {
        if (gameRoomDAO.exist(gameRoomName)) {
            throw new IllegalArgumentException("해당 이름의 방이 존재합니다!");
        }
    }

    public void checkExistRoom() {
        if (gameRoomDAO.findAllNames().isEmpty()) {
            throw new IllegalArgumentException("방이 존재하지 않습니다.");
        }
    }

    public GameRoom newGameRoom(String gameRoomName, TeamHorseElephantPositionDto maSangPositionByCho,
                                TeamHorseElephantPositionDto maSangPositionByHan) {
        if (gameRoomDAO.exist(gameRoomName)) {
            throw new IllegalArgumentException("이미 존재하는 방입니다. 다시 입력해주세요!");
        }
        validateTeamPositionDto(maSangPositionByCho, maSangPositionByHan);

        gameRoomDAO.create(gameRoomName);

        Board board = initializeBoard(maSangPositionByCho, maSangPositionByHan);
        boardDAO.save(gameRoomName, board);

        return new GameRoom(gameRoomName, board, Team.CHO);
    }

    private void validateTeamPositionDto(TeamHorseElephantPositionDto maSangPositionByCho,
                                         TeamHorseElephantPositionDto maSangPositionByHan) {
        if (!maSangPositionByCho.team().isSameSide(Team.CHO)) {
            throw new IllegalArgumentException("초나라의 포지션 정보를 가져와야 합니다!");
        }

        if (!maSangPositionByHan.team().isSameSide(Team.HAN)) {
            throw new IllegalArgumentException("한나라의 포지션 정보를 가져와야 합니다!");
        }
    }

    public GameRoom loadGameRoom(String gameRoomName) {
        if (!gameRoomDAO.exist(gameRoomName)) {
            throw new IllegalArgumentException("존재하지 않는 방입니다. 다시 입력해주세요!");
        }

        Board board = boardDAO.toDomain(gameRoomName);
        Team turn = gameRoomDAO.findTurn(gameRoomName);
        return new GameRoom(gameRoomName, board, turn);
    }

    private Board initializeBoard(TeamHorseElephantPositionDto maSangPositionByCho,
                                  TeamHorseElephantPositionDto maSangPositionByHan) {
        Map<Position, Piece> initializeBoard = PieceInitFactory.initialize();

        initializeBoard.putAll(HorseElephantFactory.create(maSangPositionByCho.horseElephantPosition(), maSangPositionByCho.team()));
        initializeBoard.putAll(HorseElephantFactory.create(maSangPositionByHan.horseElephantPosition(), maSangPositionByHan.team()));

        return new Board(initializeBoard);
    }

    public List<String> getAllGameRoomName() {
        return gameRoomDAO.findAllNames();
    }

    public void saveGameRoom(String gameRoomName, Team turn) {
        gameRoomDAO.update(gameRoomName, turn);
    }

    public void deleteGameRoomIfEnd(GameRoom gameRoom, boolean isNotClosed) {
        Board board = gameRoom.board();
        Team turn = gameRoom.turn();
        String gameRoomName = gameRoom.name();

        if (isNotClosed && !board.hasGeneral(turn.reverse())) {
            gameRoomDAO.delete(gameRoomName);
        }
    }

    public void movePiece(GameRoom gameRoom, Position currentPosition, Position targetPosition) {
        Board board = gameRoom.board();
        String gameRoomName = gameRoom.name();

        board.movePiece(currentPosition, targetPosition);
        boardDAO.movePiece(gameRoomName, currentPosition, targetPosition);
    }
}
