package domain.move;

import domain.board.palace.PalaceArea;
import domain.board.palace.PalaceRoute;
import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.Team;
import java.util.List;

public class MoveContext {
    private static final MoveDelta ONE_STEP_DIAGONAL = new MoveDelta(1, 1);

    private final Coordination from;
    private final Coordination to;
    private final PalaceArea fromPalaceArea;
    private final PalaceArea toPalaceArea;
    private final PalaceRoute palaceRoute;

    public MoveContext(Coordination from,
                       Coordination to,
                       PalaceArea fromPalaceArea,
                       PalaceArea toPalaceArea,
                       PalaceRoute palaceRoute) {
        this.from = from;
        this.to = to;
        this.fromPalaceArea = fromPalaceArea;
        this.toPalaceArea = toPalaceArea;
        this.palaceRoute = palaceRoute;
    }

    public Coordination from() {
        return from;
    }

    public Coordination to() {
        return to;
    }

    public boolean isSameRowMove() {
        return from.isSameRowDifferentColumn(to);
    }

    public boolean isSameColumnMove() {
        return from.isSameColumnDifferentRow(to);
    }

    public boolean isPalaceDiagonalMove() {
        return palaceRoute.exists();
    }

    public List<Coordination> palacePath() {
        return palaceRoute.path();
    }

    public boolean isSamePalace() {
        return fromPalaceArea.isSameArea(toPalaceArea);
    }

    public boolean isOrthogonalOneStep() {
        MoveDelta absolute = MoveDelta.between(from, to).absolute();
        return absolute.deltaColumn() + absolute.deltaRow() == 1;
    }

    public boolean isPalaceDiagonalOneStep() {
        MoveDelta absolute = MoveDelta.between(from, to).absolute();
        return ONE_STEP_DIAGONAL.equals(absolute) && isPalaceDiagonalMove();
    }

    public boolean isOneStepMoveInSamePalace() {
        return isSamePalace() && (isOrthogonalOneStep() || isPalaceDiagonalOneStep());
    }

    public boolean isFromInEnemyPalace(Team team) {
        return isEnemyPalace(fromPalaceArea, team);
    }

    public boolean isToInEnemyPalace(Team team) {
        return isEnemyPalace(toPalaceArea, team);
    }

    private boolean isEnemyPalace(PalaceArea palaceArea, Team team) {
        if (team.isCho()) {
            return palaceArea == PalaceArea.TOP;
        }
        return palaceArea == PalaceArea.BOTTOM;
    }
}
