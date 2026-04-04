package domain.movement;

import domain.coordination.Coordination;
import domain.piece.Team;
import domain.piece.error.PieceException;

import java.util.List;
import java.util.Map;

public class TeamPalaceMovement implements PalaceMovement {

    private static final Map<Coordination, List<Coordination>> CHO_PALACE = Map.of(
            Coordination.of(4, 8), List.of(Coordination.of(4, 9), Coordination.of(5, 8), Coordination.of(5, 9)),
            Coordination.of(4, 9), List.of(Coordination.of(4, 10), Coordination.of(5, 9)),
            Coordination.of(4, 10), List.of(Coordination.of(5, 10)),
            Coordination.of(5, 8), List.of(Coordination.of(4, 8), Coordination.of(5, 9), Coordination.of(6, 8)),
            Coordination.of(5, 9), List.of(Coordination.of(4, 9), Coordination.of(4, 10), Coordination.of(5, 10), Coordination.of(6, 9), Coordination.of(6, 10)),
            Coordination.of(5, 10), List.of(Coordination.of(4, 10), Coordination.of(6, 10)),
            Coordination.of(6, 8), List.of(Coordination.of(5, 8), Coordination.of(5, 9), Coordination.of(6, 9)),
            Coordination.of(6, 9), List.of(Coordination.of(5, 9), Coordination.of(6, 10)),
            Coordination.of(6, 10), List.of(Coordination.of(5, 10))
    );
    private static final Map<Coordination, List<Coordination>> HAN_PALACE = Map.of(
            Coordination.of(4, 1), List.of(Coordination.of(5, 1)),
            Coordination.of(4, 2), List.of(Coordination.of(4, 1), Coordination.of(5, 2)),
            Coordination.of(4, 3), List.of(Coordination.of(4, 2), Coordination.of(5, 2), Coordination.of(5, 3)),
            Coordination.of(5, 1), List.of(Coordination.of(4, 1), Coordination.of(6, 1)),
            Coordination.of(5, 2), List.of(Coordination.of(4, 1), Coordination.of(4, 2), Coordination.of(5, 1), Coordination.of(6, 1), Coordination.of(6, 2)),
            Coordination.of(5, 3), List.of(Coordination.of(4, 3), Coordination.of(5, 2), Coordination.of(6, 3)),
            Coordination.of(6, 1), List.of(Coordination.of(5, 1)),
            Coordination.of(6, 2), List.of(Coordination.of(6, 1), Coordination.of(5, 2)),
            Coordination.of(6, 3), List.of(Coordination.of(5, 2), Coordination.of(5, 3), Coordination.of(6, 2))
    );

    private final Map<Coordination, List<Coordination>> palace;

    public TeamPalaceMovement(Team team) {
        if (team == Team.CHO) {
            this.palace = CHO_PALACE;
            return;
        }
        this.palace = HAN_PALACE;
    }

    @Override
    public boolean isPalace(Coordination from, Coordination to) {
        return palace.containsKey(from) && palace.containsKey(to);
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        if (!palace.getOrDefault(from, List.of()).contains(to)) {
            throw new PieceException(IMPOSSIBLE_PALACE_MOVE_MESSAGE);
        }
    }
}
