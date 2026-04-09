package domain.board;

import domain.coordination.Coordination;
import java.util.List;

public class MoveContext {

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

    public PalaceArea fromPalaceArea() {
        return fromPalaceArea;
    }

    public PalaceArea toPalaceArea() {
        return toPalaceArea;
    }

    public boolean isSamePalace() {
        return fromPalaceArea.isSameArea(toPalaceArea);
    }
}
