package janggi.service;


import janggi.dao.BoardDao;
import janggi.dao.GameStateDao;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import java.util.Map;

public class JanggiDBService {
    private final BoardDao boardDao = new BoardDao();
    private final GameStateDao gameStateDao = new GameStateDao();

    public void saveInitialBoard(Map<Position, Piece> board) {
        boardDao.saveBoard(board);
    }

    public void updateMoveResult(Position source, Position destination, PieceType pieceType, TeamColor teamColor) {
        boardDao.updatePiecePosition(source, destination, pieceType, teamColor);
    }

    public void saveStartSate(TeamColor teamColor) {
        gameStateDao.saveStartGameState(teamColor);
    }

    public void updateGameState(TeamColor teamColor) {
        int gameId = gameStateDao.getInProgressGameId()
                .orElseThrow(() -> new IllegalStateException("진행 중인 게임이 없습니다."));

        gameStateDao.updateGameState(gameId, teamColor);
    }

    public void finishGame(TeamColor winnerColor) {
        int gameId = gameStateDao.getInProgressGameId()
                .orElseThrow(() -> new IllegalStateException("진행 중인 게임이 없습니다."));

        gameStateDao.finishGame(gameId, winnerColor);
    }
}
