package factory;

import domain.Position;
import java.util.List;

public class DownTeamPositionFactory {

    public List<Position> downTeamChariotPositions() {
        return List.of(
                new Position(9, 0),
                new Position(9, 8)
        );
    }

    public List<Position> downTeamCannonPositions() {
        return List.of(
                new Position(7, 1),
                new Position(7, 7)
        );
    }

    public List<Position> downTeamSoldierPositions() {
        return List.of(
                new Position(6, 0),
                new Position(6, 2),
                new Position(6, 4),
                new Position(6, 6),
                new Position(6, 8)
        );
    }

    public List<Position> downTeamHorsePositions() {
        return List.of(
                new Position(9, 1),
                new Position(9, 7)
        );
    }

    public List<Position> downTeamElephantPositions() {
        return List.of(
                new Position(9, 2),
                new Position(9, 6)
        );
    }

    public List<Position> downTeamGuardPositions() {
        return List.of(
                new Position(9, 3),
                new Position(9, 5)
        );
    }

    public Position downTeamGeneralPosition() {
        return new Position(8, 4);
    }
}
