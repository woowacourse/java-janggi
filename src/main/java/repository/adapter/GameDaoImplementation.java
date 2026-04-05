package repository.adapter;

import domain.GameContext;
import domain.GameId;
import domain.JanggiGame;
import domain.ScoreCalculator;
import domain.board.Board;
import domain.piece.Piece;
import domain.position.Position;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        List<Position> activePositions = new ArrayList<>(currentBoardStatus.keySet());

        List<GamePieceEntity> updatedPieces = compareEntitiesAndDomainForCheckUpdatedInformation(existingEntities,
                activePositions, currentBoardStatus);

        gamePieceJdbcRepository.updateAll(connection, updatedPieces);
    }

    private List<GamePieceEntity> compareEntitiesAndDomainForCheckUpdatedInformation(
            List<GamePieceEntity> existingEntities, List<Position> activePositions, Map<Position, Piece> boardStatus) {

        return existingEntities.stream()
                .map(entity -> synchronizeEntityAndDomain(entity, activePositions, boardStatus))
                .toList();
    }

    private GamePieceEntity synchronizeEntityAndDomain(GamePieceEntity entity, List<Position> activePositions,
                                                       Map<Position, Piece> boardStatus) {
        Optional<Position> positionIfActive = findPositionIfActive(entity, activePositions, boardStatus);

        if (positionIfActive.isPresent()) {
            activePositions.remove(positionIfActive.get());
            return createActiveEntity(entity, positionIfActive.get());
        }

        return createInactiveEntity(entity);
    }

    private Optional<Position> findPositionIfActive(GamePieceEntity entity, List<Position> activePositions,
                                                    Map<Position, Piece> boardStatus) {
        Optional<Position> matchedPosition = findPositionIfNotMovedAndActive(entity, activePositions, boardStatus);

        if (matchedPosition.isPresent()) {
            return matchedPosition;
        }

        return findPositionMovedButActive(entity, activePositions, boardStatus);
    }

    private Optional<Position> findPositionMovedButActive(GamePieceEntity entity, List<Position> activePositions,
                                                          Map<Position, Piece> boardStatus) {
        return activePositions.stream()
                .filter(pos -> isSamePieceTeamAndType(entity, boardStatus.get(pos)))
                .findFirst();
    }

    private Optional<Position> findPositionIfNotMovedAndActive(GamePieceEntity entity, List<Position> activePositions,
                                                               Map<Position, Piece> boardStatus) {
        return activePositions.stream()
                .filter(pos -> isSamePieceTeamAndType(entity, boardStatus.get(pos)))
                .filter(pos -> isSamePosition(entity, pos))
                .findFirst();
    }

    private boolean isSamePieceTeamAndType(GamePieceEntity entity, Piece piece) {
        String pieceTeam = piece.getTeam().name();
        String pieceType = piece.getPieceType().name();
        return pieceTeam.equals(entity.team())
                && pieceType.equals(entity.pieceType());
    }

    private boolean isSamePosition(GamePieceEntity entity, Position position) {
        boolean isSameRow = entity.row() == position.getRow().value();
        boolean isSameColumn = entity.col() == position.getColumn().value();
        return isSameRow && isSameColumn;
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

        Map<Position, Piece> piecePositions = gamePieceEntities.stream()
                .filter(GamePieceEntity::isActive)
                .collect(Collectors.toMap(
                        entity -> Position.of(entity.row(), entity.col()),
                        pieceEntityMapper::toDomain
                ));

        return new JanggiGame(
                new GameId(gameEntity.id()),
                Board.reconstruct(piecePositions),
                gameContextMapper.toDomain(gameEntity),
                new ScoreCalculator()
        );
    }
}
