package janggi.service;

import janggi.domain.Position;
import janggi.domain.game.GameStatus;
import janggi.domain.game.TurnManager;
import janggi.domain.piece.Piece;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.global.Pair;
import janggi.infrastructure.entity.GameEntity;
import janggi.infrastructure.mapper.TurnManagerMapper;
import janggi.infrastructure.repository.BoardCellRepository;
import janggi.infrastructure.repository.GameRepository;
import java.util.List;
import java.util.Optional;

public class GameService {

    private final GameRepository gameRepository;
    private final BoardCellRepository boardCellRepository;

    public GameService(final GameRepository gameRepository,
        final BoardCellRepository boardCellRepository) {
        this.gameRepository = gameRepository;
        this.boardCellRepository = boardCellRepository;
    }

    public List<Long> getAllGameInProgressIds(final int limit) {
        return gameRepository.findByStatusOrderByLatest(GameStatus.IN_PROGRESS, limit);
    }

    public List<String> getAllGameNamesInProgress(final int limit) {
        return gameRepository.findByStatusOrderByLatest(GameStatus.IN_PROGRESS, limit)
            .stream()
            .map(gameRepository::findById)
            .flatMap(Optional::stream)
            .map(GameEntity::name).toList();
    }

    public long createNewGame(final String name, final TurnManager turnManager) {
        final List<TeamType> teamQueue = turnManager.getTeams()
            .stream()
            .map(Team::getTeamType)
            .toList();
        final GameEntity gameEntity = GameEntity.from(name, turnManager.getTurnTaken(), teamQueue,
            GameStatus.IN_PROGRESS);

        return gameRepository.save(gameEntity);
    }

    public Pair<String, TurnManager> loadGame(final long id) {
        return gameRepository.findById(id)
            .map(gameEntity ->
                new Pair<>(gameEntity.name(), TurnManagerMapper.toDomain(gameEntity)))
            .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 게임이 존재하지 않습니다."));
    }

    public Team getCurrentTeam(final long id) {
        return gameRepository.findById(id)
            .map(TurnManagerMapper::toDomain)
            .map(TurnManager::getCurrentTeam)
            .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 게임이 존재하지 않습니다."));
    }

    public void progressTurn(
        final long gameId,
        final Position from,
        final Position to,
        final Piece piece
    ) {
        final TurnManager turnManager =
            TurnManagerMapper.toDomain(gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 게임이 존재하지 않습니다.")));
        turnManager.progressToNext();
        boardCellRepository.upsertByPositionAndGameId(to, gameId, piece);
        boardCellRepository.deleteByPositionAndGameId(from, gameId);
        gameRepository.updateById(
            gameId,
            TurnManagerMapper.toEntity(
                turnManager.getTurnTaken(), turnManager.getTeams(), GameStatus.IN_PROGRESS)
        );
    }

    public void closeGame(final long id) {
        gameRepository.updateStatusById(id, GameStatus.CLOSED);
    }
}
