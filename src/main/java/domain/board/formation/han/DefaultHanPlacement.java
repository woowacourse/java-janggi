package domain.board.formation.han;

import domain.coordination.Coordination;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Team;

import java.util.Map;

public class DefaultHanPlacement extends HanPlacementStrategy {

    @Override
    protected void placeHorseAndElephant(Map<Coordination, Piece> map) {
        map.put(Coordination.of(2, 1), new Horse(Team.HAN));
        map.put(Coordination.of(3, 1), new Elephant(Team.HAN));
        map.put(Coordination.of(7, 1), new Elephant(Team.HAN));
        map.put(Coordination.of(8, 1), new Horse(Team.HAN));
    }
}
