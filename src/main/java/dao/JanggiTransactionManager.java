package dao;

import dao.dto.UpdatePieceRequest;
import domain.entity.JanggiGameEntity;
import domain.entity.PieceEntity;
import domain.game.JanggiGame;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JanggiTransactionManager {

    private final DataBaseConnector dataBaseConnector;
    private final JanggiGameDao janggiGameDao;
    private final PieceDao pieceDao;
    private final EntityMapper entityMapper;

    public JanggiTransactionManager(
            DataBaseConnector dataBaseConnector,
            JanggiGameDao janggiGameDao,
            PieceDao pieceDao,
            EntityMapper entityMapper
    ) {
        this.dataBaseConnector = dataBaseConnector;
        this.janggiGameDao = janggiGameDao;
        this.pieceDao = pieceDao;
        this.entityMapper = entityMapper;
    }

    public void createTable() {
        try (Connection connection = dataBaseConnector.getConnection()) {
            connection.setAutoCommit(false);

            try {
                pieceDao.dropTable(connection);
                janggiGameDao.dropTable(connection);
                janggiGameDao.createTable(connection);
                pieceDao.createTable(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("[ERROR] 테이블 생성 중 오류 발생하였습니다");
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 중 오류 발생하였습니다", e);
        }
    }

    public void create(JanggiGame janggiGame) {
        try (Connection connection = dataBaseConnector.getConnection()) {
            connection.setAutoCommit(false);

            try {
                Long janggiGameId = janggiGameDao.create(connection, entityMapper.mapToCreateJanggiGameEntity(janggiGame));
                pieceDao.createAll(connection, entityMapper.mapToCreatePieceEntities(janggiGame.getBoard(), janggiGameId));
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("[ERROR] 엔터티 생성 중 오류 발생하였습니다");
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 중 오류 발생하였습니다");
        }
    }

    public void update(Long janggiGameId, JanggiGame updatedJanggiGame, UpdatePieceRequest updatePieceRequest) {
        try (Connection connection = dataBaseConnector.getConnection()) {
            connection.setAutoCommit(false);

            try {
                janggiGameDao.update(connection, janggiGameId, entityMapper.mapToUpdateJanggiGameEntity(updatedJanggiGame));
                pieceDao.update(connection, updatePieceRequest.originLocation(), updatePieceRequest.updateLocation());
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("[ERROR] 엔터티 수정 중 오류 발생하였습니다", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 중 오류 발생하였습니다");
        }
    }

    public Optional<JanggiGame> findById(Long id) {
        try (Connection connection = dataBaseConnector.getConnection()) {

            try {
                Optional<JanggiGameEntity> janggiGameEntity = janggiGameDao.findById(connection, id);
                List<PieceEntity> pieceEntities = pieceDao.findAllByJanggiGameId(connection, id);
                return janggiGameEntity.map(gameEntity -> entityMapper.mapToJanggiGame(gameEntity, pieceEntities));
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("[ERROR] 게임 조회 중 오류 발생하였습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 중 오류 발생하였습니다");
        }
    }
}
