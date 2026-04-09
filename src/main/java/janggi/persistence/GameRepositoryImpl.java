package janggi.persistence;


import janggi.domain.Janggi;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.persistence.dao.GameDao;
import janggi.persistence.dao.PieceDao;
import janggi.persistence.entity.GameEntity;
import janggi.persistence.entity.PieceEntity;
import janggi.persistence.entity.vo.Status;
import janggi.persistence.entity.vo.Turn;
import janggi.persistence.mapper.GameMapper;
import janggi.persistence.mapper.PieceMapper;
import janggi.service.GameRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameRepositoryImpl implements GameRepository {
    private final GameDao gameDao;
    private final PieceDao pieceDao;
    private final GameMapper gameMapper;
    private final PieceMapper pieceMapper;

    private final Map<String, Janggi> activeGames = new ConcurrentHashMap<>();

    public GameRepositoryImpl(GameDao gameDao, PieceDao pieceDao, GameMapper gameMapper, PieceMapper pieceMapper) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
        this.gameMapper = gameMapper;
        this.pieceMapper = pieceMapper;
    }

    @Override
    public String save(String name, Janggi janggi) {
        String newGameId = UUID.randomUUID().toString();

        activeGames.put(newGameId, janggi.clone());

        GameEntity gameEntity = gameMapper.toGameEntity(newGameId, name, janggi);
        gameDao.create(gameEntity);

        List<PieceEntity> pieceEntities = pieceMapper.toEntity(newGameId, janggi);
        pieceDao.createAll(pieceEntities);

        return newGameId;
    }

    @Override
    public Optional<Janggi> findById(String gameId) {
        Janggi janggi = activeGames.get(gameId);
        if (janggi != null) {
            return Optional.of(janggi.clone());
        }

        Optional<GameEntity> gameEntityOpt = gameDao.findById(gameId);
        if (gameEntityOpt.isEmpty()) {
            return Optional.empty();
        }

        GameEntity gameEntity = gameEntityOpt.get();
        List<PieceEntity> pieceEntities = pieceDao.findByGameId(gameId);
        Map<Position, Piece> board = pieceMapper.toDomainBoard(pieceEntities);
        Janggi resurrectedJanggi = gameMapper.toDomain(gameEntity, board);

        activeGames.put(gameId, resurrectedJanggi);

        return Optional.of(resurrectedJanggi.clone());
    }

    @Override
    public void update(String gameId, Janggi janggi) {
        gameDao.updateStatus(gameId, Turn.of(janggi.currentCamp()), Status.of(janggi));
        pieceDao.deleteByGameId(gameId);
        pieceDao.createAll(pieceMapper.toEntity(gameId, janggi));
        activeGames.put(gameId, janggi.clone());
    }

    @Override
    public void updateGameResult(String gameId, Janggi janggi) {
        Status status = Status.of(janggi);
        Turn turn = Turn.of(janggi.currentCamp());

        gameDao.updateStatus(gameId, turn, status);

        activeGames.remove(gameId);
    }

    @Override
    public List<String> findAllNames() {
        return gameDao.findAllNames();
    }

    @Override
    public Optional<Janggi> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public void deleteByName(String name) {

    }
}
