package janggi.service;

import janggi.dao.BoardSnapshotDAO;
import janggi.dao.GameDAO;
import janggi.dao.PieceDAO;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;
import janggi.entity.BoardSnapshot;
import janggi.entity.Game;
import java.util.Map;

public class JanggiService {
    private final GameDAO gameDAO;
    private final BoardSnapshotDAO boardSnapshotDAO;
    private final PieceDAO pieceDAO;

    public JanggiService(final GameDAO gameDAO, final BoardSnapshotDAO boardSnapshotDAO, final PieceDAO pieceDAO) {
        this.gameDAO = gameDAO;
        this.boardSnapshotDAO = boardSnapshotDAO;
        this.pieceDAO = pieceDAO;
    }

    public Map<Position, Piece> getLatestPiecesOrNull() {
        Game game = gameDAO.loadLatestGameOrNull();
        if (game == null) {
            return null;
        }
        BoardSnapshot boardSnapshot = boardSnapshotDAO.loadLatestSnapshotOrNull(game.gameId());

        return pieceDAO.loadPieces(boardSnapshot.snapshotId());
    }

    public void createNewGame() {
        gameDAO.createNewGame();
    }

    public int getLatestGameId() {
        return gameDAO.loadLatestGameOrNull().gameId();
    }

    public void saveGame(final int gameId, final Board board, final Team turn) {
        boardSnapshotDAO.saveBoardSnapshot(gameId, board, turn);
    }

    public void deleteGame(final int gameId) {
        gameDAO.softDeleteGame(gameId);
    }

    public Team loadTurn(final int gameId) {
        BoardSnapshot boardSnapshot = boardSnapshotDAO.loadLatestSnapshotOrNull(gameId);
        return Team.findByName(boardSnapshot.turn());
    }
}