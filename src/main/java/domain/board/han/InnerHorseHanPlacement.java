package domain.board.han;

import domain.board.PlacementStrategy;
import domain.coordination.Coordination;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.piece.Team;

import java.util.HashMap;
import java.util.Map;

public class InnerHorseHanPlacement implements PlacementStrategy {

    @Override
    public Map<Coordination, Piece> place() {
        Map<Coordination, Piece> map = new HashMap<>();
        map.put(Coordination.of(1, 1), new Chariot(Team.HAN));
        map.put(Coordination.of(9, 1), new Chariot(Team.HAN));
        map.put(Coordination.of(4, 1), new Guard(Team.HAN));
        map.put(Coordination.of(6, 1), new Guard(Team.HAN));
        map.put(Coordination.of(5, 2), new General(Team.HAN));
        map.put(Coordination.of(2, 3), new Cannon(Team.HAN));
        map.put(Coordination.of(8, 3), new Cannon(Team.HAN));
        map.put(Coordination.of(1, 4), new Soldier(Team.HAN));
        map.put(Coordination.of(3, 4), new Soldier(Team.HAN));
        map.put(Coordination.of(5, 4), new Soldier(Team.HAN));
        map.put(Coordination.of(7, 4), new Soldier(Team.HAN));
        map.put(Coordination.of(9, 4), new Soldier(Team.HAN));
        map.put(Coordination.of(2, 1), new Elephant(Team.HAN));
        map.put(Coordination.of(3, 1), new Horse(Team.HAN));
        map.put(Coordination.of(7, 1), new Horse(Team.HAN));
        map.put(Coordination.of(8, 1), new Elephant(Team.HAN));
        return map;
    }
}
