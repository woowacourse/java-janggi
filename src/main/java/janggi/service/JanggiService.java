package janggi.service;


import janggi.dao.GameRoomDao;
import janggi.dao.BoardDao;
import janggi.domain.board.Position;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import java.sql.Connection;
import java.util.Map;

public class JanggiService {
    private final BoardDao boardDao;
    private final GameRoomDao gameRoomDao;

    private final int roomId;

    public JanggiService(Connection connection, int roomId) {
        this.boardDao = new BoardDao(connection);
        this.gameRoomDao = new GameRoomDao(connection);
        this.roomId = roomId;
    }

    public void updateMoveResult(Position source, Position destination, PieceType pieceType, TeamColor teamColor) {
        boardDao.update(roomId, source, destination, pieceType, teamColor);
    }

    public void updateGameRoom(TeamColor teamColor, Map<TeamColor, Integer> teamScore) {
        int redScore = teamScore.getOrDefault(TeamColor.RED, 0);
        int blueScore = teamScore.getOrDefault(TeamColor.BLUE, 0);

        gameRoomDao.updateGameRoom(roomId, teamColor, redScore, blueScore);
    }

    public void finishGame(TeamColor winnerColor) {
        gameRoomDao.finishGame(roomId, winnerColor);
    }
}
