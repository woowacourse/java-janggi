import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DefaultUnitPosition {
    KING(1, 8, List.of(4)),
    SCHOLAR(0, 9, List.of(3, 5)),
    BOMB(2, 7, List.of(1, 7)),
    CAR(0, 9, List.of(0, 8)),
    JOL(0, 6, List.of(0, 2, 4, 6, 8)),
    ELEPHANT(0, 6, List.of(1, 6)),
    HORSE(0, 6, List.of(2, 7)),
    ;

    private final int hanY;
    private final int choY;
    private final List<Integer> xPositions;

    DefaultUnitPosition(int hanY, int choY, List<Integer> xPositions) {
        this.hanY = hanY;
        this.choY = choY;
        this.xPositions = xPositions;
    }

    public static List<Point> createPositions(DefaultUnitPosition position, String team) {
        if (team.equals("한나라")) {
            return position.xPositions.stream()
                    .map(x -> new Point(x, position.hanY))
                    .toList();
        }
        return position.xPositions.stream()
                .map(x -> new Point(x, position.choY))
                .toList();
    }
}
