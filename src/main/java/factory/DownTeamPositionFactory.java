package factory;

import domain.Position;
import java.util.List;

public class DownTeamPositionFactory {

    public List<Position> downTeamChariotPositions() {
        return List.of(
                Position.of(9, 0),
                Position.of(9, 8)
        );
    }

    public List<Position> downTeamCannonPositions() {
        return List.of(
                Position.of(7, 1),
                Position.of(7, 7)
        );
    }

    public List<Position> downTeamSoldierPositions() {
        return List.of(
                Position.of(6, 0),
                Position.of(6, 2),
                Position.of(6, 4),
                Position.of(6, 6),
                Position.of(6, 8)
        );
    }

    public List<Position> downTeamHorsePositions() {
        return List.of(
                Position.of(9, 1),
                Position.of(9, 7)
        );
    }

    public List<Position> downTeamElephantPositions() {
        return List.of(
                Position.of(9, 2),
                Position.of(9, 6)
        );
    }

    public List<Position> downTeamGuardPositions() {
        return List.of(
                Position.of(9, 3),
                Position.of(9, 5)
        );
    }

    public Position downTeamGeneralPosition() {
        return Position.of(8, 4);
    }
}
