package janggi.service;

import janggi.dao.GameRoomDAO;
import janggi.dao.PieceDAO;
import janggi.domain.Board;
import janggi.domain.GameRoom;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.dto.TeamMaSangPositionDto;
import janggi.factory.PieceInitFactory;
import janggi.factory.masang.MaSangFactory;
import java.util.List;
import java.util.Map;

public class JanggiService {

    private final GameRoomDAO gameRoomDAO;
    private final PieceDAO pieceDAO;

    public JanggiService(GameRoomDAO gameRoomDAO, PieceDAO pieceDAO) {
        this.gameRoomDAO = gameRoomDAO;
        this.pieceDAO = pieceDAO;
    }

    public void checkExistRoom() {
        if (gameRoomDAO.findAllNames().isEmpty()) {
            throw new IllegalArgumentException("방이 존재하지 않습니다.");
        }
    }

    public GameRoom newGameRoom(String gameRoomName, TeamMaSangPositionDto maSangPositionByCho,
                                TeamMaSangPositionDto maSangPositionByHan) {
        if (gameRoomDAO.exist(gameRoomName)) {
            throw new IllegalArgumentException("이미 존재하는 방입니다. 다시 입력해주세요!");
        }

        gameRoomDAO.create(gameRoomName);

        Board board = initializeBoard(maSangPositionByCho, maSangPositionByHan);
        pieceDAO.saveAll(gameRoomName, board);

        return new GameRoom(gameRoomName, board, Team.CHO);
    }

    public GameRoom loadGameRoom(String gameRoomName) {
        if (!gameRoomDAO.exist(gameRoomName)) {
            throw new IllegalArgumentException("존재하지 않는 방입니다. 다시 입력해주세요!");
        }

        Board board = pieceDAO.toDomain(gameRoomName);
        Team turn = gameRoomDAO.findTurn(gameRoomName);
        return new GameRoom(gameRoomName, board, turn);
    }

    private Board initializeBoard(TeamMaSangPositionDto maSangPositionByCho,
                                  TeamMaSangPositionDto maSangPositionByHan) {
        Map<Position, Piece> initializeBoard = PieceInitFactory.initialize();

        initializeBoard.putAll(MaSangFactory.create(maSangPositionByCho.maSangPosition(), maSangPositionByCho.team()));
        initializeBoard.putAll(MaSangFactory.create(maSangPositionByHan.maSangPosition(), maSangPositionByHan.team()));

        return new Board(initializeBoard);
    }

    public List<String> getAllGameRoomName() {
        return gameRoomDAO.findAllNames();
    }

    public void saveGameRoom(String gameRoomName, Team turn) {
        gameRoomDAO.save(gameRoomName, turn);
    }

    public void deleteGameRoomIfNotEnd(GameRoom gameRoom, boolean isNotClosed) {
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
        pieceDAO.movePiece(gameRoomName, currentPosition, targetPosition);
    }
}
