package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.path.Path;
import domain.point.Point;

import java.util.List;

public class MoveRuleManager {

    private final List<MoveRule> moveRules;

    public MoveRuleManager() {
        this.moveRules = List.of(
                new ChariotMoveRule(),
                new GeneralMoveRule(),
                new GuardMoveRule(),
                new ElephantMoveRule(),
                new SoliderMoveRule(),
                new CannonMoveRule(),
                new HorseMoveRule()
        );
    }

    public List<Point> findPathOfPoints(Intersection from, Intersection to) {
        MoveRule moveRule = findMoveRule(from);
        return moveRule.findPathOfPoints(from, to);
    }

    public void inspectPathByMoveRule(Intersection from, Path path) {
        MoveRule moveRule = findMoveRule(from);
        moveRule.checkMoveRule(path);
    }

    public MoveRule findMoveRule(Intersection from) {
        return moveRules.stream()
                .filter(moveRule -> moveRule.support(from))
                .findFirst()
                .orElse(null);
    }

}
