package janggi.service;

import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.domain.Board;
import janggi.domain.dto.BoardPieceSnapshot;
import janggi.domain.dto.PieceData;
import janggi.domain.Position;

import java.util.List;

public class JanggiService {

    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JanggiService(GameDao gameDao, PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public long createGame(Board board) {
        long gameId = gameDao.save();
        pieceDao.saveAll(toPieceData(gameId, board.getPieces()));
        return gameId;
    }

    private List<PieceData> toPieceData(long gameId, List<BoardPieceSnapshot> snapshots) {
        return snapshots.stream()
            .map(snapshot -> new PieceData(
                gameId,
                snapshot.x(),
                snapshot.y(),
                snapshot.team(),
                snapshot.pieceType()
            ))
            .toList();
    }

    public Board loadGame(long gameId) {
        validateGameExists(gameId);

        List<PieceData> pieceData = pieceDao.findByGameId(gameId);
        return Board.from(toSnapshots(pieceData));
    }

    public void move(long gameId, Board board, Position from, Position to) {
        validateGameExists(gameId);
        board.move(from, to);
        pieceDao.move(gameId, from, to);
    }

    private void validateGameExists(long gameId) {
        if (!gameDao.findById(gameId)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 게임입니다.");
        }
    }

    private List<BoardPieceSnapshot> toSnapshots(List<PieceData> pieces) {
        return pieces.stream()
            .map(piece -> new BoardPieceSnapshot(
                piece.x(),
                piece.y(),
                piece.team(),
                piece.pieceType()
            ))
            .toList();
    }
}
