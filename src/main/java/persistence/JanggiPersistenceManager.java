package persistence;

import persistence.dao.JanggiGameDao;
import persistence.dao.PieceDao;
import persistence.dto.UpdatePieceRequest;
import persistence.entity.JanggiGameEntity;
import persistence.entity.PieceEntity;
import domain.game.JanggiGame;
import java.util.List;
import java.util.Optional;
import persistence.mapper.EntityMapper;
import persistence.transaction.TransactionManager;

public class JanggiPersistenceManager {

    private final TransactionManager transactionManager;
    private final JanggiGameDao janggiGameDao;
    private final PieceDao pieceDao;
    private final EntityMapper entityMapper;

    public JanggiPersistenceManager(
            TransactionManager transactionManager,
            JanggiGameDao janggiGameDao,
            PieceDao pieceDao,
            EntityMapper entityMapper
    ) {
        this.transactionManager = transactionManager;
        this.janggiGameDao = janggiGameDao;
        this.pieceDao = pieceDao;
        this.entityMapper = entityMapper;
    }

    public void create(JanggiGame janggiGame) {
        transactionManager.execute(connection -> {
            Long janggiGameId = janggiGameDao.create(connection, entityMapper.mapToCreateJanggiGameEntity(janggiGame));
            pieceDao.createAll(connection, entityMapper.mapToCreatePieceEntities(janggiGame.getBoard(), janggiGameId));
        });
    }

    public void update(Long janggiGameId, JanggiGame updatedJanggiGame, UpdatePieceRequest updatePieceRequest) {
        transactionManager.execute(connection -> {
            janggiGameDao.update(connection, janggiGameId, entityMapper.mapToUpdateJanggiGameEntity(updatedJanggiGame));
            pieceDao.update(connection, updatePieceRequest.originLocation(), updatePieceRequest.updateLocation());
        });
    }

    public Optional<JanggiGame> findById(Long janggiGameId) {
        return transactionManager.executeQuery(connection -> {
            Optional<JanggiGameEntity> janggiGameEntity = janggiGameDao.findById(connection, janggiGameId);
            List<PieceEntity> pieceEntities = pieceDao.findAllByJanggiGameId(connection, janggiGameId);
            return janggiGameEntity.map(gameEntity -> entityMapper.mapToJanggiGame(gameEntity, pieceEntities));
        });
    }
}
