package domain;

import domain.position.Position;
import domain.position.Route;
import domain.position.Routes;
import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.UnitType;
import domain.unit.Units;
import java.util.List;
import java.util.Map;

public class Janggi {

    private static final String EMPTY_POINT_EXCEPTION = "해당 위치에 기물이 존재하지 않습니다.";
    private static final String PICK_OPPOSITE_UNIT_EXCEPTION = "상대팀 말은 고를 수 없습니다.";
    private static final String CANNOT_MOVE_EXCEPTION = "이동할 수 없는 도착지입니다.";

    private static final Team FIRST_ATTACK_TEAM = Team.CHO;
    private static final double AFTER_ATTACK_HANDICAP = 1.5;

    private final Units totalUnits;
    private Team turn;

    private Janggi(Units totalUnits, Team turn) {
        this.totalUnits = totalUnits;
        this.turn = turn;
    }

    public static Janggi of(Units totalUnits) {
        return new Janggi(totalUnits, FIRST_ATTACK_TEAM);
    }

    public static Janggi of(Units totalUnits, Team turn) {
        return new Janggi(totalUnits, turn);
    }

    public void surrender() {
        totalUnits.killGeneralOf(turn);
    }

    public void doTurn(Position pick, Position destination) {
        if (!canMove(pick, destination)) {
            throw new IllegalArgumentException(CANNOT_MOVE_EXCEPTION);
        }
        if (totalUnits.isOppositeTeam(pick, destination)) {
            totalUnits.removeUnitAt(destination);
        }
        totalUnits.moveUnit(pick, destination);
        turn = turn.getOpposite();
    }

    private boolean canMove(Position pick, Position destination) {
        Routes movableRoutes = findMovableRoutesFrom(pick);
        return movableRoutes.hasRouteTo(pick, destination);
    }

    public Routes findMovableRoutesFrom(Position pick) {
        if (totalUnits.isEmptyPosition(pick)) {
            throw new IllegalArgumentException(EMPTY_POINT_EXCEPTION);
        }
        if (totalUnits.isUnitTeamNotEqualAt(pick, turn)) {
            throw new IllegalArgumentException(PICK_OPPOSITE_UNIT_EXCEPTION);
        }

        Routes unitRoutes = calculateRoutesByUnitType(pick);
        if (totalUnits.isUnitSameType(pick, UnitType.CANNON)) {
            return unitRoutes;
        }
        return filterBlockedRoutes(pick, unitRoutes);
    }

    private Routes calculateRoutesByUnitType(Position pick) {
        Routes routes = totalUnits.getUnitRoutes(pick);
        if (totalUnits.isUnitSameType(pick, UnitType.CANNON)) {
            return routes.filterByCondition(route -> canCannonJump(pick, route));
        }
        if (totalUnits.isUnitSameType(pick, UnitType.SOLDIER)) {
            return routes.filterByCondition(route -> route.isMovingForward(pick, turn));
        }
        return routes;
    }

    private boolean canCannonJump(Position current, Route route) {
        Position destination = route.searchDestination(current);

        if (totalUnits.isNotEmptyPosition(destination) &&
                totalUnits.isUnitSameType(destination, UnitType.CANNON)) {
            return false;
        }

        List<Position> pathPositions = route.getPositionsExceptDestination(current);
        return !hasCannonInPath(pathPositions) && onlyOneUnitInPath(pathPositions);
    }

    private boolean hasCannonInPath(List<Position> positions) {
        return positions.stream()
                .filter(totalUnits::isNotEmptyPosition)
                .anyMatch(position -> totalUnits.isUnitSameType(position, UnitType.CANNON));
    }

    private boolean onlyOneUnitInPath(List<Position> positions) {
        return positions.stream()
                .filter(totalUnits::isNotEmptyPosition)
                .count() == 1;
    }

    private Routes filterBlockedRoutes(Position pick, Routes routes) {
        return routes.filterByCondition(route -> isClearRoute(pick, route) && isClearDestination(pick, route));
    }

    private boolean isClearRoute(Position pick, Route route) {
        return route.getPositionsExceptDestination(pick).stream()
                .allMatch(totalUnits::isEmptyPosition);
    }

    private boolean isClearDestination(Position pick, Route route) {
        Position endPosition = route.searchDestination(pick);
        if (totalUnits.isEmptyPosition(endPosition)) {
            return true;
        }
        return totalUnits.isUnitTeamNotEqualAt(endPosition, turn);
    }

    public boolean isEnd() {
        return totalUnits.isGeneralNotExistIn(FIRST_ATTACK_TEAM) ||
                totalUnits.isGeneralNotExistIn(FIRST_ATTACK_TEAM.getOpposite()) ||
                totalUnits.existOnlyGeneralAndGuard();
    }

    public Team getWinner() {
        if (totalUnits.isGeneralNotExistIn(FIRST_ATTACK_TEAM)) {
            return FIRST_ATTACK_TEAM.getOpposite();
        }
        if (totalUnits.isGeneralNotExistIn(FIRST_ATTACK_TEAM.getOpposite())) {
            return FIRST_ATTACK_TEAM;
        }
        return getHigherScoreTeam();
    }

    private Team getHigherScoreTeam() {
        double firstAttackScore = getScoreOf(FIRST_ATTACK_TEAM);
        double afterAttackScore = getScoreOf(FIRST_ATTACK_TEAM.getOpposite());
        if (firstAttackScore > afterAttackScore) {
            return FIRST_ATTACK_TEAM;
        }
        return FIRST_ATTACK_TEAM.getOpposite();
    }

    public double getScoreOf(Team team) {
        double score = totalUnits.calculateScoreOf(team);
        if (team == FIRST_ATTACK_TEAM.getOpposite()) {
            score += AFTER_ATTACK_HANDICAP;
        }
        return score;
    }

    public Team getTurn() {
        return turn;
    }

    public Map<Position, Unit> getUnits() {
        return totalUnits.getAllUnits();
    }
}
