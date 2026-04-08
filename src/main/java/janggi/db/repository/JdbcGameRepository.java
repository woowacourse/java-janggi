package janggi.db.repository;

import janggi.db.DatabaseConnection;
import janggi.db.dao.JdbcGameDao;
import janggi.db.dao.JdbcPieceDao;
import janggi.db.entity.GameEntity;
import janggi.db.entity.PieceEntity;
import janggi.domain.board.Board;
import janggi.domain.board.MoveResult;
import janggi.domain.board.Position;
import janggi.domain.game.JanggiGame;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JdbcGameRepository implements GameRepository {

    private final DatabaseConnection connection;
    private final JdbcGameDao gameDao;
    private final JdbcPieceDao pieceDao;

    public JdbcGameRepository(DatabaseConnection connection, JdbcGameDao gameDao, JdbcPieceDao pieceDao) {
        this.connection = connection;
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    @Override
    public Long save(JanggiGame game) {
        try (Connection conn = connection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Long gameId = gameDao.insert(conn, game.getTurnName());
                saveAllPieces(conn, gameId, game.getBoard());
                conn.commit();
                return gameId;
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("게임 저장에 실패했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveAllPieces(Connection conn, Long gameId, Board board) {
        Map<Position, Piece> boardPieces = board.getBoard();
        List<PieceEntity> pieceEntities = boardPieces.entrySet().stream()
                .map(entry -> new PieceEntity(
                        null,
                        gameId,
                        entry.getValue().getPieceTypeName(),
                        entry.getValue().getTeamName(),
                        entry.getKey().getX(),
                        entry.getKey().getY()
                ))
                .toList();
        pieceDao.insertAll(conn, gameId, pieceEntities);
    }

    @Override
    public void updateGame(Long gameId, JanggiGame game, MoveResult result) {
        try (Connection conn = connection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                gameDao.update(conn, gameId, game.getTurnName());
                if (result.isCaptured()) {
                    pieceDao.deleteByGameId(conn, gameId, result.getTo().getX(), result.getTo().getY());
                }
                pieceDao.update(conn, gameId,
                        result.getFrom().getX(), result.getFrom().getY(),
                        result.getTo().getX(), result.getTo().getY());
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("게임 업데이트에 실패했습니다.", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터 베이스 연결을 실패했습니다");
        }

    }

    @Override
    public JanggiGame load(Long gameId) {
        try (Connection conn = connection.getConnection()) {
            GameEntity gameEntity = gameDao.selectById(conn, gameId)
                    .orElseThrow(() -> new IllegalArgumentException("진행 중인 게임이 없습니다."));
            List<PieceEntity> pieceEntities = pieceDao.selectByGameId(conn, gameEntity.getId());

            Map<Position, Piece> pieces = new LinkedHashMap<>();
            for (PieceEntity entity : pieceEntities) {
                Position position = new Position(entity.getPositionX(), entity.getPositionY());
                PieceType pieceType = PieceType.valueOf(entity.getPieceType());
                Team team = Team.valueOf(entity.getTeam());
                Piece piece = PieceFactory.create(pieceType, team);
                pieces.put(position, piece);
            }

            Board board = new Board(pieces);
            Team turnTeam = Team.valueOf(gameEntity.getTurn());
            return new JanggiGame(board, turnTeam);
        } catch (SQLException e) {
            throw new RuntimeException("데이터 베이스 연결에 실패했습니다.");
        }
    }

    @Override
    public List<Long> findAllGameIds() {
        try (Connection conn = connection.getConnection()) {
            return gameDao.selectAll(conn).stream()
                    .map(GameEntity::getId)
                    .toList();
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스에 연결에 실패했습니다.");
        }
    }

    @Override
    public void delete(Long gameId) {
        try (Connection conn = connection.getConnection()) {
            gameDao.deleteById(conn, gameId);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스에 연결에 실패했습니다.");
        }
    }
}
