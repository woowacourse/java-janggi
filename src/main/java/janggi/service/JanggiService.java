package janggi.service;


import janggi.dao.BoardPieceDao;
import janggi.dao.GameRoomDao;
import janggi.domain.board.Position;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import janggi.entity.GameRoomEntity;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Map;

public class JanggiService {
    private final BoardPieceDao boardPieceDao;
    private final GameRoomDao gameRoomDao;

    private final int roomId;

    public JanggiService(Connection connection, int roomId) {
        this.boardPieceDao = new BoardPieceDao(connection);
        this.gameRoomDao = new GameRoomDao(connection);
        this.roomId = roomId;
    }

    public void updateMoveResult(Position source, Position destination, PieceType pieceType, TeamColor teamColor) {
        boardPieceDao.update(roomId, source, destination, pieceType, teamColor);
    }

    public void updateGameRoom(TeamColor teamColor, Map<TeamColor, Integer> teamScore) {
        int redScore = teamScore.getOrDefault(TeamColor.RED, 0);
        int blueScore = teamScore.getOrDefault(TeamColor.BLUE, 0);

        GameRoomEntity gameRoomEntity = gameRoomDao.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 GameRoom이 존재하지 않습니다, roomId: " + roomId));

        gameRoomEntity.setTurnColor(teamColor.name());
        gameRoomEntity.setRedScore(redScore);
        gameRoomEntity.setBlueScore(blueScore);
        gameRoomEntity.setLastUpdated(LocalDateTime.now());

        gameRoomDao.updateGameRoom(gameRoomEntity);
    }

    public void finishGame(TeamColor winnerColor) {

        GameRoomEntity gameRoomEntity = gameRoomDao.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 GameRoom이 존재하지 않습니다, roomId: " + roomId));

        gameRoomEntity.setFinished(true);
        gameRoomEntity.setWinner(winnerColor.name());
        gameRoomEntity.setEndTime(LocalDateTime.now());
        gameRoomDao.finishGame(gameRoomEntity);
    }
}
