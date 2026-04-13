package domain.board.palace;

import domain.coordination.Coordination;
import domain.piece.Team;
import java.util.List;
import java.util.Optional;

public class Palace {

    private static final int LEFT_COLUMN = 4;
    private static final int RIGHT_COLUMN = 6;
    private static final int TOP_START_ROW = 1;
    private static final int TOP_END_ROW = 3;
    private static final int BOTTOM_START_ROW = 8;
    private static final int BOTTOM_END_ROW = 10;

    private static final List<List<Coordination>> DIAGONAL_ROUTES = List.of(
            List.of(
                    Coordination.of(LEFT_COLUMN, TOP_START_ROW),
                    Coordination.of(5, 2),
                    Coordination.of(RIGHT_COLUMN, TOP_END_ROW)
            ),
            List.of(
                    Coordination.of(RIGHT_COLUMN, TOP_START_ROW),
                    Coordination.of(5, 2),
                    Coordination.of(LEFT_COLUMN, TOP_END_ROW)
            ),
            List.of(
                    Coordination.of(LEFT_COLUMN, BOTTOM_START_ROW),
                    Coordination.of(5, 9),
                    Coordination.of(RIGHT_COLUMN, BOTTOM_END_ROW)
            ),
            List.of(
                    Coordination.of(RIGHT_COLUMN, BOTTOM_START_ROW),
                    Coordination.of(5, 9),
                    Coordination.of(LEFT_COLUMN, BOTTOM_END_ROW)
            )
    );

    public boolean isTopPalace(Coordination coordination) {
        return coordination.isInRange(LEFT_COLUMN, RIGHT_COLUMN, TOP_START_ROW, TOP_END_ROW);
    }

    public boolean isBottomPalace(Coordination coordination) {
        return coordination.isInRange(LEFT_COLUMN, RIGHT_COLUMN, BOTTOM_START_ROW, BOTTOM_END_ROW);
    }

    public boolean isSamePalace(Coordination from, Coordination to) {
        return palaceAreaOf(from).isSameArea(palaceAreaOf(to));
    }

    public boolean isEnemyPalace(Coordination coordination, Team team) {
        PalaceArea palaceArea = palaceAreaOf(coordination);
        if (team.isCho()) {
            return palaceArea == PalaceArea.TOP;
        }
        return palaceArea == PalaceArea.BOTTOM;
    }

    public PalaceRoute diagonalRoute(Coordination from, Coordination to) {
        return findRoute(from, to)
                .map(route -> PalaceRoute.of(extractPath(route, from, to)))
                .orElse(PalaceRoute.empty());
    }

    private PalaceArea palaceAreaOf(Coordination coordination) {
        if (isTopPalace(coordination)) {
            return PalaceArea.TOP;
        }
        if (isBottomPalace(coordination)) {
            return PalaceArea.BOTTOM;
        }
        return PalaceArea.NONE;
    }

    private Optional<List<Coordination>> findRoute(Coordination from, Coordination to) {
        return DIAGONAL_ROUTES.stream()
                .filter(route -> route.contains(from))
                .filter(route -> route.contains(to))
                .findFirst();
    }

    private List<Coordination> extractPath(List<Coordination> route, Coordination from, Coordination to) {
        int fromIndex = route.indexOf(from);
        int toIndex = route.indexOf(to);
        int start = Math.min(fromIndex, toIndex) + 1;
        int end = Math.max(fromIndex, toIndex);
        return route.subList(start, end);
    }
}
