package janggiGame.arrangement;

import janggiGame.board.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Elephant;
import janggiGame.piece.Horse;
import janggiGame.piece.Piece;
import java.util.Map;

public class OuterElephantStrategy extends HorseElephantChangeStrategy {
    @Override
    public Map<Dot, Piece> arrange(Dynasty dynasty) {
        Map<Dot, Piece> result = super.arrange(dynasty);

        result.put(Dot.of(1, 0), new Elephant(dynasty));
        result.put(Dot.of(2, 0), new Horse(dynasty));

        result.put(Dot.of(6, 0), new Horse(dynasty));
        result.put(Dot.of(7, 0), new Elephant(dynasty));

        return result;
    }
}
