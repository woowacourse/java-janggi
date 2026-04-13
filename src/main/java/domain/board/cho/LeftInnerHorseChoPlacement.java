package domain.board.cho;

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

public class LeftInnerHorseChoPlacement implements PlacementStrategy {

    @Override
    public Map<Coordination, Piece> place() {
        Map<Coordination, Piece> map = new HashMap<>();
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
        map.put(Coordination.of(2, 10), new Elephant(Team.CHO));
        map.put(Coordination.of(3, 10), new Horse(Team.CHO));
        map.put(Coordination.of(7, 10), new Elephant(Team.CHO));
        map.put(Coordination.of(8, 10), new Horse(Team.CHO));
        return map;
    }
}
