package janggi.db.repository;

import janggi.db.connection.DbConnection;
import janggi.db.dao.JanggiGameMySqlDao;
import janggi.db.dao.PieceMySqlDao;
import janggi.db.entity.JanggiGameEntity;
import janggi.db.entity.PieceEntity;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.board.strategy.BoardAssembler;
import janggi.domain.board.strategy.DbRestoreArrangementStrategy;
import janggi.domain.piece.PieceInfo;
import janggi.domain.piece.PieceType;
import janggi.dto.GameDto;
import janggi.exception.DataAccessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GameRepository {

    private final DbConnection dbConnection;
    private final JanggiGameMySqlDao gameDao;
    private final PieceMySqlDao pieceDao;

    public GameRepository(DbConnection dbConnection, JanggiGameMySqlDao gameDao, PieceMySqlDao pieceDao) {
        this.dbConnection = dbConnection;
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public int countOngoingGame() {
        try (Connection connection = dbConnection.getConnection()) {
            return gameDao.selectOngoingGameCount(connection);
        } catch (SQLException e) {
            throw new DataAccessException("진행 중인 게임 개수를 불러오는데 실패했습니다.", e);
        }
    }

    public int saveNewGame(Board board, Side currentTurn) {
        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int gameId = gameDao.insertNewGame(connection, currentTurn.name());
                List<PieceEntity> pieceEntities = getPieceEntities(board, gameId);
                pieceDao.insertNewPieces(connection, pieceEntities);
                connection.commit();
                return gameId;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("새로운 게임을 저장하는데 실패했습니다.", e);
        }
    }

    private List<PieceEntity> getPieceEntities(Board board, int gameId) {
        List<PieceInfo> pieceInfos = board.getAlivePieceInfos();
        return convertPieceInfosToPieceEntities(pieceInfos, gameId);
    }

    private List<PieceEntity> convertPieceInfosToPieceEntities(List<PieceInfo> pieceInfos, int gameId) {
        return pieceInfos.stream()
                .map(pieceInfo -> this.convertPieceInfoToPieceEntity(pieceInfo, gameId))
                .toList();
    }

    private PieceEntity convertPieceInfoToPieceEntity(PieceInfo pieceInfo, int gameId) {
        String pieceType = pieceInfo.type().name();
        String side = pieceInfo.side().name();
        return new PieceEntity(pieceType, side, pieceInfo.rowIndex(), pieceInfo.colIndex(), gameId);
    }

    public GameDto loadOngoingGame() {
        try (Connection connection = dbConnection.getConnection()) {
            JanggiGameEntity gameEntity = gameDao.selectOngoingGame(connection);
            List<PieceEntity> pieceEntities = pieceDao.selectPiecesByGameId(connection, gameEntity.gameId());
            List<PieceInfo> pieceInfos = convertPieceEntitiesToPieceInfos(pieceEntities);
            Board board = Board.create(BoardAssembler.from(List.of(DbRestoreArrangementStrategy.from(pieceInfos))));
            Side side = Side.valueOf(gameEntity.playingSide());
            return new GameDto(gameEntity.gameId(), board, side);
        } catch (SQLException e) {
            throw new DataAccessException("진행 중인 게임 데이터를 불러오는데 실패했습니다.", e);
        }
    }

    private List<PieceInfo> convertPieceEntitiesToPieceInfos(List<PieceEntity> pieceEntities) {
        return pieceEntities.stream()
                .map(this::convertPieceEntityToPieceInfo)
                .toList();
    }

    private PieceInfo convertPieceEntityToPieceInfo(PieceEntity pieceEntity) {
        PieceType pieceType = PieceType.valueOf(pieceEntity.pieceType());
        Side side = Side.valueOf(pieceEntity.side());
        return new PieceInfo(pieceType, side, pieceEntity.rowIndex(), pieceEntity.colIndex());
    }

    public void saveGame(int gameId, Board board, Side currentTurn) {
        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                gameDao.updatePlayingSideByGameId(connection, gameId, currentTurn.name());
                pieceDao.deletePiecesByGameId(connection, gameId);
                List<PieceEntity> pieceEntities = getPieceEntities(board, gameId);
                pieceDao.insertNewPieces(connection, pieceEntities);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("게임 진행 상황을 저장하는데 실패했습니다.", e);
        }
    }

    public void deleteGame(int gameId) {
        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                gameDao.deleteGameByGameId(connection, gameId);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("종료된 게임을 삭제하는데 실패했습니다.", e);
        }
    }
}
