package janggi.service;


import janggi.dao.GameRoomDao;
import janggi.dao.PiecePositionDao;
import janggi.domain.board.Position;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import java.util.Map;

public class JanggiDBService {
    private final int boardId;
    private final int roomId;

    public JanggiDBService(int boardId, int roomId) {
        this.boardId = boardId;
        this.roomId = roomId;
    }

    private final PiecePositionDao piecePositionDao = new PiecePositionDao();
    private final GameRoomDao gameRoomDao = new GameRoomDao();

    public void updateMoveResult(Position source, Position destination, PieceType pieceType, TeamColor teamColor) {
        piecePositionDao.updatePiecePosition(boardId, source, destination, pieceType, teamColor);
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
