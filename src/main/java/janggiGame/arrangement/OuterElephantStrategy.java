package janggiGame.arrangement;

import janggiGame.Position;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Elephant;
import janggiGame.piece.Horse;
import janggiGame.piece.Piece;
import java.util.Map;

public class OuterElephantStrategy extends ArrangementExceptHorseAndElephant {
    @Override
    public Map<Position, Piece> arrange(Dynasty dynasty) {
        Map<Position, Piece> result = super.arrange(dynasty);

        result.put(Position.of(1, 0), new Elephant(dynasty));
        result.put(Position.of(2, 0), new Horse(dynasty));

        result.put(Position.of(6, 0), new Horse(dynasty));
        result.put(Position.of(7, 0), new Elephant(dynasty));

        return result;
    }
}
