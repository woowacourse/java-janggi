package repository.adapter;

import domain.GameContext;
import domain.GameId;
import domain.JanggiGame;
import domain.ScoreCalculator;
import domain.board.Board;
import domain.piece.Piece;
import domain.position.Position;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import repository.dao.GameDao;
import repository.entity.GameEntity;
import repository.entity.GamePieceEntity;
import repository.jdbc.GameJdbcRepository;
import repository.jdbc.GamePieceJdbcRepository;
import repository.mapper.GameContextMapper;
import repository.mapper.PieceEntityMapper;

public class GameDaoImplementation implements GameDao {

    private final GameJdbcRepository gameJdbcRepository;
    private final GamePieceJdbcRepository gamePieceJdbcRepository;
    private final GameContextMapper gameContextMapper;
    private final PieceEntityMapper pieceEntityMapper;

    public GameDaoImplementation(
            GameJdbcRepository gameJdbcRepository,
            GamePieceJdbcRepository gamePieceJdbcRepository,
            GameContextMapper gameContextMapper,
            PieceEntityMapper pieceEntityMapper
    ) {
        this.gameJdbcRepository = gameJdbcRepository;
        this.gamePieceJdbcRepository = gamePieceJdbcRepository;
        this.gameContextMapper = gameContextMapper;
        this.pieceEntityMapper = pieceEntityMapper;
    }

    @Override
    public void initTable(Connection connection) {
        gameJdbcRepository.initTable(connection);
        gamePieceJdbcRepository.initTable(connection);
    }

    @Override
    public GameId save(Connection connection, JanggiGame janggiGame) {
        GameEntity gameEntity = gameContextMapper.toNewEntity(janggiGame.getContext());
        Long gameId = gameJdbcRepository.save(connection, gameEntity);

        Map<Position, Piece> currentBoardStatus = janggiGame.getBoard().getBoardStatus().status();
        List<GamePieceEntity> pieceEntities = currentBoardStatus.keySet().stream()
                .map(key -> convertToEntity(key, gameId, currentBoardStatus))
                .toList();
        gamePieceJdbcRepository.saveAll(connection, pieceEntities);

        return new GameId(gameId);
    }

    private GamePieceEntity convertToEntity(Position key, Long gameId, Map<Position, Piece> currentBoardStatus) {
        return pieceEntityMapper.toNewEntity(
                gameId,
                currentBoardStatus.get(key),
                key
        );
    }

    @Override
    public JanggiGame findGameById(Connection connection, GameId entityId) {
        GameEntity gameEntity = gameJdbcRepository.find(connection, entityId.value());
        return createNewJanggiGameFromDatabaseContents(connection, gameEntity);
    }

    @Override
    public List<GameId> findPlayingGameIds(Connection connection) {
        return gameJdbcRepository.findByState(connection, "PLAYING").stream()
                .map(entity -> new GameId(entity.id()))
                .toList();
    }

    @Override
    public List<JanggiGame> findPlayingGames(Connection connection) {
        return gameJdbcRepository.findByState(connection, "PLAYING").stream()
                .map(entity -> createNewJanggiGameFromDatabaseContents(connection, entity))
                .toList();
    }

    @Override
    public void updateContext(Connection connection, GameId entityId, GameContext newContext) {
        GameEntity updatedEntity = gameContextMapper.toEntity(entityId, newContext);
        gameJdbcRepository.update(connection, entityId.value(), updatedEntity);
    }

    @Override
    public void updateGamePiece(Connection connection, GameId id, JanggiGame game) {
        List<GamePieceEntity> existingEntities = gamePieceJdbcRepository.findByGameId(connection, id.value());
        Map<Position, Piece> currentBoardStatus = game.getBoard().getBoardStatus().status();

        Map<Long, Position> activePiecePositions = extractActivePositions(currentBoardStatus);

        List<GamePieceEntity> updatedPieces = existingEntities.stream()
                .map(entity -> updateEntityState(entity, activePiecePositions))
                .toList();

        gamePieceJdbcRepository.updateAll(connection, updatedPieces);
    }

    private Map<Long, Position> extractActivePositions(Map<Position, Piece> boardStatus) {
        return boardStatus.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getValue().getId().value(),
                                Entry::getKey
                        )
                );
    }

    private GamePieceEntity updateEntityState(GamePieceEntity entity, Map<Long, Position> activePiecePositions) {
        Position currentPosition = activePiecePositions.get(entity.id());

        if (currentPosition != null) {
            return createActiveEntity(entity, currentPosition);
        }

        return createInactiveEntity(entity);
    }

    private GamePieceEntity createActiveEntity(GamePieceEntity entity, Position position) {
        return new GamePieceEntity(
                entity.id(),
                entity.gameId(),
                entity.pieceType(),
                entity.team(),
                position.getRow().value(),
                position.getColumn().value(),
                true
        );
    }

    private GamePieceEntity createInactiveEntity(GamePieceEntity entity) {
        return new GamePieceEntity(
                entity.id(),
                entity.gameId(),
                entity.pieceType(),
                entity.team(),
                entity.row(),
                entity.col(),
                false
        );
    }

    private JanggiGame createNewJanggiGameFromDatabaseContents(Connection connection, GameEntity gameEntity) {
        List<GamePieceEntity> gamePieceEntities = gamePieceJdbcRepository.findByGameId(connection, gameEntity.id());
        Map<Position, Piece> piecePositions = convertGamePieceEntityToDomainType(gamePieceEntities);

        return new JanggiGame(
                new GameId(gameEntity.id()),
                Board.reconstruct(piecePositions),
                gameContextMapper.toDomain(gameEntity),
                new ScoreCalculator()
        );
    }

    private Map<Position, Piece> convertGamePieceEntityToDomainType(List<GamePieceEntity> gamePieceEntities) {
        return gamePieceEntities.stream()
                .filter(GamePieceEntity::isActive)
                .collect(Collectors.toMap(
                        entity -> Position.of(entity.row(), entity.col()),
                        pieceEntityMapper::toDomain
                ));
    }
}
