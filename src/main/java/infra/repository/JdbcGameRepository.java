package infra.repository;

import domain.Game;
import domain.Team;
import infra.dao.CurrentPiecePositionDao;
import infra.dao.FormationDao;
import infra.dao.GameDao;
import infra.entity.CurrentPiecePositionEntity;
import infra.entity.GameEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JdbcGameRepository implements GameRepository {
    private final GameDao gameDao;
    private final CurrentPiecePositionDao currentPiecePositionDao;
    private final FormationDao formationDao;

    public JdbcGameRepository(GameDao gameDao, CurrentPiecePositionDao currentPiecePositionDao,
                              FormationDao formationDao) {
        this.gameDao = gameDao;
        this.currentPiecePositionDao = currentPiecePositionDao;
        this.formationDao = formationDao;
    }

    @Override
    public void save(Game game) {
        Map<Team, Long> formationId = findFormationId(game);
        Long gameId = saveGame(formationId, game);
        saveCurrentPiecePositions(game, gameId);
    }

    /**
     * 헬퍼 메서드
     */
    private Map<Team, Long> findFormationId(Game game) {
        Map<Team, Long> formationIds = new HashMap<>();

        Long choFormationId = formationDao.findIdByNameAndTeam(game.getHorseElephantFormation(Team.CHO),
                Team.CHO.getKoreanName());
        Long hanFormationId = formationDao.findIdByNameAndTeam(game.getHorseElephantFormation(Team.HAN),
                Team.HAN.getKoreanName());

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
}
