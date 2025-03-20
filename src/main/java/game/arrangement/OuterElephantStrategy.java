package game.arrangement;

import game.Dot;
import piece.*;

import java.util.HashMap;
import java.util.Map;

public class OuterElephantStrategy implements ArrangementStrategy {

    @Override
    public Map<Dot, Piece> arrange(Dynasty dynasty) {
        Map<Dot, Piece> result = new HashMap<>();

        result.put(Dot.of(0, 0), new Chariot(dynasty));

        result.put(Dot.of(1, 0), new Elephant(dynasty));
        result.put(Dot.of(2, 0), new Horse(dynasty));
        result.put(Dot.of(3, 0), new Advisor(dynasty));

        result.put(Dot.of(5, 0), new Advisor(dynasty));
        result.put(Dot.of(6, 0), new Horse(dynasty));
        result.put(Dot.of(7, 0), new Elephant(dynasty));
        result.put(Dot.of(8, 0), new Chariot(dynasty));

        result.put(Dot.of(4, 1), new King(dynasty));

        result.put(Dot.of(1, 2), new Cannon(dynasty));
        result.put(Dot.of(7, 2), new Cannon(dynasty));

        result.put(Dot.of(0, 3), new Pawn(dynasty));
        result.put(Dot.of(2, 3), new Pawn(dynasty));
        result.put(Dot.of(4, 3), new Pawn(dynasty));
        result.put(Dot.of(6, 3), new Pawn(dynasty));
        result.put(Dot.of(8, 3), new Pawn(dynasty));

        return result;
    }
}
