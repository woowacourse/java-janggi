package janggi.service;

import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.PieceInfo;
import janggi.domain.GameSession;
import janggi.dao.PieceEntity;
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
        pieceDao.saveAll(toPieceEntity(gameId, board.getPieces()));
        return new GameSession(gameId, board, initialTurn);
    }

    private List<PieceEntity> toPieceEntity(long gameId, List<PieceInfo> pieceInfo) {
        return pieceInfo.stream()
            .map(snapshot -> new PieceEntity(
                gameId,
                snapshot.x(),
                snapshot.y(),
                snapshot.team(),
                snapshot.pieceType()
            ))
            .toList();
    }

    public List<Long> loadPastGameIds() {
        return gameDao.findGameIds();
    }

    public GameSession loadPastGame(long gameId) {
        validateGameExists(gameId);

        Board board = loadBoard(gameId);
        Team turn = loadTurn(gameId);

        return new GameSession(gameId, board, turn);
    }

    private Board loadBoard(long gameId) {
        List<PieceEntity> pieceData = pieceDao.findByGameId(gameId);
        return Board.from(toPieceInfo(pieceData));
    }

    private Team loadTurn(long gameId) {
        return gameDao.findTurnByGameId(gameId);
    }

    public void validateFrom(long gameId, Board board, Position from) {
        validateGameExists(gameId);

        Team currentTurn = gameDao.findTurnByGameId(gameId);
        board.validateFromPiece(currentTurn, from);
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
        if (!gameDao.existsById(gameId)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 게임입니다.");
        }
    }

    private List<PieceInfo> toPieceInfo(List<PieceEntity> pieces) {
        return pieces.stream()
            .map(piece -> new PieceInfo(
                piece.x(),
                piece.y(),
                piece.team(),
                piece.pieceType()
            ))
            .toList();
    }
}
