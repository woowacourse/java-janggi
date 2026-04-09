package janggi.service;

import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.dto.BoardPieceSnapshot;
import janggi.domain.dto.GameSession;
import janggi.domain.dto.PieceData;
import janggi.domain.Position;

import janggi.domain.strategy.BasicPlacementStrategy;
import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;

public class JanggiService {

    private final DataSource dataSource;
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JanggiService(DataSource dataSource, GameDao gameDao, PieceDao pieceDao) {
        this.dataSource = dataSource;
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public GameSession createNewGame() {
        Board board = new Board(new BasicPlacementStrategy());
        Team initialTurn = Team.initialTeam();

        long gameId = gameDao.save(initialTurn);
        pieceDao.saveAll(toPieceData(gameId, board.getPieces()));
        return new GameSession(gameId, board, initialTurn);
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

    public Team loadTurn(long gameId) {
        return gameDao.findTurnByGameId(gameId);
    }

    public void move(long gameId, Board board, Position from, Position to) {
        validateGameExists(gameId);

        Team currentTurn = gameDao.findTurnByGameId(gameId);
        board.move(currentTurn, from, to);

        Team nextTurn = currentTurn.next();

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try {
                pieceDao.move(conn, gameId, from, to);
                gameDao.updateTurn(conn, gameId, nextTurn);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } catch (Exception e) {
            throw new RuntimeException("[ERROR] 이동 저장 중 오류가 발생했습니다.", e);
        }
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
