package domain.board.formation.cho;

import domain.board.formation.PlacementStrategy;
import domain.coordination.Coordination;
import domain.piece.*;

import java.util.Map;

public abstract class ChoPlacementStrategy extends PlacementStrategy {

    @Override
    protected void placeCommon(Map<Coordination, Piece> map) {
        map.put(Coordination.of(1, 10), new Chariot(Team.CHO));
        map.put(Coordination.of(9, 10), new Chariot(Team.CHO));
        map.put(Coordination.of(4, 10), new Guard(Team.CHO));
        map.put(Coordination.of(6, 10), new Guard(Team.CHO));
        map.put(Coordination.of(5, 9), new General(Team.CHO));
        map.put(Coordination.of(2, 8), new Cannon(Team.CHO));
        map.put(Coordination.of(8, 8), new Cannon(Team.CHO));
        map.put(Coordination.of(1, 7), new Soldier(Team.CHO));
        map.put(Coordination.of(3, 7), new Soldier(Team.CHO));
        map.put(Coordination.of(5, 7), new Soldier(Team.CHO));
        map.put(Coordination.of(7, 7), new Soldier(Team.CHO));
        map.put(Coordination.of(9, 7), new Soldier(Team.CHO));
    }
}
