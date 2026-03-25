package domain.board.han;

import domain.board.PlacementStrategy;
import domain.coordination.Coordination;
import domain.piece.Piece;
import domain.piece.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Guard;
import domain.piece.General;
import domain.piece.Soldier;

import java.util.Map;

public abstract class HanPlacementStrategy extends PlacementStrategy {

    @Override
    protected void placeCommon(Map<Coordination, Piece> map) {
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
    }
}
