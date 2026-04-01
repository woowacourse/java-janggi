package strategy;

import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.Position;
import domain.Team;
import java.util.HashMap;
import java.util.Map;

public class LeftElephantFormationStrategy extends InitializeStrategy {

    @Override
    public Map<Position, Piece> initializeElephantHorseFormation(Team team) {
        Map<Position, Piece> map = new HashMap<>();

        if (team.equals(Team.HAN)) {
            map.put(Position.from(1, 3), new Elephant(Team.HAN));
            map.put(Position.from(1, 8), new Elephant(Team.HAN));
            map.put(Position.from(1, 2), new Horse(Team.HAN));
            map.put(Position.from(1, 7), new Horse(Team.HAN));
            return map;
        }

        map.put(Position.from(10, 2), new Elephant(Team.CHO));
        map.put(Position.from(10, 7), new Elephant(Team.CHO));
        map.put(Position.from(10, 3), new Horse(Team.CHO));
        map.put(Position.from(10, 8), new Horse(Team.CHO));

        return map;
    }
}
