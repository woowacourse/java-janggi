package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.path.Path;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.point.Point;
import java.util.List;
import java.util.Map;

public class MoveRuleManager {

    private final Map<PieceType, MoveRule> moveRules;

    public MoveRuleManager() {
        this.moveRules = Map.of(
                PieceType.CHARIOT, new ChariotMoveRule(),
                PieceType.GENERAL, new GeneralMoveRule(),
                PieceType.GUARD, new GuardMoveRule(),
                PieceType.ELEPHANT, new ElephantMoveRule(),
                PieceType.SOLDIER, new SoliderMoveRule(),
                PieceType.CANNON, new CannonMoveRule(),
                PieceType.HORSE, new HorseMoveRule()
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
        Piece piece = from.readPiece();
        return moveRules.get(piece.pieceType());
    }

}
