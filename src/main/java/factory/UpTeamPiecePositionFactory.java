package factory;

import domain.Position;
import java.util.List;

public class UpTeamPiecePositionFactory {

    public List<Position> upTeamChariotPositions() {
        return List.of(
                new Position(0, 0),
                new Position(0, 8)
        );
    }

    public List<Position> upTeamCannonPositions() {
        return List.of(
                new Position(2, 1),
                new Position(2, 7)
        );
    }

    public List<Position> upTeamSoldierPositions() {
        return List.of(
                new Position(3, 0),
                new Position(3, 2),
                new Position(3, 4),
                new Position(3, 6),
                new Position(3, 8)
        );
    }

    public List<Position> upTeamHorsePositions() {
        return List.of(
                new Position(0, 1),
                new Position(0, 7)
        );
    }

    public List<Position> upTeamElephantPositions() {
        return List.of(
                new Position(0, 2),
                new Position(0, 6)
        );
    }

    public List<Position> upTeamGuardPositions() {
        return List.of(
                new Position(0, 3),
                new Position(0, 5)
        );
    }

    public Position upTeamGeneralPosition() {
        return new Position(1, 4);
    }
}
