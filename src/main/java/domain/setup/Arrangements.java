package domain.setup;

import domain.piece.Team;
import java.util.EnumMap;
import java.util.Map;

public class Arrangements {
    private static final String ERROR_ARRANGEMENT_NOT_FOUND = "[ERROR] 해당 팀의 배치가 없습니다.";

    private final Map<Team, Arrangement> arrangements;

    public Arrangements() {
        this.arrangements = new EnumMap<>(Team.class);
    }

    public Arrangements(Map<Team, Arrangement> arrangements) {
        this.arrangements = new EnumMap<>(arrangements);
    }

    public Arrangements assignArrangement(Team team, Arrangement arrangement) {
        Map<Team, Arrangement> newArrangements = new EnumMap<>(arrangements);
        newArrangements.put(team, arrangement);
        return new Arrangements(newArrangements);
    }

    public boolean needsArrangementFor(Team team) {
        return !arrangements.containsKey(team);
    }

    public Arrangement arrangeFor(Team team) {
        Arrangement arrangement = arrangements.get(team);
        if (arrangement != null) {
            return arrangement;
        }
        throw new IllegalArgumentException(ERROR_ARRANGEMENT_NOT_FOUND);
    }
}
