package janggi.domain.pieceaction;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Direction;
import janggi.domain.movement.SlidingMovement;
import janggi.domain.movement.SlidingRule;
import janggi.domain.movement.Rule;
import janggi.domain.team.TeamType;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

public class SoldierAction implements PieceAction {

    private static final EnumMap<TeamType, List<Rule>> TEAM_RULES_MAP;

    static {
        TEAM_RULES_MAP = new EnumMap<>(TeamType.class);
        TEAM_RULES_MAP.put(TeamType.RED, List.of(
            SlidingRule.of(new SlidingMovement(1, Direction.WEST)),
            SlidingRule.of(new SlidingMovement(1, Direction.SOUTH_WEST)),
            SlidingRule.of(new SlidingMovement(1, Direction.SOUTH)),
            SlidingRule.of(new SlidingMovement(1, Direction.SOUTH_EAST)),
            SlidingRule.of(new SlidingMovement(1, Direction.EAST))));
        TEAM_RULES_MAP.put(TeamType.BLUE, List.of(
            SlidingRule.of(new SlidingMovement(1, Direction.WEST)),
            SlidingRule.of(new SlidingMovement(1, Direction.NORTH_WEST)),
            SlidingRule.of(new SlidingMovement(1, Direction.NORTH)),
            SlidingRule.of(new SlidingMovement(1, Direction.NORTH_EAST)),
            SlidingRule.of(new SlidingMovement(1, Direction.EAST))));
    }

    private final List<Rule> rules;

    public SoldierAction(final TeamType teamType) {
        validateTeamRulesMapContainsKey(teamType);
        this.rules = TEAM_RULES_MAP.get(teamType);
    }

    private void validateTeamRulesMapContainsKey(final TeamType teamType) {
        if (!TEAM_RULES_MAP.containsKey(teamType)) {
            throw new IllegalStateException(
                String.format("%s의 병 기물 이동 패턴이 존재하지 않습니다.", teamType.getName()));
        }
    }

    @Override
    public List<Position> calculateMovablePositions(final Position from,
        final BoardMediator boardMediator) {
        return rules.stream()
            .map(rule -> rule.execute(from, boardMediator))
            .flatMap(Collection::stream)
            .toList();
    }

}
