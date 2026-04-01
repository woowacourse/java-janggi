package factory;

import domain.Position;
import java.util.List;

public class UpTeamPiecePositionFactory {

    public List<Position> upTeamChariotPositions() {
        return List.of(
                Position.of(0, 0),
                Position.of(0, 8)
        );
    }

    public List<Position> upTeamCannonPositions() {
        return List.of(
                Position.of(2, 1),
                Position.of(2, 7)
        );
    }

    public List<Position> upTeamSoldierPositions() {
        return List.of(
                Position.of(3, 0),
                Position.of(3, 2),
                Position.of(3, 4),
                Position.of(3, 6),
                Position.of(3, 8)
        );
    }

    public List<Position> upTeamHorsePositions() {
        return List.of(
                Position.of(0, 1),
                Position.of(0, 7)
        );
    }

    public List<Position> upTeamElephantPositions() {
        return List.of(
                Position.of(0, 2),
                Position.of(0, 6)
        );
    }

    public List<Position> upTeamGuardPositions() {
        return List.of(
                Position.of(0, 3),
                Position.of(0, 5)
        );
    }

    public Position upTeamGeneralPosition() {
        return Position.of(1, 4);
    }
}
