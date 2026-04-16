package janggi.service;

import janggi.domain.Board;
import janggi.domain.BoardFactory;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.dto.GameRoomDto;
import janggi.repository.GameDao;
import janggi.repository.PieceDao;
import java.util.List;
import java.util.Map;

public class JanggiService {
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JanggiService(GameDao gameDao, PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public int createNewGame() {
        Board board = new Board(BoardFactory.generate());
        double choScore = board.calculateScore(Team.CHO);
        double hanScore = board.calculateScore(Team.HAN);

        int gameId = gameDao.createGame("CHO", "PLAYING", choScore, hanScore);

        saveInitialPieces(gameId, board);

        return gameId;
    }

    private void saveInitialPieces(int gameId, Board board) {
        Map<Position, janggi.domain.Piece> pieces = board.getBoard();
        for (Map.Entry<Position, janggi.domain.Piece> entry : pieces.entrySet()) {
            Position pos = entry.getKey();
            janggi.domain.Piece piece = entry.getValue();
            pieceDao.savePiece(gameId, pos.getRowValue(), pos.getColumnValue(), piece.getName(),
                    piece.getTeam().name());
        }
    }

    public void movePiece(int gameId, Board board, Position from, Position to, Team team) {
        boolean isCapture = board.hasPieceAt(to);
        board.move(from, to, team);

        handlePieceDatabaseUpdate(gameId, from, to, isCapture);

        String status = determineGameStatus(board);
        double choScore = board.calculateScore(Team.CHO);
        double hanScore = board.calculateScore(Team.HAN);

        Team nextTeam = team.switchTeam();

        gameDao.updateGameMetadata(gameId, nextTeam.name(), status, choScore, hanScore);
    }

    private void handlePieceDatabaseUpdate(int gameId, Position from, Position to, boolean isCapture) {
        if (isCapture) {
            pieceDao.deleteCapturedPiece(gameId, to.getRowValue(), to.getColumnValue());
        }
        pieceDao.updatePiecePosition(gameId, from.getRowValue(), from.getColumnValue(), to.getRowValue(),
                to.getColumnValue());
    }

    private String determineGameStatus(Board board) {
        if (board.isKingCaptured()) {
            return "FINISHED";
        }
        return "PLAYING";
    }

    public List<GameRoomDto> findAllGames() {
        return gameDao.findAll();
    }
}
