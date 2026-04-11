package infra.repository;

import controller.dto.MovedPieceRequest;
import domain.Game;
import domain.HorseElephantFormation;
import domain.Team;
import infra.dao.CurrentPiecePositionDao;
import infra.dao.FormationDao;
import infra.dao.GameDao;
import infra.dao.MoveEventDao;
import infra.entity.CurrentPiecePositionEntity;
import infra.entity.GameEntity;
import infra.entity.MoveEventEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JdbcGameRepository implements GameRepository {
    private final GameDao gameDao;
    private final CurrentPiecePositionDao currentPiecePositionDao;
    private final FormationDao formationDao;
    private final MoveEventDao moveEventDao;

    public JdbcGameRepository(GameDao gameDao, CurrentPiecePositionDao currentPiecePositionDao,
                              FormationDao formationDao, MoveEventDao moveEventDao) {
        this.gameDao = gameDao;
        this.currentPiecePositionDao = currentPiecePositionDao;
        this.formationDao = formationDao;
        this.moveEventDao = moveEventDao;
    }

    @Override
    public void save(Game game) {
        Map<Team, Long> formationId = findFormationId(game);
        Long gameId = saveGame(formationId, game);
        saveCurrentPiecePositions(game, gameId);
    }

    @Override
    public List<String> findAllGameNames() {
        return gameDao.findAllGameNames();
    }

    @Override
    public Game findGameByName(String name) {
        GameEntity gameEntity = gameDao.findGameByName(name);
        Game game = findInitialGameFormation(gameEntity);
        replayGame(game, moveEventDao.findByGameId(gameEntity.getId()));
        return game;
    }

    /**
     * 헬퍼 메서드
     */
    private Map<Team, Long> findFormationId(Game game) {
        Map<Team, Long> formationIds = new HashMap<>();

        Long choFormationId = formationDao.findIdByNameAndTeam(game.getHorseElephantFormationName(Team.CHO),
                Team.CHO.name());
        Long hanFormationId = formationDao.findIdByNameAndTeam(game.getHorseElephantFormationName(Team.HAN),
                Team.HAN.name());

        formationIds.put(Team.CHO, choFormationId);
        formationIds.put(Team.HAN, hanFormationId);

        return formationIds;
    }

    private Long saveGame(Map<Team, Long> formationId, Game game) {
        GameEntity gameEntity = GameEntity.createWithoutId(game.getName(),
                game.getCurrentTeamName(),
                formationId.get(Team.CHO),
                formationId.get(Team.HAN));

        return gameDao.save(gameEntity);
    }

    private void saveCurrentPiecePositions(Game game, Long gameId) {
        List<CurrentPiecePositionEntity> pieceEntities = game.getCurrentBoardStatus().stream()
                .map(status -> CurrentPiecePositionEntity.createWithoutId(
                        gameId,
                        status.pieceType(),
                        status.team(),
                        status.row(),
                        status.column()
                ))
                .collect(Collectors.toList());
        currentPiecePositionDao.save(gameId, pieceEntities);
    }

    private Game findInitialGameFormation(GameEntity gameEntity) {
        String choFormationName = formationDao.findNameById(gameEntity.getChoFormationId());
        String hanFormationName = formationDao.findNameById(gameEntity.getHanFormationId());

        return new Game(gameEntity.getName(),
                Map.of(Team.CHO, HorseElephantFormation.valueOf(choFormationName),
                        Team.HAN, HorseElephantFormation.valueOf(hanFormationName)));
    }

    private void replayGame(Game game, List<MoveEventEntity> events) {
        for (MoveEventEntity event : events) {
            MovedPieceRequest request = MovedPieceRequest.of(
                    String.format("%d,%d", event.getFromRow(), event.getFromColumn()),
                    String.format("%d,%d", event.getToRow(), event.getToColumn())
            );
            game.movePiece(request);
        }
    }
}
