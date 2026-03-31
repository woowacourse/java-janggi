package janggi.domain.pieceaction;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.ConstrainedMovement;
import janggi.domain.movement.Direction;
import janggi.domain.movement.UnconstrainedMovement;
import janggi.domain.movement.Rule;
import janggi.domain.movement.RuleWithTraces;
import janggi.domain.team.TeamType;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

public class SoldierAction implements PieceAction {

    private static final EnumMap<TeamType, List<Rule>> TEAM_RULES_MAP;

    static {
        TEAM_RULES_MAP = new EnumMap<>(TeamType.class);
        TEAM_RULES_MAP.put(TeamType.RED, List.of(
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.WEST)),
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.EAST)),
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.SOUTH))));
        TEAM_RULES_MAP.put(TeamType.BLUE, List.of(
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.WEST)),
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.EAST)),
            RuleWithTraces.of(new ConstrainedMovement(1, Direction.NORTH))));
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
