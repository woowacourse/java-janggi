package domain.board.cho;

import domain.coordination.Coordination;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Team;

import java.util.Map;

public class LeftInnerHorseChoPlacement extends ChoPlacementStrategy {

    @Override
    protected void placeHorseAndElephant(Map<Coordination, Piece> map) {
        map.put(Coordination.of(2, 10), new Elephant(Team.CHO));
        map.put(Coordination.of(3, 10), new Horse(Team.CHO));
        map.put(Coordination.of(7, 10), new Elephant(Team.CHO));
        map.put(Coordination.of(8, 10), new Horse(Team.CHO));
    }
}
